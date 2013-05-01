package ru.ifmo.de.courses.daos;

import ru.ifmo.de.courses.pojo.Course;
import ru.ifmo.de.courses.pojo.CoursePage;
import ru.ifmo.de.courses.tools.MySQLManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * ru.ifmo.de.courses.daos.CourseDAO.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */
public class CourseDAO extends DAO{
    public CourseDAO(MySQLManager manager) {
        super(manager);
    }

    /**
     * Возвращает курс без страниц
     * @param id
     * @return
     * @throws SQLException
     */
    public Course getCourseById(Integer id) throws SQLException {
        Course course = new Course();

        ResultSet rs = super.getManager().exeQue("SELECT * FROM courses c WHERE c.`id` = ?", id);

        if (rs.next()){
            course.setId(rs.getInt(1));
            course.setNumber(rs.getString(2));
            course.setName(rs.getString(3));

        } else {
            return null;
        }

        return course;
    }

    /**
     * Возвращает курс со страницами без контента страниц, но с контентом главной страницы курса
     * @param id
     * @return
     * @throws SQLException
     */
    public Course getCourseWithPagesById(Integer id) throws SQLException {
        Course course = new Course();

        ResultSet rs = super.getManager().exeQue("SELECT * FROM courses c WHERE c.`id` = ?", id);

        if (rs.next()){

            //Сам курс
            course.setId(rs.getInt(1));
            course.setNumber(rs.getString(2));
            course.setName(rs.getString(3));

            //Главная страница курса
            ResultSet rs2 = super.getManager().exeQue("SELECT * FROM pages p WHERE p.course_id = ? AND p.type = 'main'", id);

            if (rs2.next()){
                CoursePage mainPage = new CoursePage();
                //Заполнение

                mainPage.setId(rs2.getInt(1));
                mainPage.setCourseId(rs2.getInt(2));
                mainPage.setType(rs2.getString(3));
                mainPage.setShortName(rs2.getString(4));
                mainPage.setShortNameEng(rs2.getString(5));
                mainPage.setContent(rs2.getString(6));

                course.setMainPage(mainPage);
            }

            //Названия остальных страниц курса
            ResultSet rs3 = super.getManager().exeQue("SELECT p.id, p.course_id, p.type, p.short_name, p.short_name_eng FROM pages p WHERE p.course_id = ?", id);
            List<CoursePage> pages = new LinkedList<CoursePage>();

            while (rs3.next()){
                CoursePage coursePage = new CoursePage();

                coursePage.setId(rs3.getInt(1));
                coursePage.setCourseId(rs3.getInt(2));
                coursePage.setType(rs3.getString(3));
                coursePage.setShortName(rs3.getString(4));
                coursePage.setShortNameEng(rs3.getString(5));

                pages.add(coursePage);

            }

            course.setPages(pages);

        } else {
            return null;
        }

        return course;
    }

    public CoursePage getCoursePage(Integer pageId){
        ResultSet rs2 = super.getManager().exeQue("SELECT * FROM pages p WHERE p.id = ?", pageId);

        try {
            if (rs2.next()){
                CoursePage page = new CoursePage();
                //Заполнение

                page.setId(rs2.getInt(1));
                page.setCourseId(rs2.getInt(2));
                page.setType(rs2.getString(3));
                page.setShortName(rs2.getString(4));
                page.setShortNameEng(rs2.getString(5));
                page.setContent(rs2.getString(6));

                return page;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new LinkedList<Course>();

        ResultSet rs = super.getManager().exeQue("SELECT * FROM courses");

        while (rs.next()){
            Course course = new Course();

            course.setId(rs.getInt(1));
            course.setNumber(rs.getString(2));
            course.setName(rs.getString(3));

            courses.add(course);

        }

        manager.close();
        return courses;
    }
}
