package com.duoxik.tasks.calculatorV2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Рекурсия для мат. выражения
*/
public class Calculator {

    public double calculate(final String expression) {

        int bracketEndIndex;
        if ((bracketEndIndex = expression.indexOf(")")) != -1) {

            int bracketStartIndex = expression.substring(0, bracketEndIndex).lastIndexOf("(");
            String innerBracketsExpression = expression.substring(bracketStartIndex + 1, bracketEndIndex);
            String calculatingResult = calculateExpressionWithoutBrackets(innerBracketsExpression);

            String newExpression = expression.substring(0, bracketStartIndex) + calculatingResult + expression.substring(bracketEndIndex + 1);

            return calculate(newExpression);
        } else {
            return Operation.round(Double.parseDouble(calculateExpressionWithoutBrackets(expression)));
        }
    }

    private String calculateExpressionWithoutBrackets(String expression) {

        expression = StringHelper.removeSpaces(expression);
        expression = StringHelper.insertPlusesBeforeNumToDegree(expression);

        while (!isNumber(expression))
            expression = simplifyExpression(expression);
        return expression;
    }

    private String simplifyExpression(String expression) {

        for (Pattern pattern : Operation.OPERATIONS_PRIORITY_PATTERN_LIST) {
            Matcher matcher = pattern.matcher(expression);
            if (matcher.find()) {

                String simpleExpression = expression.substring(matcher.start(), matcher.end());
                String result = parseSimpleExpression(Operation.getOperation(simpleExpression), simpleExpression);
                return expression.substring(0, matcher.start()) + result + expression.substring(matcher.end());
            }
        }

        return defineSignOfExpression(expression);
    }

    private String parseSimpleExpression(Operation operation, String simpleExpression) {

        double result;
        switch (operation) {
            case COSINE:
            case SINUS:
            case TANGENT:
                double a = Double.parseDouble(simpleExpression.substring(3));
                result = operation.calculate(a);
                break;
            case MULTIPLY:
            case DIVISION:
            case PLUS:
            case MINUS:
            case DEGREE:
                int operationIndex = simpleExpression.indexOf(operation.toString(), 1);
                double b = Double.parseDouble(simpleExpression.substring(0, operationIndex));
                double c = Double.parseDouble(simpleExpression.substring(operationIndex + 1));
                result = operation.calculate(b, c);
                break;
            default:
                throw new NumberFormatException();
        }

        return Double.toString(result);
    }

    private String defineSignOfExpression(String expression) {

        Matcher matcher = Pattern.compile("\\d+[.]?\\d*").matcher(expression);

        if (!matcher.find())
            throw new NumberFormatException();

        return (getSignOfExpression(expression.substring(0, matcher.start())) == '-') ?
                '-' + expression.substring(matcher.start()) : expression.substring(matcher.start());
    }

    private char getSignOfExpression(String signs) {

        if (signs.length() == 0)
            return '+';

        int countMinuses = 0;
        for (char sign : signs.toCharArray())
            if (sign == '-')
                countMinuses++;

        return (countMinuses == 0 || countMinuses % 2 == 0) ? '+' : '-';
    }

    private boolean isNumber(final String expression) {
        try {
            Double.parseDouble(expression);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }
}
