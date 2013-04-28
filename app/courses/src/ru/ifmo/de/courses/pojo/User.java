package ru.ifmo.de.courses.pojo;

/**
 * ru.ifmo.de.courses.pojo.User.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */

/**
 * Информация о пользователе
 */
public class User {
    private boolean auth = false;

    private Integer id = -1;

    private String login = "";

    private String password = "";

    private String name = "";

    private String email = "";

    public User(){

    }

    public User(Integer id, String login, String name, String email) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.email = email;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String toString(){
        return id.toString() + "/" + login + "/" + name + "/" + email;
    }

    public static User parseUser(String str){
        User user = new User();
        String[] uMas = str.split("/");
        user.setId(Integer.valueOf(uMas[0]));
        user.setLogin(uMas[1]);
        user.setName(uMas[2]);
        user.setEmail(uMas[3]);

        return user;
    }
}
