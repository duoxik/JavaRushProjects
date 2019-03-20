package com.duoxik.tasks.restaurant.ad;

import java.util.ArrayList;
import java.util.List;

public class StatisticAdvertisementManager {

    private static StatisticAdvertisementManager instance = new StatisticAdvertisementManager();

    private AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {}

    public static StatisticAdvertisementManager getInstance() {
        return instance;
    }

    public List<Advertisement> getActiveVideoSet() {
        List<Advertisement> activeVideoSet = new ArrayList<>();
        for (Advertisement ads : advertisementStorage.list())
            if (ads.getHits() > 0)
                activeVideoSet.add(ads);

        return activeVideoSet;
    }

    public List<Advertisement> getArchivedVideoSet() {
        List<Advertisement> archivedVideoSet = new ArrayList<>();
        for (Advertisement ads : advertisementStorage.list())
            if (ads.getHits() == 0)
                archivedVideoSet.add(ads);

        return archivedVideoSet;
    }
}
