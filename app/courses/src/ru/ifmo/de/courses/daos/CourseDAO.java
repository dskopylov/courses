package ru.ifmo.de.courses.daos;

import ru.ifmo.de.courses.pojo.Course;
import ru.ifmo.de.courses.tools.MySQLManager;

import java.sql.ResultSet;
import java.sql.SQLException;
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
     * Возвращает курс со страницами без контента страниц
      * @param id
     * @return
     * @throws SQLException
     */
    public Course getCourseWithPagesById(Integer id) throws SQLException {
        Course course = new Course();

        ResultSet rs = super.getManager().exeQue("SELECT * FROM courses c WHERE c.`id` = ?", id);

        if (rs.next()){
            course.setId(rs.getInt(1));
            course.setNumber(rs.getString(2));
            course.setName(rs.getString(3));

            ResultSet rs2 = super.getManager().exeQue("SELECT p.id, p.course_id, p.type, p.short_name, p.short_name_eng, FROM pages p WHERE p.course_id = ?", id);

            while (rs2.next()){

            }

        } else {
            return null;
        }

        return course;
    }
}
