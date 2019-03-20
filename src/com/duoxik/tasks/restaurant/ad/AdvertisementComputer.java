package com.duoxik.tasks.restaurant.ad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdvertisementComputer {
    private List<Advertisement> storageAdsList;
    private int maxTimeSeconds;

    private List<Advertisement> optimalAdsSet;
    private long bestAdsPrice;
    private int bestAdsTimeSeconds;

    public AdvertisementComputer(int maxTimeSeconds, List<Advertisement> storageAdsList) {
        this.maxTimeSeconds = maxTimeSeconds;
        this.storageAdsList = storageAdsList;
        this.optimalAdsSet = new ArrayList<>();

        computeBestAdsList(storageAdsList);
        sortAdsByAmount();
    }

    public List<Advertisement> getOptimalAdsSet() {
        return optimalAdsSet;
    }

    public long getAdsPrice() {
        return bestAdsPrice;
    }

    public int getAdsTimeSeconds() {
        return bestAdsTimeSeconds;
    }

    private void sortAdsByAmount() {
        Collections.sort(optimalAdsSet, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {

                long amount1 = o1.getAmountPerOneDisplaying();
                long amount2 = o2.getAmountPerOneDisplaying();

                if (amount2 - amount1 > 0) return 1;
                if (amount2 - amount1 < 0) return -1;
                return (1000 * amount1 / o1.getDuration()  - 1000 * amount2 / o2.getDuration() > 0) ? 1 : -1;
            }
        });
    }

    private void computeBestAdsList(List<Advertisement> advertisements) {
        if (advertisements.size() > 0)
            if (checkAdsList(advertisements)) {
                optimalAdsSet = advertisements;
                bestAdsPrice = calculatePrice(advertisements);
                bestAdsTimeSeconds = calculateTime(advertisements);
            }

        for (int i = 0; i < advertisements.size(); i++) {
            List<Advertisement> newAdsList = new ArrayList<>(advertisements);
            newAdsList.remove(i);
            computeBestAdsList(newAdsList);
        }
    }

    private int calculateTime(List<Advertisement> advertisements) {
        int result = 0;

        for (Advertisement ad : advertisements)
            result += ad.getDuration();

        return result;
    }

    private long calculatePrice(List<Advertisement> advertisements) {
        long result = 0;

        for (Advertisement ad : advertisements)
            result += ad.getAmountPerOneDisplaying();

        return result;
    }

    private boolean checkAdsList(List<Advertisement> advertisements) {

        long adsTimeSeconds = calculateTime(advertisements);

        if (adsTimeSeconds <= maxTimeSeconds) {

            long adsPrice = calculatePrice(advertisements);

            if (adsPrice > bestAdsPrice)
                return true;

            if (adsPrice == bestAdsPrice
                    && adsTimeSeconds > bestAdsTimeSeconds)
                return true;

            if (adsPrice == bestAdsPrice
                    && adsTimeSeconds == bestAdsTimeSeconds
                    && advertisements.size() < optimalAdsSet.size())
                return true;
        }
        return false;
    }
}
