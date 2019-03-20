package com.duoxik.tasks.calculator;

import com.duoxik.tasks.calculator.exceptions.WrongExpressionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        ExpressionFormater expressionFormater;
        System.out.print("Введите выражение: ");
        while (true) {
            try {
                expressionFormater = new ExpressionFormater(in.readLine());
                System.out.println("Результат: " + new Calculator(expressionFormater).calculate());
                break;
            } catch (WrongExpressionException e) {
                System.out.println(e.getMessage());
                System.out.print("Попробуйте ещё раз: ");
            }
        }
    }
}
