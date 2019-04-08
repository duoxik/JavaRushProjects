package com.duoxik.tasks.calculatorV2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public enum Operation {
    SINUS("sin"), COSINE("cos"), TANGENT("tan"),
    DEGREE("^"), MULTIPLY("*"), DIVISION("/"),
    PLUS("+"), MINUS("-");

    public static final List<Pattern> OPERATIONS_PRIORITY_PATTERN_LIST = new ArrayList<>();
    public static final Map<Operation, Pattern> OPERATIONS_PATTERN_MAP = new HashMap<>();

    private String operation;

    Operation(String operation) {
        this.operation = operation;
    }

    static {
        OPERATIONS_PRIORITY_PATTERN_LIST.add(Pattern.compile("(cos|sin|tan)[-]?(\\d+[.]?\\d*)"));
        OPERATIONS_PRIORITY_PATTERN_LIST.add(Pattern.compile("[-+]?(\\d+[.]?\\d*)[\\^][-]?(\\d+[.]?\\d*)"));
        OPERATIONS_PRIORITY_PATTERN_LIST.add(Pattern.compile("[-]?(\\d+[.]?\\d*)[*/][-]?(\\d+[.]?\\d*)"));
        OPERATIONS_PRIORITY_PATTERN_LIST.add(Pattern.compile("[-]?(\\d+[.]?\\d*)[+-][-]?(\\d+[.]?\\d*)"));

        OPERATIONS_PATTERN_MAP.put(SINUS, Pattern.compile("(sin)[-]?(\\d+[.]?\\d*)"));
        OPERATIONS_PATTERN_MAP.put(COSINE, Pattern.compile("(cos)[-]?(\\d+[.]?\\d*)"));
        OPERATIONS_PATTERN_MAP.put(TANGENT, Pattern.compile("(tan)[-]?(\\d+[.]?\\d*)"));
        OPERATIONS_PATTERN_MAP.put(DEGREE, Pattern.compile("[-+](\\d+[.]?\\d*)[\\^][-]?(\\d+[.]?\\d*)"));
        OPERATIONS_PATTERN_MAP.put(MULTIPLY, Pattern.compile("[-]?(\\d+[.]?\\d*)[*][-]?(\\d+[.]?\\d*)"));
        OPERATIONS_PATTERN_MAP.put(DIVISION, Pattern.compile("[-]?(\\d+[.]?\\d*)[/][-]?(\\d+[.]?\\d*)"));
        OPERATIONS_PATTERN_MAP.put(PLUS, Pattern.compile("[-]?(\\d+[.]?\\d*)[+][-]?(\\d+[.]?\\d*)"));
        OPERATIONS_PATTERN_MAP.put(MINUS, Pattern.compile("[-]?(\\d+[.]?\\d*)[-][-]?(\\d+[.]?\\d*)"));
    }

    public static Operation getOperation(String simpleExpression) {

        for (Operation operation : values()) {
            Pattern pattern = OPERATIONS_PATTERN_MAP.get(operation);
            if (pattern.matcher(simpleExpression).find())
                return operation;
        }

        return null;
    }

    public static double round(double a) {
        return (double) Math.round(a * 100) / 100;
    }

    public double calculate(double a, double b) {

        switch (this) {
            case DEGREE:
                return Math.pow(a, b);
            case MULTIPLY:
                return a * b;
            case DIVISION:
                return a / b;
            case PLUS:
                return a + b;
            case MINUS:
                return a - b;
            default:
                throw new NumberFormatException();
        }
    }

    public double calculate(double a) {

        switch (this) {
            case SINUS:
                return Math.sin(a * Math.PI / 180);
            case COSINE:
                return Math.cos(a * Math.PI / 180);
            case TANGENT:
                return Math.tan(a * Math.PI / 180);
            default:
                throw new NumberFormatException();
        }
    }

    @Override
    public String toString() {
        return operation;
    }
}
