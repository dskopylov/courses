package ru.ifmo.de.courses.tools;

import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 03.05.13
 * Time: 22:02
 * To change this template use File | Settings | File Templates.
 */
public class StringRoutine {
    public static String isoToUtf(String s){
        try {
            return new String(s.getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
