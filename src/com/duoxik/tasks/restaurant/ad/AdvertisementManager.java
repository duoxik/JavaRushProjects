package com.duoxik.tasks.restaurant.ad;

import com.duoxik.tasks.restaurant.ConsoleHelper;
import com.duoxik.tasks.restaurant.statistic.StatisticManager;
import com.duoxik.tasks.restaurant.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.List;


public class AdvertisementManager {

    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();

    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() {

        List<Advertisement> availableAdsList = getAvailableVideosSet();

        if (availableAdsList.isEmpty())
            throw new NoVideoAvailableException();

        AdvertisementComputer computer = new AdvertisementComputer(timeSeconds, availableAdsList);

        List<Advertisement> optimalAdsSet = computer.getOptimalAdsSet();

        if (optimalAdsSet.isEmpty())
            throw new NoVideoAvailableException();

        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(optimalAdsSet, computer.getAdsPrice(), computer.getAdsTimeSeconds()));

        for (Advertisement ad : optimalAdsSet) {
            ConsoleHelper.writeMessage(ad.toString());
            ad.revalidate();
        }
    }

    private List<Advertisement> getAvailableVideosSet() {
        List<Advertisement> availableAdsList = new ArrayList<>();

        for (Advertisement ad : storage.list())
            if (ad.getHits() > 0)
                availableAdsList.add(ad);

        return availableAdsList;
    }
}
