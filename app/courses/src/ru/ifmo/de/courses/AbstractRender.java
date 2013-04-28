package ru.ifmo.de.courses;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * ru.ifmo.de.courses.AbstractRender.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */
public abstract class AbstractRender {
    HttpServletRequest request;

    HttpServletResponse response;

    VelocityEngine velocityEngine;

    public AbstractRender() {
    }

    public AbstractRender(HttpServletRequest request, HttpServletResponse response, VelocityEngine velocityEngine) {
        this.request = request;
        this.response = response;
        this.velocityEngine = velocityEngine;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
}
