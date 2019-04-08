package com.duoxik.tasks.calculatorV2;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class CalculatorTest {

    private static Calculator calculator = new Calculator();
    private static Map<String, Double> examples = new LinkedHashMap<>();

    static {
        examples.put("1+(1+(1+1)*(1+1))*(1+1)+1", 12d); //1
        examples.put("-2+(-2+(-2)-2*(2+2))", -14d); //2
        examples.put("1+4/2/2+2^2+2*2-2^(2-1+1)", 6d); //3
        examples.put("2^10+2^(5+5)", 2048d); //4
        examples.put("10-2^(2-1+1)", 6d); //5
        examples.put("1.01+(2.02-1+1/0.5*1.02)/0.1+0.25+41.1", 72.96); //6
        examples.put("-2-(-2-1-(-2)-(-2)-(-2-2-(-2)-2)-2-2)", -3d); //7
        examples.put("2*(589+((2454*0.1548/0.01*(-2+9^2))+((25*123.12+45877*25)+25))-547)", 8302231.36); //8
        examples.put("(-1 + (-2))", -3d); //9
        examples.put("sin(2*(-5+1.5*4)+28)", 0.5); //10

        examples.put("tan(45)", 1d); //11
        examples.put("tan(-45)", -1d); //12
        examples.put("tan(44+sin(89-cos(180)^2))", 1d); //13
        examples.put("sin(80+(2+(1+1))*(1+1)+2)", 1d); //14
        examples.put("cos(3 + 19*3)", 0.5); //15
        examples.put("-sin(2*(-5+1.5*4)+28)", -0.5); //16
        examples.put("sin(100)-sin(100)", 0d); //17
        examples.put("-(-22+22*2)", -22d); //18
        examples.put("-2^(-2)", -0.25); // 19
        examples.put("-(-2^(-2))+2+(-(-2^(-2)))", 2.5); //20

        examples.put("(-2)*(-2)", 4d); //21
        examples.put("(-2)/(-2)", 1d); //22
        examples.put("sin(-30)", -0.5); //23
        examples.put("cos(-30)", 0.87); //24
        examples.put("tan(-30)", -0.58); //26
        examples.put("2+8*(9/4-1.5)^(1+1)", 6.5); //26
        examples.put("sin(45) - cos(45)", 0d); //27
        examples.put("0/(-3)", 0d); //28
    }

    @Test
    public void validate() {

        double delta = 0.01;

        int count = 1;
        for (Map.Entry<String, Double> entry : examples.entrySet()) {

            String expression = entry.getKey();
            double expectedValue = entry.getValue();
            double actualValue = calculator.calculate(expression);

            System.out.println(count++ + ") " +expression);
            Assert.assertEquals(expectedValue, actualValue, delta);
            System.out.println("equal " + actualValue);
            System.out.println(true + "\n");
        }
    }

    @Test
    public void tempTest() {

        double delta = 0.01;
        String expression = "-(-2^(-2))+2+(-(-2^(-2)))";
        double expectedValue = 2.5;
        double actualValue = calculator.calculate(expression);

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println("equal " + actualValue);
        System.out.println(true + "\n");
    }
}