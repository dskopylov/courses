package ru.ifmo.de.courses.daos;

import ru.ifmo.de.courses.pojo.Course;
import ru.ifmo.de.courses.pojo.CoursePage;
import ru.ifmo.de.courses.pojo.PageType;
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
     * Возвращает курс со страницами без контента страниц, но с контентом главной страницы курса по course.id
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
            ResultSet rs2 = super.getManager().exeQue("SELECT * FROM pages p WHERE p.course_id = ? AND p.type = ?", id, PageType.home);

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

    /**
     * Возвращает курс со страницами без контента страниц, но с контентом главной страницы курса по course.number
     * @param courseNumber
     * @return
     * @throws SQLException
     */
    public Course getCourseWithPagesByCourseNumber(String courseNumber) throws SQLException {
        Course course = new Course();

        ResultSet rs = super.getManager().exeQue("SELECT * FROM courses c WHERE c.`number` = ?", courseNumber);

        if (rs.next()){

            //Сам курс
            course.setId(rs.getInt(1));
            course.setNumber(rs.getString(2));
            course.setName(rs.getString(3));

            //Главная страница курса
            ResultSet rs2 = super.getManager().exeQue("SELECT * FROM pages p WHERE p.course_id = ? AND p.type = ?", course.getId(), PageType.home);

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
            ResultSet rs3 = super.getManager().exeQue("SELECT p.id, p.course_id, p.type, p.short_name, p.short_name_eng FROM pages p WHERE p.course_id = ?", course.getId());
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

    /**
     * Возвращает страницу курса
     * @param courseNumber
     * @param pageType
     * @return
     */
    public CoursePage getCoursePageByType(String courseNumber, String pageType){
        ResultSet rs = super.getManager().exeQue("SELECT * FROM courses c WHERE c.number = ?", courseNumber);

        try {
            if (rs.next()){
                ResultSet rs2 = super.getManager().exeQue("SELECT * FROM pages p WHERE p.`type` = ? AND p.course_id = ?", pageType, rs.getInt(1));

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
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    /**
     * Возвращает курс и текущую страницу курса
     * @param courseNumber
     * @param pageType
     * @return
     * @throws java.sql.SQLException
     */
    public Course getCourseAndCurrPage(String courseNumber, String pageType) throws SQLException {
        Course course = new Course();

        ResultSet rs = super.getManager().exeQue("SELECT * FROM courses c WHERE c.`number` = ?", courseNumber);

        if (rs.next()){
            course.setId(rs.getInt(1));
            course.setNumber(rs.getString(2));
            course.setName(rs.getString(3));

            //Названия остальных страниц курса
            ResultSet rs3 = super.getManager().exeQue("SELECT p.id, p.course_id, p.type, p.short_name, p.short_name_eng FROM pages p WHERE p.course_id = ?", course.getId());
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

            //Текущая страница
            ResultSet rs2 = super.getManager().exeQue("SELECT * FROM pages p WHERE p.`type` = ? AND p.course_id = ?", pageType, rs.getInt(1));

            if (rs2.next()){
                CoursePage page = new CoursePage();
                //Заполнение

                page.setId(rs2.getInt(1));
                page.setCourseId(rs2.getInt(2));
                page.setType(rs2.getString(3));
                page.setShortName(rs2.getString(4));
                page.setShortNameEng(rs2.getString(5));
                page.setContent(rs2.getString(6));

                course.setCurr(page);
                return course;
            }

        }

        return course;
    }

    /**
     * Возвращает список всех курсов
     * @return
     * @throws SQLException
     */
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

    /**
     * Возвращает текущую версию страницы по идентификатору страницы
     * @param pageId
     * @return
     */
    public String getCurrentPageVersion(Integer pageId){
        ResultSet rs = super.getManager().exeQue("SELECT * FROM page_hist ph WHERE ph.`id` = ? ORDER BY ph.datetime DESC LIMIT 1", pageId);

        try {
            if (rs.next()){
                return rs.getString(5);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return "0";
    }

    /**
     * Проверяет, есть ли course.number, если нет,то создает курс и необходимые страницы.
     * @param course
     * @return
     */
    public String createCourse(Course course){


        return "successful";
    }
}
