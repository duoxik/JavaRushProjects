package com.duoxik.tasks.calculator;

import com.duoxik.tasks.calculator.exceptions.BracketsException;
import com.duoxik.tasks.calculator.exceptions.WrongExpressionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpressionFormater {

    private static final List<Character> NUMBERS = new ArrayList<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
    private static final List<Character> TRUE_SYMBOLS = new ArrayList<>(Arrays.asList('+', '*', '/', '(', ')', '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'));
    private static final List<Character> OPERATIONS_AND_BRACKETS = new ArrayList<>(Arrays.asList('+', '*', '/', '(', ')'));

    private final StringBuffer sbExpression;

    public ExpressionFormater(final String expression) {
        this.sbExpression = new StringBuffer(expression);
    }

    public String getFormattedString() throws WrongExpressionException {

        deleteSpaces();
        deleteEmptyBrackets();
        checkOnTrueSymbols();
        checkBrackets();
        addMultiplyBeforeAfterBracket();
        addPlusesBeforeMinuses();

        return sbExpression.toString();
    }

    private void checkBrackets() throws BracketsException {

        StringBuffer sbBrackets = new StringBuffer();
        for (int i = 0; i < sbExpression.length(); i++) {
            char currentChar = sbExpression.charAt(i);
            if (currentChar == ')' || currentChar == '(') {
                sbBrackets.append(currentChar);
            }
        }
        while (sbBrackets.length() != 0) {
            int indx = sbBrackets.indexOf("()");
            if (indx == -1)
                throw new BracketsException("Неверно указаны скобки в выражении.");
            sbBrackets.delete(indx, indx + 2);
        }
    }

    private void deleteSpaces() {

        int spaceIndx;
        while ((spaceIndx = sbExpression.indexOf(" ")) != -1) {
            sbExpression.deleteCharAt(spaceIndx);
        }
    }

    private void deleteEmptyBrackets() {

        int indxBrackets;
        while ((indxBrackets = sbExpression.indexOf("()")) != -1) {
            sbExpression.delete(indxBrackets, indxBrackets + 2);
        }
    }

    private void addPlusesBeforeMinuses() {

        int minusIndx = 0;
        while ((minusIndx = sbExpression.indexOf("-", minusIndx + 1))!= -1) {
            if (sbExpression.charAt(minusIndx - 1) == ')' || !OPERATIONS_AND_BRACKETS.contains(sbExpression.charAt(minusIndx - 1))) {
                sbExpression.insert(minusIndx, "+");
                minusIndx++;
            }
        }
    }

    private void checkOnTrueSymbols() throws WrongExpressionException {

        for (int i = 0; i < sbExpression.length(); i++) {
            if (!TRUE_SYMBOLS.contains(sbExpression.charAt(i))) {
                throw new WrongExpressionException("Заданы неверные символы для выражения.");
            }
        }
    }

    private void addMultiplyBeforeAfterBracket() {

        int bracketIndx = 0;
        while ((bracketIndx = sbExpression.indexOf("(", bracketIndx + 1)) != -1) {
            char currentChar = sbExpression.charAt(bracketIndx - 1);
            if (NUMBERS.contains(currentChar) || currentChar == '.') {
                sbExpression.insert(bracketIndx, '*');
                bracketIndx++;
            }
        }

        bracketIndx = 0;
        while ((bracketIndx = sbExpression.indexOf(")", bracketIndx + 1)) != -1) {
            if (bracketIndx + 1 != sbExpression.length()) {
                char currentChar = sbExpression.charAt(bracketIndx + 1);
                if (NUMBERS.contains(currentChar) || currentChar == '.') {
                    sbExpression.insert(bracketIndx + 1, '*');
                    bracketIndx++;
                }
            }
        }
    }
}
