package ru.ifmo.de.courses.renders;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import ru.ifmo.de.courses.AbstractRender;
import ru.ifmo.de.courses.daos.CourseDAO;
import ru.ifmo.de.courses.pojo.Course;
import ru.ifmo.de.courses.pojo.CoursePage;
import ru.ifmo.de.courses.pojo.Page;
import ru.ifmo.de.courses.servlets.MainServlet;
import ru.ifmo.de.courses.tools.MySQLManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.sql.SQLException;

/**
 * ru.ifmo.de.courses.renders.CourseRender.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */
public class CourseRender extends AbstractRender{
    public CourseRender(HttpServletRequest request, HttpServletResponse response, VelocityEngine velocityEngine) {
        super(request, response, velocityEngine);
    }

    /**
     * Рендерит главную страницу курса todo проверить на используемость
     * @param page
     * @param courseNumber
     * @return
     */
    public Page renderMainPage(Page page, String courseNumber){
        MySQLManager manager = new MySQLManager(MainServlet.appProp.get("db.url"),MainServlet.appProp.get("db.login"), MainServlet.appProp.get("db.pass"));
        Template t = super.getVelocityEngine().getTemplate("coursePage.vm", "utf-8");

        VelocityContext context = new VelocityContext();
        StringWriter sw = new StringWriter();

        context.put("page", page);

        CourseDAO courseDAO = new CourseDAO(manager);

        try {
            Course course = courseDAO.getCourseWithPagesByCourseNumber(courseNumber);

            course.setCurr(course.getMainPage());
            course.getCurr().setMode("read");

            context.put("course", course);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        t.merge(context, sw);

        page.setContent(sw.toString());
        manager.close();
        return page;
    }

    /**
     * Рендерит страницу курса
     * @param page
     * @param courseNumber
     * @param pageType
     * @return
     */
    public Page renderPage(Page page, String courseNumber, String pageType){
        MySQLManager manager = new MySQLManager(MainServlet.appProp.get("db.url"),MainServlet.appProp.get("db.login"), MainServlet.appProp.get("db.pass"));
        Template t = super.getVelocityEngine().getTemplate("coursePage.vm", "utf-8");

        VelocityContext context = new VelocityContext();
        StringWriter sw = new StringWriter();

        context.put("page", page);

        CourseDAO courseDAO = new CourseDAO(manager);

        try {

            //Должны определить Id страницы и поставить в course.curr
            //todo Проверку на null

            //Текущая страница
            Course course = courseDAO.getCourseAndCurrPage(courseNumber, pageType);

            course.getCurr().setMode("read");
            context.put("course", course);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        t.merge(context, sw);

        page.setContent(sw.toString());
        manager.close();

        return page;
    }

    /**
     * Рендерит страницу с редактированием страницы курса
     * @param page
     * @param courseNumber
     * @param pageType
     * @return
     */
    public Page renderPageEdit(Page page, String courseNumber, String pageType){
        MySQLManager manager = new MySQLManager(MainServlet.appProp.get("db.url"),MainServlet.appProp.get("db.login"), MainServlet.appProp.get("db.pass"));

        Template t = super.getVelocityEngine().getTemplate("coursePage.vm", "utf-8");
        Template tEdit = super.getVelocityEngine().getTemplate("coursePageEdit.vm", "utf-8");

        VelocityContext context = new VelocityContext();
        VelocityContext contextEdit = new VelocityContext();

        StringWriter sw = new StringWriter();
        StringWriter swEdit = new StringWriter();//Должны поставить вместо coursePage.content

        context.put("page", page);

        CourseDAO courseDAO = new CourseDAO(manager);

        try {

            //Должны определить Id страницы и поставить в course.curr
            //todo Проверку на null

            //Текущая страница
            CoursePage coursePage = courseDAO.getCoursePageByType(courseNumber, pageType);
            coursePage.setMode("edit");

            //Для редактирования
            contextEdit.put("raw", coursePage.getContent());
            tEdit.merge(contextEdit, swEdit);

            coursePage.setContent(swEdit.toString());

            //Текущий курс
            Course course = courseDAO.getCourseWithPagesById(coursePage.getCourseId());
            course.setCurr(coursePage);

            context.put("course", course);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        t.merge(context, sw);

        page.setContent(sw.toString());
        manager.close();

        return page;
    }
}
