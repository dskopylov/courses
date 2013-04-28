package ru.ifmo.de.courses.daos;

import ru.ifmo.de.courses.tools.MySQLManager;

/**
 * ru.ifmo.de.courses.daos.DAO.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */
public abstract class DAO {
    MySQLManager manager;

    public DAO(MySQLManager manager){
        this.manager = manager;
    }

    public MySQLManager getManager() {
        return manager;
    }

    public void setManager(MySQLManager manager) {
        this.manager = manager;
    }
}
