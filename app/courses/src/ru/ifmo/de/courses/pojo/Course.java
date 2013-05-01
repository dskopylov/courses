package ru.ifmo.de.courses.pojo;

import java.util.LinkedList;
import java.util.List;

/**
 * ru.ifmo.de.courses.pojo.Course.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */
public class Course {
    private Integer id = -1;

    private String number = "";

    private String name = "";

    private CoursePage mainPage = null;

    private List<CoursePage> pages = new LinkedList<CoursePage>();

    //Текущая страница курса
    private CoursePage curr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoursePage getMainPage() {
        return mainPage;
    }

    public void setMainPage(CoursePage mainPage) {
        this.mainPage = mainPage;
    }

    public List<CoursePage> getPages() {
        return pages;
    }

    public void setPages(List<CoursePage> pages) {
        this.pages = pages;
    }

    public CoursePage getCurr() {
        return curr;
    }

    public void setCurr(CoursePage curr) {
        this.curr = curr;
    }
}
