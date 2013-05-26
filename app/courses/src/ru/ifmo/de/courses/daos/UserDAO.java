package ru.ifmo.de.courses.daos;

import ru.ifmo.de.courses.pojo.User;
import ru.ifmo.de.courses.tools.MySQLManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * ru.ifmo.de.courses.daos.UserDAO.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */
public class UserDAO extends DAO {
    public UserDAO(MySQLManager manager) {
        super(manager);
    }

    public List<User> getUsers(){
        return null;
    }

    public User getUserById(Integer id) throws SQLException {
        ResultSet rs = super.getManager().exeQue("SELECT * FROM users u WHERE u.`id` = ?", id);

        if (rs.next()){
            User user = new User(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(5));
            return user;
        }

        return null;
    }

    public User getUserByLoginPassword(String login, String password) throws SQLException {
        ResultSet rs = super.getManager().exeQue("SELECT * FROM users u WHERE u.login = ? AND u.password = md5(?)", login, password);

        if (rs.next()){
            return getUserById(rs.getInt(1));
        }

        return null;
    }

    public User getUserByLogin(String login) throws SQLException {
        ResultSet rs = super.getManager().exeQue("SELECT * FROM users u WHERE u.login = ?", login);

        if (rs.next()){
            return getUserById(rs.getInt(1));
        }

        return null;
    }

    public User getUserByEmail(String email) throws SQLException {
        ResultSet rs = super.getManager().exeQue("SELECT * FROM users u WHERE u.email = ?", email);

        if (rs.next()){
            return getUserById(rs.getInt(1));
        }

        return null;
    }

    public void addUser(User user){
        super.getManager().exeUpd("INSERT INTO users (login, password, name, email, group_id) VALUES (?, md5(?), ?, ?, 2)",
        user.getLogin(), user.getPassword(), user.getName(), user.getEmail());
    }
}
