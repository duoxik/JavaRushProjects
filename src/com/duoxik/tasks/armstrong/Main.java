package com.duoxik.tasks.armstrong;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        long inputNumber = Long.MAX_VALUE;

        Date startTimer1 = new Date();
        List<Long> resultList = Armstrong.getArmstrongList(inputNumber);
        Collections.sort(resultList);
        Date endTimer1 = new Date();

        System.out.println(resultList);

        long time1 = endTimer1.getTime() - startTimer1.getTime();
        System.out.println("\nВремя выполнения без потоков: " + time1 + "ms");

        System.out.println("\n------------------------------------------------\n");

        Date startTimer2 = new Date();
        List<Long> resultList2 = Armstrong.getArmstrongListParallel(inputNumber);
        Date endTimer2 = new Date();
        Collections.sort(resultList2);
        System.out.println(resultList2);

        long time2 = endTimer2.getTime() - startTimer2.getTime();
        System.out.println("\nВремя выполнения c потоками: " + time2 + "ms");
    }
}
