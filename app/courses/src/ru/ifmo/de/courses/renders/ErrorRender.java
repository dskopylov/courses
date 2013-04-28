package ru.ifmo.de.courses.renders;

import org.apache.velocity.app.VelocityEngine;
import ru.ifmo.de.courses.AbstractRender;
import ru.ifmo.de.courses.pojo.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ru.ifmo.de.courses.renders.ErrorRender.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */
public class ErrorRender extends AbstractRender{
    public ErrorRender(HttpServletRequest request, HttpServletResponse response, VelocityEngine velocityEngine) {
        super(request, response, velocityEngine);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public Page renderError(Page page){

        page.setContent("Нет такой страницы!");

        return page;
    }

    public Page renderLoginError(Page page){
        page.setContent("Не правильный логин / пароль!");

        return page;
    }
}
