package ru.ifmo.de.courses.pojo;

/**
 * ru.ifmo.de.courses.pojo.Page.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */

import java.util.Properties;

/**
 * Представляет собой внутреннее содержимое страницы
 */
public class Page {

    //Заголовок страницы
    private String title = "";

    private String name = "";

    //Содержание страницы
    private String content = "";

    //Отрендеренная поле с информацией для пользователя
    private String userArea = "";

    //Язык, на котором запрошена страница
    private String language = "";

    //Текущий пользователь
    private User currentUser;

    //Путь деплоинга приложения
    private String contextPath = "";

    //Язык, на котором нужно рендерить страницу
    private Properties lang;

    //Путь до страницы без языка и деплоинга
    private String requestedPath;

    public Page(String title, String name) {
        this.title = title;
        this.name = name;
    }

    public Page() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserArea() {
        return userArea;
    }

    public void setUserArea(String userArea) {
        this.userArea = userArea;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public Properties getLang() {
        return lang;
    }

    public void setLang(Properties lang) {
        this.lang = lang;
    }

    public String getRequestedPath() {
        return requestedPath;
    }

    public void setRequestedPath(String requestedPath) {
        this.requestedPath = requestedPath;
    }
}
