package ru.ifmo.de.courses.renders;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import ru.ifmo.de.courses.AbstractRender;
import ru.ifmo.de.courses.pojo.Page;
import ru.ifmo.de.courses.tools.StringRoutine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 03.05.13
 * Time: 19:26
 * To change this template use File | Settings | File Templates.
 */
public class PageCourseRender extends AbstractRender {
    public PageCourseRender(HttpServletRequest request, HttpServletResponse response, VelocityEngine velocityEngine) {
        super(request, response, velocityEngine);
    }

    /**
     * Рендерит страницу с созданием курса
     *
     * @param page
     * @param error
     * @return
     */
    public Page renderCourseCreate(Page page, String error) {
        Template t = super.getVelocityEngine().getTemplate("courseCreate.vm", "utf-8");
        StringWriter sw = new StringWriter();
        VelocityContext context = new VelocityContext();

        if (error.startsWith("courseCreate")) {
            context.put("number", StringRoutine.isoToUtf(super.getRequest().getParameter("number")));
            context.put("name", StringRoutine.isoToUtf(super.getRequest().getParameter("name")));

            if (error.equals("courseCreateAlreadyExist")) {
                context.put("message", "Курс с таким номером существует!");

            } else if (error.equals("courseCreateEmptyFields")) {
                context.put("message", "Заполните необходимые поля!");

            } else if (error.equals("courseCreateSpacesInNumber")) {
                context.put("message", "В номере курса не может быть пробел!");

            } else if (error.equals("courseCreateNoUser")) {
                context.put("message", "Необходимо войти в систему!!");

            }
        } else {
            context.put("message", "");
            context.put("number", "");
            context.put("name", "");
        }

        t.merge(context, sw);
        page.setContent(sw.toString());
        return page;
    }
}
