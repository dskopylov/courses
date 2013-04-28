package ru.ifmo.de.courses.tools;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Properties;
import java.util.Vector;

/**
 * ru.dskopylov.ifmo.tgallery.tools.MySQLManager.java
 * Created by: Dmitriy Kopylov (dima@cde.ifmo.ru)
 * (c) DLC NRU ITMO, 2011
 */
public class MySQLManager {
    private String url;
    private String login;
    private String password;

    private Connection con = null;

    public MySQLManager(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;

        String driver = "com.mysql.jdbc.Driver";

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Properties connInfo = new Properties();

        connInfo.put("useUnicode", "true");
        connInfo.put("characterEncoding", "utf-8");

        connInfo.put("user", this.login);
        connInfo.put("password", this.password);

        try {
            con = DriverManager.getConnection(url, connInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CachedRowSet exeQue(Object... params) {
        CachedRowSet crs = null;

        if (params.length < 1) {//пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ
            return null;
        }

        String sqlStatement = String.valueOf(params[0]);

        Vector<Object> parameters = new Vector<Object>();
        parameters.addAll(Arrays.asList(params).subList(1, params.length));


        ResultSet rs = null;

        try {
            CallableStatement callStm = prepareQuery(sqlStatement, parameters);

            rs = callStm.executeQuery();
            crs = new CachedRowSetImpl();
            crs.populate(rs);
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


        return crs;
    }

    public void exeUpd(Object... params) {
        if (params.length < 1) {//пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ
            return;
        }

        String sqlStatement = String.valueOf(params[0]);

        Vector<Object> parameters = new Vector<Object>();
        parameters.addAll(Arrays.asList(params).subList(1, params.length));



        try {
            CallableStatement callStm = prepareQuery(sqlStatement, parameters);

            callStm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }

    private CallableStatement prepareQuery(String statement, Vector param) {
        int j = 1;

        CallableStatement cs = null;


        try {

            //DEBUG
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.S");
            StringBuilder params = new StringBuilder();
            params.append(df.format(new Date(System.currentTimeMillis())));
            params.append(" ");
            params.append(statement);
            params.append(" ");
            for (Object p : param) {
                params.append(String.valueOf(p) + " ");
            }
            System.out.println(params.toString());
            //DEBUG


            if (con.isClosed()) {
                con = DriverManager.getConnection(url, login, password);
                cs = con.prepareCall(statement);
            } else {
                cs = con.prepareCall(statement);
            }
            for (Object o : param) {
                if (o.getClass().equals(Integer.class)) {
                    cs.setInt(j, Integer.parseInt(o.toString()));

                } else if (o.getClass().equals(Double.class)) {
                    cs.setDouble(j, Double.parseDouble(o.toString()));

                } else if (o.getClass().equals(FileInputStream.class)){
                    cs.setBinaryStream(j, (InputStream) o);

                } else {
                    cs.setString(j, o.toString());

                }

                j++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cs;
    }


    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
