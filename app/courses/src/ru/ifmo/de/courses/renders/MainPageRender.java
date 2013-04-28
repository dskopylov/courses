package ru.ifmo.de.courses.renders;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import ru.ifmo.de.courses.AbstractRender;
import ru.ifmo.de.courses.pojo.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;

/**
 * ru.ifmo.de.courses.renders.MainPageRender.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */
public class MainPageRender extends AbstractRender {

    /**
     * Вернет страницу с заголовком и конетном
     * @param page
     * @return
     */
    public Page renderMainPage(Page page){

        Template t = super.getVelocityEngine().getTemplate("index.vm", "utf-8");

        VelocityContext velocityContext = new VelocityContext();

        velocityContext.put("mainPage", "Главная страница");

        StringWriter sw = new StringWriter();

        t.merge(velocityContext, sw);


        page.setTitle("Главная страница");
        page.setContent(sw.toString());

        return page;
    }

    public MainPageRender(HttpServletRequest request, HttpServletResponse response, VelocityEngine velocityEngine) {
        super(request, response, velocityEngine);
    }

}
