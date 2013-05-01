package ru.ifmo.de.courses.renders;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
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

/**
 * ru.ifmo.de.courses.renders.CourseRender.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */
public class CourseRender extends AbstractRender{
    public CourseRender(HttpServletRequest request, HttpServletResponse response, VelocityEngine velocityEngine) {
        super(request, response, velocityEngine);
    }

    public Page renderMainPage(Page page){
        MySQLManager manager = new MySQLManager(MainServlet.appProp.get("db.url"),MainServlet.appProp.get("db.login"), MainServlet.appProp.get("db.pass"));
        Template t = super.getVelocityEngine().getTemplate("coursePage.vm", "utf-8");

        VelocityContext context = new VelocityContext();
        StringWriter sw = new StringWriter();

        context.put("page", page);

        CourseDAO courseDAO = new CourseDAO(manager);

        try {
            Course course = courseDAO.getCourseWithPagesById(1);

            course.setCurr(course.getMainPage());

            context.put("course", course);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        t.merge(context, sw);

        page.setContent(sw.toString());
        manager.close();
        return page;
    }

    public Page renderPage(Page page){
        MySQLManager manager = new MySQLManager(MainServlet.appProp.get("db.url"),MainServlet.appProp.get("db.login"), MainServlet.appProp.get("db.pass"));
        Template t = super.getVelocityEngine().getTemplate("coursePage.vm", "utf-8");

        VelocityContext context = new VelocityContext();
        StringWriter sw = new StringWriter();

        context.put("page", page);

        CourseDAO courseDAO = new CourseDAO(manager);

        try {
            Course course = courseDAO.getCourseWithPagesById(1);


            //Должны определить Id страницы и поставить в course.curr


            course.setCurr(course.getMainPage());

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
