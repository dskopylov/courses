package ru.ifmo.de.courses.renders;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import ru.ifmo.de.courses.AbstractRender;
import ru.ifmo.de.courses.daos.UserDAO;
import ru.ifmo.de.courses.pojo.Page;
import ru.ifmo.de.courses.pojo.User;
import ru.ifmo.de.courses.servlets.MainServlet;
import ru.ifmo.de.courses.tools.MySQLManager;
import ru.ifmo.de.courses.tools.StringRoutine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 26.05.13
 * Time: 17:34
 * To change this template use File | Settings | File Templates.
 */
public class UserRender extends AbstractRender{
    public UserRender(HttpServletRequest request, HttpServletResponse response, VelocityEngine velocityEngine) {
        super(request, response, velocityEngine);
    }

    public Page renderPage(Page page, Integer userId){
        MySQLManager manager = new MySQLManager(MainServlet.appProp.get("db.url"),MainServlet.appProp.get("db.login"), MainServlet.appProp.get("db.pass"));
        UserDAO userDAO = new UserDAO(manager);
        try {
            User user = userDAO.getUserById(userId);
            if (user != null){
                Template t = super.getVelocityEngine().getTemplate("userPage.vm", "utf-8");
                VelocityContext c = new VelocityContext();
                StringWriter sw = new StringWriter();

                c.put("user", user);

                t.merge(c, sw);
                page.setContent(sw.toString());


            }
            manager.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return page;
    }

    public Page renderReg(Page page, String command){
        Template t = super.getVelocityEngine().getTemplate("userReg.vm", "utf-8");
        VelocityContext c = new VelocityContext();
        StringWriter sw = new StringWriter();

        if (!command.equals("")){
            if (command.equals("userRegEmptyFields")){
                c.put("message", "Не заполнены обязательные поля!");

            } else if (command.equals("userRegAlreadyExist")){
                c.put("message", "Пользователь с таким логином существует!");

            } else if (command.equals("userRegEmailAlreadyExist")){
                c.put("message", "Пользователь с таким e-mail существует!");
            }



            c.put("name", StringRoutine.isoToUtf(super.getRequest().getParameter("name")));
            c.put("login", StringRoutine.isoToUtf(super.getRequest().getParameter("login")));
            c.put("password", StringRoutine.isoToUtf(super.getRequest().getParameter("password")));
            c.put("email", StringRoutine.isoToUtf(super.getRequest().getParameter("email")));

        } else {
            c.put("name", "");
            c.put("login", "");
            c.put("password", "");
            c.put("email", "");
            c.put("message", "");
        }

        t.merge(c, sw);
        page.setContent(sw.toString());

        return page;
    }

    public Page renderHello(Page page){
        Template t = super.getVelocityEngine().getTemplate("userHello.vm", "utf-8");
        VelocityContext c = new VelocityContext();
        StringWriter sw = new StringWriter();
        c.put("page", page);
        t.merge(c, sw);

        page.setContent(sw.toString());
        return page;
    }
}

