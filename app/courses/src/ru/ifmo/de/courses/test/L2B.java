package ru.ifmo.de.courses.test;

import difflib.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 03.05.13
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
public class L2B {
    private StringBuilder sb = new StringBuilder();

    StringBuilder l2b(List<?> l) {
        sb.delete(0, sb.length());
        for (Object object : l) {
            sb.append(object + "");
        }
        if (sb.length() >= 1) sb.deleteCharAt(sb.length() - 1); // last "\n"
        return sb;
    }


    public static void main(String[] args) {
        List<String> original = new LinkedList<String>();
        original.add("This part of the");
        original.add("document has stayed the");
        original.add("same from version to");
        original.add("version.  It shouldn't");
        original.add("be shown if it doesn't");
        original.add("change.  Otherwise, that");
        original.add("would not be helping to");
        original.add("compress the size of the");
        original.add("changes.");
        original.add("");
        original.add("This paragraph contains");
        original.add("text that is outdated.");
        original.add("It will be deleted in the");
        original.add("near future.");
        original.add("");
        original.add("It is important to spell");
        original.add("check this dokument. On");
        original.add("the other hand, a");
        original.add("misspelled word isn't");
        original.add("the end of the world.");
        original.add("Nothing in the rest of");
        original.add("this paragraph needs to");
        original.add("be changed. Things can");
        original.add("be added after it.");
        original.add("25 ***********************");
        original.add("26 ***********************");
        original.add("27 ****###**#***#**###****");
        original.add("28 ****#****##**#**#**#***");
        original.add("29 ****###**#*#*#**#**#***");
        original.add("30 ****#****#**##**#**#***");
        original.add("31 ****###**#***#**###****");
        original.add("32 ***********************");
        original.add("33 ***********************");
        original.add("25 ");
        original.add("This paragraph contains");
        original.add("important new additions");
        original.add("to this document.");

        List<String> revised = new LinkedList<String>();
        revised.add("This is an important");
        revised.add("notice! It should");
        revised.add("therefore be located at");
        revised.add("document!");
        revised.add("");
        revised.add("This part of the");
        revised.add("document has stayed the");
        revised.add("same from version to");
        revised.add("version.  It shouldn't");
        revised.add("be shown if it doesn't");
        revised.add("change.  Otherwise, that");
        revised.add("would not be helping to");
        revised.add("compress anything.");
        revised.add("");
        revised.add("It is important to spell");
        revised.add("check this document. On");
        revised.add("the other hand, a");
        revised.add("misspelled word isn't");
        revised.add("the end of the world.");
        revised.add("Nothing in the rest of");
        revised.add("this paragraph needs to");
        revised.add("be changed. Things can");
        revised.add("be added after it.");
        revised.add("");
        revised.add("This paragraph contains");
        revised.add("important new additions");
        revised.add("to this document.");

        String[] cors = new String[]{"palegreen", "khaki", "pink", "moccasin", "lightskyblue", "lightyellow", "coral", "aliceblue", "yellowgreen", "beige", "lightpink"};
        StringBuilder tl = new StringBuilder();
        StringBuilder tr = new StringBuilder();
        Patch patch = DiffUtils.diff(original, revised);
        List<Delta> deltas = patch.getDeltas();
        L2B l2B = new L2B();
        int cori = 0;
        int last = 0;
        for (Delta delta : deltas) {
            if (last + 1 < delta.getOriginal().getPosition()) { // not continuous
                tl.append("<span style='font-size:smaller;'>\n");
                tr.append("<span style='font-size:smaller;'>\n");
                for (int i = last + 1; i < delta.getOriginal().getPosition(); i++) {
                    tl.append(original.get(i) + "\n");
                    tr.append(original.get(i) + "\n");
                }
                tl.append("</span>\n");
                tr.append("</span>\n");
            }
            List<?> or = delta.getOriginal().getLines();
            System.out.println("<span style='background-color:" + cors[cori] + ";'>\n" + l2B.l2b(or) + "\n</span>");
            tl.append("<span style='background-color:" + cors[cori] + ";'>\n" + l2B.l2b(or) + "\n</span>");
            List<?> re = delta.getRevised().getLines();
            System.out.println("<span style='background-color:" + cors[cori] + ";'>\n" + l2B.l2b(re) + "\n</span>");
            tr.append("<span style='background-color:" + cors[cori] + ";'>\n" + l2B.l2b(re) + "\n</span>");
            cori = (cori < cors.length) ? cori + 1 : 0;
            last = delta.getOriginal().last();
        }
        if (last + 1 < original.size()) { //last is not delta
            tl.append("<span style='font-size:smaller;'>\n");
            tr.append("<span style='font-size:smaller;'>\n");
            for (int i = last + 1; i < original.size(); i++) {
                tl.append(original.get(i) + "\n");
                tr.append(original.get(i) + "\n");
            }
            tl.append("</span>\n");
            tr.append("</span>\n");
        }

        System.out.println("<html><table><tr><td style='vertical-align:top;'>" + tl.toString() + "</td><td style='vertical-align:top;'>" + tr.toString() + "</td></tr></table></html>");
    }
}
