package com.duoxik.tasks.calculatorV2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

    public static String removeSpaces(String expression) {

        StringBuilder sb = new StringBuilder(expression);
        for (int i = 0; i < sb.length(); i++)
            if (sb.charAt(i) == ' ')
                sb.deleteCharAt(i--);

        return sb.toString();
    }

    public static String insertPlusesBeforeNumToDegree(String expression) {
        Pattern pattern = Pattern.compile("\\d[-+*/]\\d+[.]?\\d*[\\^]");
        Matcher matcher;

        while ((matcher = pattern.matcher(expression)).find())
            expression = expression.substring(0, matcher.start() + 2)
                    + "+" + expression.substring(matcher.start() + 2);


        pattern = Pattern.compile("^\\d+[.]?\\d*[\\^]");
        while ((matcher = pattern.matcher(expression)).find())
            expression = expression.substring(0, matcher.start())
                    + "+" + expression.substring(matcher.start());

        return expression;
    }
}
