package ru.ifmo.de.courses.actions;

import org.apache.velocity.app.VelocityEngine;
import ru.ifmo.de.courses.AbstractRender;
import ru.ifmo.de.courses.pojo.CoursePage;
import ru.ifmo.de.courses.pojo.PageType;
import ru.ifmo.de.courses.servlets.MainServlet;
import ru.ifmo.de.courses.tools.DiffTool;
import ru.ifmo.de.courses.tools.MySQLManager;
import ru.ifmo.de.courses.tools.StringRoutine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 03.05.13
 * Time: 19:39
 * To change this template use File | Settings | File Templates.
 */
public class CourseAction extends AbstractRender {
    public CourseAction(HttpServletRequest request, HttpServletResponse response, VelocityEngine velocityEngine) {
        super(request, response, velocityEngine);
    }

    /**
     * Проверка на существование name и создание курса
     * @return номер курса, на который нужно отфорводить пользователя
     */
    public String createCourse(){
        String courseNumber = super.getRequest().getParameter("number");
        String courseName = super.getRequest().getParameter("name");

        if ((courseNumber == null) || (courseName == null) || (courseNumber.equals("")) || (courseName.equals(""))){
            return "courseCreateEmptyFields";
        }

        if (courseNumber.contains(" ")){
            return "courseCreateSpacesInNumber";
        }

        MySQLManager manager = new MySQLManager(MainServlet.appProp.get("db.url"),MainServlet.appProp.get("db.login"), MainServlet.appProp.get("db.pass"));

        ResultSet rs = manager.exeQue("SELECT c.id FROM courses c WHERE c.number = ?", courseNumber);
        try {
            if (rs.next()){
                manager.close();
                return "courseCreateAlreadyExist";
            }

            manager.exeUpd("INSERT INTO courses (`number`, `name`) VALUES (?, ?)",
                    StringRoutine.isoToUtf(courseNumber), StringRoutine.isoToUtf(courseName));

            ResultSet rs2 = manager.exeQue("SELECT c.`id` FROM courses c WHERE c.`number` = ?", courseNumber);
            if (rs2.next()){
                Integer courseId = rs2.getInt(1);
                //todo Называть по разному в зависимости от языка

                manager.exeUpd("INSERT INTO pages " +
                        "(pages.course_id, pages.`type`, pages.`short_name`, pages.content, pages.short_name_eng) VALUES " +
                        "(?, ?, ?, '', ''), " +
                        "(?, ?, ?, '', ''), " +
                        "(?, ?, ?, '', ''), " +
                        "(?, ?, ?, '', ''), " +
                        "(?, ?, ?, '', ''), " +
                        "(?, ?, ?, '', '') ",

                        courseId, PageType.home, "Главная",
                        courseId, PageType.program, "Программа курса",
                        courseId, PageType.literature, "Литература",
                        courseId, PageType.exercise, "Упражнения",
                        courseId, PageType.training, "Практикумы",
                        courseId, PageType.infoResources, "Информационные ресурсы"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        manager.close();
        return "courseCreateSuccessful/" + courseNumber;
    }

    /**
     * Обновление страницы курса.
     * Получаем последнюю полную версию страницы last. Получаем номер последней ревизии lastRev.
     * Вычисляем diff между last и newStr - diffStr. Заменяем в базе last на newStr. Добавляем ревизию diffStr.
     * @param page
     * @return
     */
    public String updatePage(CoursePage page, Integer userId){

        String newStr = page.getContent();//Новая страница
        String lastStr = "";//Последняя полная версия страницы
        Integer lastRevNum = 0;//Номер последней ревизии
        String diffStr = "";//Разница между lastStr и newStr

        MySQLManager manager = new MySQLManager(MainServlet.appProp.get("db.url"),MainServlet.appProp.get("db.login"), MainServlet.appProp.get("db.pass"));

        //Определяем номер последней ревизии
        ResultSet rs = manager.exeQue("SELECT * FROM page_hist p WHERE p.page_id = ? ORDER BY p.date_time DESC LIMIT 1", page.getId());
        try {
            if (rs.next()){
                lastRevNum = rs.getInt(5);
            } else {
                lastRevNum = 0;
            }

            //Получаем последнюю полную копию
            ResultSet rs2 = manager.exeQue("SELECT * FROM pages p WHERE p.`id` = ?", page.getId());
            if (rs2.next()){
                lastStr = rs2.getString(6);
            }

            diffStr = DiffTool.getDiff(lastStr, newStr);

            manager.exeUpd("UPDATE pages p SET p.content = ? WHERE p.`id` = ?", newStr, page.getId());

            lastRevNum++;

            manager.exeUpd("INSERT INTO page_hist (date_time, user_id, diff, version, page_id) VALUES (now(), ?, ?, ?, ?)",
                    userId, diffStr, lastRevNum, page.getId()
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }

        manager.close();
        return "coursePageUpdateError";
    }
}
