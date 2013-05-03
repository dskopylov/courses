package ru.ifmo.de.courses.tools;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import difflib.PatchFailedException;
import org.apache.commons.lang.text.StrBuilder;
import org.xml.sax.SAXNotRecognizedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 03.05.13
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
public class DiffTool {
    /**
     * Возвращает разницу между original и newStr
     * @param original старая строка
     * @param newStr новая строка
     * @return
     */
    public static String getDiff(String original, String newStr){
        List<String> originList = stringToLines(original);
        List<String> newStrList = stringToLines(newStr);

        Patch patch = DiffUtils.diff(originList, newStrList);
        StringBuilder sb = new StringBuilder();

        List<String> unidiff = DiffUtils.generateUnifiedDiff("Before", "After", originList, patch, 0);
        for (String string : unidiff) {
            sb.append(string).append("\n");
        }

        return sb.toString();
    }

    /**
     * Применяет к originalStr патч patchStr
     * @param originalStr
     * @param patchStr
     * @return
     */
    public static String applyPatch(String originalStr, String patchStr){
        StringBuilder sb = new StringBuilder();

        List<String> originList = stringToLines(originalStr);
        List<String> patchList = stringToLines(patchStr);

        Patch<String> patch = DiffUtils.parseUnifiedDiff(patchList);
        try {
            List<String> resultList = patch.applyTo(originList);

            for (String string : resultList) {
                sb.append(string).append("\n");
            }

            return sb.toString();

        } catch (PatchFailedException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }


    /**
     * Преобразует строку с переносом \n в лист строк
     * @param str
     * @return
     */
    private static List<String> stringToLines(String str) {
        List<String> lines = new LinkedList<String>();
        String line = "";
        try {
            BufferedReader in = new BufferedReader(new StringReader(str));
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void main(String[] args){
        String patch = getDiff("Копылов\nДмитрий\nСергеевич", "Дмитрий\nИванов");
        System.out.println(patch);

        System.out.println("---");

        System.out.println(applyPatch("Копылов\nДмитрий\nСергеевич", patch));


    }
}
