package com.duoxik.tasks.calculator;

import com.duoxik.tasks.calculator.exceptions.WrongExpressionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator {

    private static final List<Character> OPERATIONS_AND_BRACKETS = new ArrayList<>(Arrays.asList('+', '*', '/', '(', ')'));

    private final ExpressionFormater expressionFormater;

    public Calculator(final ExpressionFormater expressionFormater) {
        this.expressionFormater = expressionFormater;
    }

    public double calculate() throws WrongExpressionException {
        try {
            return Double.parseDouble(parse(expressionFormater.getFormattedString()));
        } catch (Exception e) {
            throw new WrongExpressionException("Указано недопустимое выражение.");
        }
    }

    private String parse(final String expression) throws WrongExpressionException {

        final int bracketEndIndx;
        if ((bracketEndIndx = expression.indexOf(")")) != -1) {

            final int bracketStartIndx = expression.substring(0, bracketEndIndx).lastIndexOf("(");
            final String result = expression.substring(0, bracketStartIndx)
                    + parse(expression.substring(bracketStartIndx + 1, bracketEndIndx))
                    + expression.substring(bracketEndIndx + 1);
            return parse(result);
        } else {

            final int signIndx = findIndxOperation(expression);
            if (signIndx != -1) {
                return parse(expressionSimplify(expression, signIndx));
            } else {
                return expression;
            }
        }
    }

    private String expressionSimplify(final String expression, final int signIndx) throws WrongExpressionException {

        int leftIndx = 0;
        int rightIndx = expression.length() - 1;

        for(int i = signIndx - 1; i >= 0; i--) {
            if (OPERATIONS_AND_BRACKETS.contains(expression.charAt(i))) {
                leftIndx = i + 1;
                break;
            }
        }

        for(int i = signIndx + 1; i < expression.length(); i++) {
            if (OPERATIONS_AND_BRACKETS.contains(expression.charAt(i))) {
                rightIndx = i - 1;
                break;
            }
        }

        if (signIndx == rightIndx || signIndx == leftIndx)
            throw new WrongExpressionException("Слева или справа от операции нет выражения.");


        final String simpleExpression = expression.substring(leftIndx, rightIndx + 1);
        final String simpleExpressionResult = calculateSimpleExpression(simpleExpression);

        return expression.substring(0, leftIndx) + simpleExpressionResult + expression.substring(rightIndx + 1);
    }

    private String calculateSimpleExpression(final String simpleExpression) {

        final int operIndx = findIndxOperation(simpleExpression);

        final double a = Double.parseDouble(simpleExpression.substring(0, operIndx));
        final double b = Double.parseDouble(simpleExpression.substring(operIndx + 1));

        if (simpleExpression.charAt(operIndx) == '*')
            return Double.toString(a * b);
        else if (simpleExpression.charAt(operIndx) == '/')
            return Double.toString(a / b);
        else
            return Double.toString(a + b);
    }

    private int findIndxOperation(final String expression) {

        int operIndx;
        if ((operIndx = expression.indexOf("*")) != -1)
            return operIndx;
        else if ((operIndx = expression.indexOf("/")) != -1)
            return operIndx;
        else if ((operIndx = expression.indexOf("+")) != -1)
            return operIndx;
        else
            return -1;
    }
}
