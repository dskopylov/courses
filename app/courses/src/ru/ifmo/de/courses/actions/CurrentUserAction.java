package ru.ifmo.de.courses.actions;

import org.apache.velocity.app.VelocityEngine;
import ru.ifmo.de.courses.AbstractRender;
import ru.ifmo.de.courses.daos.UserDAO;
import ru.ifmo.de.courses.pojo.User;
import ru.ifmo.de.courses.servlets.MainServlet;
import ru.ifmo.de.courses.tools.MySQLManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * ru.ifmo.de.courses.actions.CurrentUserAction.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */
public class CurrentUserAction extends AbstractRender{
    public CurrentUserAction(HttpServletRequest request, HttpServletResponse response, VelocityEngine velocityEngine) {
        super(request, response, velocityEngine);
    }

    public User getCurrentUser(){
        User user = new User();

        Object currentUserSession = super.getRequest().getSession().getAttribute("currentUser");

        if (currentUserSession == null){//Не залогинен
            return user;

        } else {
            user = User.parseUser(currentUserSession.toString());
            user.setAuth(true);
        }



        return user;
    }

    public String processLogin(){
        if (super.getRequest().getParameter("login") != null && super.getRequest().getParameter("password") != null){

            MySQLManager manager = new MySQLManager(MainServlet.appProp.get("db.url"),MainServlet.appProp.get("db.login"), MainServlet.appProp.get("db.pass"));

            UserDAO userDAO = new UserDAO(manager);

            try {
                User user = userDAO.getUserByLoginPassword(super.getRequest().getParameter("login"), super.getRequest().getParameter("password"));

                if (user != null){
                    super.getRequest().getSession().setAttribute("currentUser", user.toString());
                    manager.close();
                    return "successful";
                }

            } catch (SQLException e) {
                e.printStackTrace();
                manager.close();
                return "exceptionError";
            }


        }

        return "error";
    }

    public void processLogout(){
        if (super.getRequest().getSession().getAttribute("currentUser") != null){
            super.getRequest().getSession().removeAttribute("currentUser");
        }
    }
}
