package ru.ifmo.de.courses.test;

import difflib.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 03.05.13
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args){
//        List<String> original = new ArrayList<String>();
//        List<String> revised  = new ArrayList<String>();
//
//        original.add("Dima");
//        original.add("Test");
//        original.add("Test");
//
//        revised.add("Kopylov");
//        revised.add("Kopylov");
//
//        // Compute diff. Get the Patch object. Patch is the container for computed deltas.
//        Patch<String> patch = DiffUtils.diff(original, revised);
//
//        for (Delta<String> delta: patch.getDeltas()) {
//            System.out.println(delta);
//        }


        final List<String> original = Arrays.asList("//Заголовок страницы\n" +
                "    private String title = \"\";\n" +
                "\n" +
                "    private String name = \"\";\n" +
                "\n" +
                "    //Содержание страницы\n" +
                "    private String content = \"\";\n" +
                "\n" +
                "    //Отрендеренная поле с информацией для пользователя\n" +
                "    private String userArea = \"\";\n" +
                "\n" +
                "    //Язык, на котором запрошена страница\n" +
                "    private String language = \"\";\n" +
                "\n" +
                "    //Текущий пользователь\n" +
                "    private User currentUser;\n" +
                "\n" +
                "    //Путь деплоинга приложения\n" +
                "    private String contextPath = \"\";\n" +
                "\n" +
                "    //Язык, на котором нужно рендерить страницу\n" +
                "    private Properties lang;".split("\n"));


        final List<String> revised = Arrays.asList("//Заголовок страницы\n" +
                "    private String title = \"\";\n" +
                "\n" +
                "    private String name = \"\";\n" +
                "\n" +
                "    private String content = \"\";\n" +
                "\n" +
                "    //Отрендеренная поле с информацией для пользователя\n" +
                "    private String userArea = \"\";\n" +
                "\n" +
                "    private String language = \"\";\n" +
                "\n" +
                "    //Текущий пользователь\n" +
                "    private User currentUser;\n" +
                "\n" +
                "    //Путь деплоинга приложения\n" +
                "    private String contextPath = \"\";\n" +
                "\n" +
                "    //Язык, на котором нужно рендерить страницу\n" +
                "    private Properties lang;".split("\n"));

        final StringBuilder sb = new StringBuilder();
        final DiffRowGenerator.Builder builder = new DiffRowGenerator.Builder();
        final DiffRowGenerator dfg = builder.build();
        final List<DiffRow> rows = dfg.generateDiffRows(original, revised);
        for (final DiffRow diffRow : rows)
        {
            if (diffRow.getTag().equals(DiffRow.Tag.INSERT)) // or use switch*
            {
                sb.append("<div class='insert'>" + diffRow.getNewLine() +  "</div>");
            }
            else if (diffRow.getTag().equals(DiffRow.Tag.DELETE))
            {
                sb.append("<div class='del'>"  + diffRow.getOldLine() +  "</div>");
            }
            else if (diffRow.getTag().equals(DiffRow.Tag.CHANGE))
            {
                sb.append("<div class='mod'>");
                sb.append("\t<div class='mc'>" +  diffRow.getOldLine() +  "</div>");
                sb.append("\t<div class='mc'>" +  diffRow.getNewLine()  + "</div>");
                sb.append("</div>");
            }
        }

        System.out.println(sb.toString());
    }
}
