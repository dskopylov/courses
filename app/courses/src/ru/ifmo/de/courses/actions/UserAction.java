package ru.ifmo.de.courses.actions;

import org.apache.velocity.app.VelocityEngine;
import ru.ifmo.de.courses.AbstractRender;
import ru.ifmo.de.courses.daos.UserDAO;
import ru.ifmo.de.courses.pojo.User;
import ru.ifmo.de.courses.servlets.MainServlet;
import ru.ifmo.de.courses.tools.MySQLManager;
import ru.ifmo.de.courses.tools.StringRoutine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 26.05.13
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public class UserAction extends AbstractRender {
    public UserAction(HttpServletRequest request, HttpServletResponse response, VelocityEngine velocityEngine) {
        super(request, response, velocityEngine);
    }

    public String regUser(){
        if (super.getRequest().getParameter("name") == null || super.getRequest().getParameter("login") == null
                || super.getRequest().getParameter("password") == null || super.getRequest().getParameter("email") == null
                || super.getRequest().getParameter("name").equals("") || super.getRequest().getParameter("login").equals("")
                || super.getRequest().getParameter("password").equals("") || super.getRequest().getParameter("email").equals("")){

            return "userRegEmptyFields";
        }

        UserDAO userDAO = new UserDAO(new MySQLManager(MainServlet.appProp.get("db.url"),MainServlet.appProp.get("db.login"), MainServlet.appProp.get("db.pass")));

        try {
            User u = userDAO.getUserByLogin(super.getRequest().getParameter("login"));
            if (u != null){
                return "userRegAlreadyExist";
            }

            u = userDAO.getUserByEmail(super.getRequest().getParameter("email"));
            if (u != null){
                return "userRegEmailAlreadyExist";
            }

            //Создаем пользователя
            User user = new User();
            user.setLogin(StringRoutine.isoToUtf(super.getRequest().getParameter("login")));
            user.setPassword(StringRoutine.isoToUtf(super.getRequest().getParameter("password")));
            user.setName(StringRoutine.isoToUtf(super.getRequest().getParameter("name")));
            user.setEmail(StringRoutine.isoToUtf(super.getRequest().getParameter("email")));
            userDAO.addUser(user);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "userRegSuccessful";
    }
}
