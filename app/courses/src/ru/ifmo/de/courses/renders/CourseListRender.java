package ru.ifmo.de.courses.renders;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import ru.ifmo.de.courses.AbstractRender;
import ru.ifmo.de.courses.daos.CourseDAO;
import ru.ifmo.de.courses.pojo.Course;
import ru.ifmo.de.courses.pojo.Page;
import ru.ifmo.de.courses.servlets.MainServlet;
import ru.ifmo.de.courses.tools.MySQLManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * ru.ifmo.de.courses.renders.CourseListRender.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */
public class CourseListRender extends AbstractRender {
    public CourseListRender(HttpServletRequest request, HttpServletResponse response, VelocityEngine velocityEngine) {
        super(request, response, velocityEngine);
    }

    public Page renderListCourses(Page page){
        MySQLManager manager = new MySQLManager(MainServlet.appProp.get("db.url"),MainServlet.appProp.get("db.login"), MainServlet.appProp.get("db.pass"));
        Template t = super.getVelocityEngine().getTemplate("courseList.vm", "utf-8");

        CourseDAO courseDAO = new CourseDAO(manager);
        try {
            List<Course> courses = courseDAO.getAllCourses();
            VelocityContext context = new VelocityContext();

            context.put("courses", courses);
            context.put("page", page);

            StringWriter sw = new StringWriter();

            t.merge(context, sw);

            page.setContent(sw.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return page;
    }

    public Page getMainCoursePage(Page page){


        return page;
    }
}
