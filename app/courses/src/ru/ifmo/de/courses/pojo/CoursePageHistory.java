package ru.ifmo.de.courses.pojo;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 26.05.13
 * Time: 13:41
 * To change this template use File | Settings | File Templates.
 */
public class CoursePageHistory {
    private Integer id;

    private Integer user;

    private Integer version;

    private Integer pageId;

    private String dateTime;

    private String diff;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
