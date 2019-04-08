package com.duoxik.tasks.calculatorV2;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

    private static Calculator solution = new Calculator();

    @Test
    public void expression1() {
        String expression = "1+(1+(1+1)*(1+1))*(1+1)+1";
        double expectedValue = 12;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression2() {
        String expression = "-2+(-2+(-2)-2*(2+2))";
        double expectedValue = -14;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression3() {
        String expression = "1+4/2/2+2^2+2*2-2^(2-1+1)";
        double expectedValue = 6;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression4() {
        String expression = "2^10+2^(5+5)";
        double expectedValue = 2048;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression5() {
        String expression = "10-2^(2-1+1)";
        double expectedValue = 6;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression6() {
        String expression = "1.01+(2.02-1+1/0.5*1.02)/0.1+0.25+41.1";
        double expectedValue = 72.96;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression7() {
        String expression = "-2-(-2-1-(-2)-(-2)-(-2-2-(-2)-2)-2-2)";
        double expectedValue = -3;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression8() {
        String expression = "-1*5^2";
        double expectedValue = -25;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression9() {
        String expression = "2*(589+((2454*0.1548/0.01*(-2+9^2))+((25*123.12+45877*25)+25))-547)";
        double expectedValue = 8302231.36;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression10() {
        String expression = "(-1 + (-2))";
        double expectedValue = -3;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression11() {
        String expression = "sin(2*(-5+1.5*4)+28)";
        double expectedValue = 0.5;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression12() {
        String expression = "tan(45)";
        double expectedValue = 1;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression13() {
        String expression = "tan(-45)";
        double expectedValue = -1;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression14() {
        String expression = "tan(44+sin(89-cos(180)^2))";
        double expectedValue = 1;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression15() {
        String expression = "sin(80+(2+(1+1))*(1+1)+2)";
        double expectedValue = 1;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression16() {
        String expression = "cos(3 + 19*3)";
        double expectedValue = 0.5;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression17() {
        String expression = "-sin(2*(-5+1.5*4)+28)";
        double expectedValue = -0.5;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }

    @Test
    public void expression18() {
        String expression = "sin(100)-sin(100)";
        double expectedValue = 0;
        double actualValue = solution.calculate(expression);
        double delta = 0.01;

        System.out.println(expression);
        Assert.assertEquals(expectedValue, actualValue, delta);
        System.out.println(true + "\n");
    }
}