package com.duoxik.tasks.armstrong;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*
Алгоритмы-числа
*/

public class Armstrong {

    private static final int MAX_NUMBER_LENGTH = 20;
    private static final long[][] TABLE_DEGREE = new long[10][MAX_NUMBER_LENGTH];
    private static final long[] MAX_NUM_OF_DEGREE = new long[MAX_NUMBER_LENGTH];

    static {

        for (int degree = 0; degree < MAX_NUMBER_LENGTH; degree++)
            TABLE_DEGREE[0][degree] = 0;

        for (int degree = 0; degree < MAX_NUMBER_LENGTH; degree++)
            TABLE_DEGREE[1][degree] = 1;

        for (int currentNum = 2; currentNum < TABLE_DEGREE.length; currentNum++) {
            for (int degree = 0; degree < MAX_NUMBER_LENGTH; degree++) {
                TABLE_DEGREE[currentNum][degree] = currentNum;
                for (int i = 1; i < degree; i++)
                    TABLE_DEGREE[currentNum][degree] = TABLE_DEGREE[currentNum][degree] * currentNum;
            }
        }

        MAX_NUM_OF_DEGREE[0] = 0;
        MAX_NUM_OF_DEGREE[19] = Long.MAX_VALUE;

        for (int degree = 1; degree < MAX_NUMBER_LENGTH - 1; degree++)
            MAX_NUM_OF_DEGREE[degree] = MAX_NUM_OF_DEGREE[degree - 1] * 10 + 9;
    }

    public static List<Long> getArmstrongList(long endIndex) {

        return getArmstrongListByDegree(endIndex, 1, Long.toString(endIndex).length());
    }

    public static List<Long> getArmstrongListParallel(final long endIndex) throws InterruptedException, ExecutionException {

        List<Long> resultList = new ArrayList<>(100);

        if (endIndex <= 0) return resultList;

        int endDegree = Long.toString(endIndex).length();

        if (endDegree > 12) {

            final ExecutorService executorService = Executors.newWorkStealingPool();
            final List<Future<List<Long>>> futures = new ArrayList<>();

            futures.add(executorService.submit(new Callable<List<Long>>() {
                @Override
                public List<Long> call() {
                    return Armstrong.getArmstrongListByDegree(endIndex, 1, 12);
                }
            }));

            for (int i = 13; i <= endDegree; i++) {

                final int degree = i;

                futures.add(executorService.submit(new Callable<List<Long>>() {
                    @Override
                    public List<Long> call() {
                        return Armstrong.getArmstrongListByDegree(endIndex, degree, degree);
                    }
                }));
            }

            for (Future<List<Long>> future : futures)
                resultList.addAll(future.get());

        } else {
            resultList = getArmstrongListByDegree(endIndex, 1, endDegree);
        }

        return resultList;
    }

    private static List<Long> getArmstrongListByDegree(long endIndex, int startDegree, int endDegree) {

        List<Long> resultList = new ArrayList<>();

        for (int degree = startDegree; degree <= endDegree; degree++) {

            long currentNumber = 1;

            while ((currentNumber < endIndex) && (currentNumber <= MAX_NUM_OF_DEGREE[degree])) {

                long armstrongNumber = getArmstrongNumber(currentNumber, degree);

                if (armstrongNumber != -1 && armstrongNumber < endIndex)
                    resultList.add(armstrongNumber);

                currentNumber = getNextNum(currentNumber);
            }
        }

        return resultList;
    }

    private static long getNextNum(long number) {

        if (number++ % 10 != 9)
            return number;
        else {

            long lastFigure = number % 10;
            int countFigure = 0;

            while (lastFigure == 0) {
                number = number / 10;
                lastFigure = number % 10;
                countFigure++;
            }

            StringBuffer sbNumber = new StringBuffer(Long.toString(number));

            for (int i = 0; i < countFigure; i++)
                sbNumber.append(lastFigure);

            try {
                return Long.parseLong(sbNumber.toString());
            } catch (NumberFormatException e) {
                return Long.MAX_VALUE;
            }
        }
    }

    private static long getArmstrongNumber(long N, int degree) {

        long armSumm = getArmstrongSum(N, degree);
        return (armSumm != -1
                && isArmstrongNumber(armSumm)
                && isDegreeNumber(armSumm, degree)) ? armSumm : -1;
    }

    private static long getArmstrongSum(long N, int degree) {

        long currentNumber = N;
        long result = 0;

        while (currentNumber != 0) {

            result += TABLE_DEGREE[(int) (currentNumber % 10)][degree];
            if (result < 0) return -1;
            currentNumber /= 10;
        }

        return result;
    }

    private static boolean isDegreeNumber(long N, int inputDegree) {

        int numberDegree = Long.toString(N).length();
        return numberDegree == inputDegree ? true : false;
    }

    private static boolean isArmstrongNumber(long N) {

        int degree = Long.toString(N).length();
        return getArmstrongSum(N, degree) == N ? true : false;
    }
}
