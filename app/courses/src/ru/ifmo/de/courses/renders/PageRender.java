package ru.ifmo.de.courses.renders;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import ru.ifmo.de.courses.AbstractRender;
import ru.ifmo.de.courses.pojo.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ru.ifmo.de.courses.renders.PageRender.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */
public class PageRender extends AbstractRender{
    public PageRender(HttpServletRequest request, HttpServletResponse response, VelocityEngine velocityEngine) {
        super(request, response, velocityEngine);
    }

    /**
     * Рендерит страницу. В метод придет готовая page без userArea
     */
    public void renderPage(Page page){
        //Должны взять главный шаблон, отрендерить userArea и все совместить и отдать в response.getWriter()
        Template t = super.getVelocityEngine().getTemplate("main.vm", "utf-8");

        VelocityContext context = new VelocityContext();

        UserAreaRenderer userAreaRenderer = new UserAreaRenderer(super.getRequest(), super.getResponse(), super.getVelocityEngine());

        page = userAreaRenderer.renderUA(page);

        page.setUserArea(page.getUserArea());

        context.put("page", page);

        try {
            t.merge(context, super.getResponse().getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
