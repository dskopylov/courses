package ru.ifmo.de.courses.renders;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import ru.ifmo.de.courses.AbstractRender;
import ru.ifmo.de.courses.pojo.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;

/**
 * ru.ifmo.de.courses.renders.UserAreaRenderer.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */
public class UserAreaRenderer extends AbstractRender{
    public UserAreaRenderer(HttpServletRequest request, HttpServletResponse response, VelocityEngine velocityEngine) {
        super(request, response, velocityEngine);
    }

    public Page renderUA(Page page){

        Template t = super.getVelocityEngine().getTemplate("userArea.vm", "utf-8");

        VelocityContext velocityContext = new VelocityContext();

        velocityContext.put("user", page.getCurrentUser());



        StringWriter sw = new StringWriter();

        t.merge(velocityContext, sw);

        page.setUserArea(sw.toString());

        return page;
    }
}
