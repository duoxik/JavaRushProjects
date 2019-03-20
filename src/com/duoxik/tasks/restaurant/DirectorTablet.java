package com.duoxik.tasks.restaurant;

import com.duoxik.tasks.restaurant.ad.Advertisement;
import com.duoxik.tasks.restaurant.ad.StatisticAdvertisementManager;
import com.duoxik.tasks.restaurant.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.*;

public class DirectorTablet {

    private final StatisticManager statisticManager = StatisticManager.getInstance();
    private final StatisticAdvertisementManager statisticAdvertisementManager
            = StatisticAdvertisementManager.getInstance();


    public void printAdvertisementProfit() {

        Map<Date, Long> totalDaysAmount = statisticManager.getAdvertisementProfit();

        long totalAmount = 0;
        for (Map.Entry<Date, Long> entry : totalDaysAmount.entrySet()) {
            ConsoleHelper.writeMessage(String.format("%s - %.2f", getFormattedDateString(entry.getKey()), entry.getValue() / 100.0));
            totalAmount += entry.getValue();
        }

        ConsoleHelper.writeMessage(String.format("Total - %.2f", totalAmount / 100.0));
    }

    public void printCookWorkloading() {

        Map<Date, Map<String, Integer>> cooksWorkloadingDays = statisticManager.getCooksWorkloading();

        for (Map.Entry<Date, Map<String, Integer>> firstEntry : cooksWorkloadingDays.entrySet()) {
            String date = getFormattedDateString(firstEntry.getKey());
            ConsoleHelper.writeMessage(date);
            for (Map.Entry<String, Integer> secondEntry : firstEntry.getValue().entrySet())
                ConsoleHelper.writeMessage(String.format("%s - %d min", secondEntry.getKey(), (int) Math.ceil(secondEntry.getValue() / 60.0)));
            ConsoleHelper.writeMessage("");
        }
    }

    private String getFormattedDateString(Date date) {
        return new SimpleDateFormat("dd-MMM-yyyy").format(date);
    }

    public void printActiveVideoSet() {
        List<Advertisement> activeVideoSet = statisticAdvertisementManager.getActiveVideoSet();

        Collections.sort(activeVideoSet, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        for (Advertisement ads : activeVideoSet)
            ConsoleHelper.writeMessage(String.format("%s - %s", ads.getName(), ads.getHits()));
    }

    public void printArchivedVideoSet() {
        List<Advertisement> archivedVideoSet = statisticAdvertisementManager.getArchivedVideoSet();

        Collections.sort(archivedVideoSet, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        for (Advertisement ads : archivedVideoSet)
            ConsoleHelper.writeMessage(ads.getName());
    }
}
