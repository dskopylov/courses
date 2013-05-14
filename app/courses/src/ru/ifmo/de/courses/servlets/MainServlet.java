package ru.ifmo.de.courses.servlets;

import com.sun.org.apache.bcel.internal.generic.FDIV;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import ru.ifmo.de.courses.actions.CourseAction;
import ru.ifmo.de.courses.actions.CurrentUserAction;
import ru.ifmo.de.courses.pojo.Page;
import ru.ifmo.de.courses.renders.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * ${PACKAGE_NAME}.${NAME}.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */
@javax.servlet.annotation.WebServlet(name = "MainServlet")
public class MainServlet extends HttpServlet {
    private VelocityEngine velocityEngine;

    //Настройки для всего приложения
    public static HashMap<String, String> appProp = new HashMap<String, String>();

    //Перевод для русского языка
    public static Properties ru = new Properties();

    //Перевод для английского языка
    public static Properties en = new Properties();


    public void init() {
        appProp.put("db.url", getServletContext().getInitParameter("db.url"));
        appProp.put("db.login", getServletContext().getInitParameter("db.login"));
        appProp.put("db.pass", getServletContext().getInitParameter("db.pass"));

        try {
            String fs = System.getProperty("file.separator");


            String path = getServletContext().getRealPath("") + fs + "WEB-INF" + fs + "messages" + fs;

            //ru
            FileInputStream fis = new FileInputStream(path + "ru" + fs + "ru.properties");
            ru.load(fis);


            //en
            fis = new FileInputStream(path + "en" + fs + "en.properties");
            en.load(fis);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //debug
        //init();

        Properties p = new Properties();

        String fs = System.getProperty("file.separator");

        p.setProperty("file.resource.loader.path", getServletContext().getRealPath("") + fs + "WEB-INF" + fs + "template" + fs);

        response.setContentType("text/html;charset=utf-8");

        velocityEngine = new VelocityEngine(p);

        String command = "";

        if (request.getParameter("act") != null) {//Запрошено действие

            if (request.getParameter("act").equals("login")) {//Вход пользователя
                CurrentUserAction currentUserAction = new CurrentUserAction(request, response, velocityEngine);
                String result = currentUserAction.processLogin();
                if (!result.equals("successful")) {
                    //Не удачный вход
                    command = "loginError";
                }
            } else if (request.getParameter("act").equals("logout")) {
                CurrentUserAction currentUserAction = new CurrentUserAction(request, response, velocityEngine);
                currentUserAction.processLogout();

            } else if (request.getParameter("act").equals("courseCreate")){
                CourseAction courseAction = new CourseAction(request, response, velocityEngine);
                command = courseAction.createCourse();

            } else if (request.getParameter("act").equals("coursePageUpdate")){

            }
        }

        parseRequestedPage(request, response, command);

    }


    /**
     * Метод парсит запрошенную страницу и вызывает необходимый рендерер
     */
    public void parseRequestedPage(HttpServletRequest request, HttpServletResponse response, String command) throws IOException {
        String requestedPath = request.getRequestURI();

        //Обрезаем из запрошенного URI contextPath
        requestedPath = requestedPath.replaceAll(request.getContextPath(), "");

        Page page = new Page();

        PageRender pageRender = new PageRender(request, response, velocityEngine);

        //Получаем информацию о пользователе
        CurrentUserAction currentUserAction = new CurrentUserAction(request, response, velocityEngine);
        page.setCurrentUser(currentUserAction.getCurrentUser());

        page.setContextPath(request.getContextPath());


        //Если запрошена главная страница приложения, необходимо определить язык и отфорвадить на нужный язык
        if (requestedPath.equals("/") || requestedPath.equals("/index.html")) {//Запрошена главная страница без языка

            String language = detectLanguage(request);

            response.sendRedirect(request.getContextPath() + "/" + language + "/index.html");

            //Определение запрошенного


        } else {//Сюда попадаем уже с языком в адресе

            String[] mas = requestedPath.split("/");

            if (mas.length > 1) {
                String lang = mas[1];
                if (!(lang.equals("ru") || lang.equals("en"))) {
                    //Если что-то неправильно то посылаем на русский
                    response.sendRedirect(request.getContextPath() + "/" + "ru" + "/index.html");

                } else {
                    page.setLanguage(lang);

                    if (lang.equals("ru")) {
                        page.setLang(ru);

                    } else if (lang.equals("en")) {
                        page.setLang(en);
                    }


                }
            }

            //После этого мы знаем язык в page.getLanguage(). Убираем его из requestedPath
            requestedPath = requestedPath.replaceAll(page.getLanguage() + "/", "");
            page.setRequestedPath(requestedPath);

            if (command.equals("")) {
                if (requestedPath.equals("/") || requestedPath.equals("/index.html")) {//Запрошена главная страница
                    MainPageRender mainPageRender = new MainPageRender(request, response, velocityEngine);
                    page = mainPageRender.renderMainPage(page);

                } else if (requestedPath.startsWith("/course/")) {//Страница из курсов
                    page = parseCoursePage(requestedPath, request, response, page);

                } else if (requestedPath.startsWith("/page/")) {//Другая страница
                    page = parsePagePage(requestedPath, request, response, page);

                } else { //Нет такой запрошенной страницы
                    ErrorRender errorRender = new ErrorRender(request, response, velocityEngine);

                    page = errorRender.renderError(page);

                }


//                { //Нет такой запрошенной страницы
//                    ErrorRender errorRender = new ErrorRender(request, response, velocityEngine);
//
//                    page = errorRender.renderError(page);
//                }
            } else {
                if (command.equals("loginError")) {
                    ErrorRender errorRender = new ErrorRender(request, response, velocityEngine);
                    page = errorRender.renderLoginError(page);

                } else if (command.startsWith("courseCreate")){//Команды по созданию курса

                    if (command.startsWith("courseCreateSuccessful")){//Курс создан, редиректим на страницу
                        response.sendRedirect(page.getContextPath() + "/" + page.getLanguage() + "/course/" + command.split("/")[1] + "/home/");

                    } else {
                        PageCourseRender render = new PageCourseRender(request, response, velocityEngine);
                        page = render.renderCourseCreate(page, command);
                    }
                }
            }

        }


        pageRender.renderPage(page);

    }

    /**
     * Парсит путь для страницы из курсов и вызывает соответствующий рендер
     * @param requestedPath
     * @param request
     * @param response
     * @param page
     * @return
     */
    private Page parseCoursePage(String requestedPath, HttpServletRequest request, HttpServletResponse response, Page page) {
        //Главная страница курсов
        if (requestedPath.equals("/course/")) {
            CourseListRender courseListRender = new CourseListRender(request, response, velocityEngine);
            page = courseListRender.renderListCourses(page);

        } else if (requestedPath.split("/").length == 3) {//Запрошена главная страница курса
            CourseRender courseRender = new CourseRender(request, response, velocityEngine);

            page = courseRender.renderMainPage(page, requestedPath.split("/")[2]);

        } else if (requestedPath.split("/").length == 4) {//Запрошена страница курса
            CourseRender courseRender = new CourseRender(request, response, velocityEngine);

            page = courseRender.renderPage(page, requestedPath.split("/")[2], requestedPath.split("/")[3]);

        } else if (requestedPath.split("/").length == 5) {//Запрошены либо подстраницы страницы, либо аттачи страницы
            CourseRender courseRender = new CourseRender(request, response, velocityEngine);
            //Должны определить, что запрошено
            String[] masC = requestedPath.split("/");

            if (masC[4].equals("edit")) {//Редактирование страницы
                page = courseRender.renderPageEdit(page, requestedPath.split("/")[2], requestedPath.split("/")[3]);

            } else if (masC[4].equals("history")) {//История страницы

            } else if (masC[4].equals("discus")) {//Обсуждение страницы

            } else {//Запрошен аттач страницы

            }

        }

        return page;
    }

    /**
     * Парсит путь для страницы из страниц и вызывает соответсвующий рендер
     * @param requestedPath
     * @param request
     * @param response
     * @param page
     * @return
     */
    private Page parsePagePage(String requestedPath, HttpServletRequest request, HttpServletResponse response, Page page) {
        if (requestedPath.equals("/page/createCourse/")){
            PageCourseRender render = new PageCourseRender(request, response, velocityEngine);
            page = render.renderCourseCreate(page, "");
        }
        return page;
    }

    private String detectLanguage(HttpServletRequest request) {
        return "ru";
    }
}
