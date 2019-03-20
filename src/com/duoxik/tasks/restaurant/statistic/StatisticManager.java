package com.duoxik.tasks.restaurant.statistic;

import com.duoxik.tasks.restaurant.statistic.event.CookedOrderEventDataRow;
import com.duoxik.tasks.restaurant.statistic.event.EventDataRow;
import com.duoxik.tasks.restaurant.statistic.event.EventType;
import com.duoxik.tasks.restaurant.statistic.event.VideoSelectedEventDataRow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager {
    private static StatisticManager ourInstance = new StatisticManager();
    private StatisticStorage statisticStorage = new StatisticStorage();

    private StatisticManager() {}

    public static StatisticManager getInstance() {
        return ourInstance;
    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    public Map<Date, Long> getAdvertisementProfit() {

        Map<Date, Long> totalDaysAmount = new TreeMap<>(Collections.reverseOrder());
        List<EventDataRow> adsEvents = statisticStorage.getEventsListByType(EventType.SELECTED_VIDEOS);

        for (EventDataRow event : adsEvents) {
            VideoSelectedEventDataRow adsEvent = (VideoSelectedEventDataRow) event;
            Date date = getFormattedDate(adsEvent.getDate());
            totalDaysAmount.put(date, totalDaysAmount.getOrDefault(date, 0l) + adsEvent.getAmount());
        }

        return totalDaysAmount;
    }

    public Map<Date, Map<String, Integer>> getCooksWorkloading() {

        Map<Date, Map<String, Integer>> cooksWorkloading = new TreeMap<>(Collections.reverseOrder());
        List<EventDataRow> cookedOrders = statisticStorage.getEventsListByType(EventType.COOKED_ORDER);

        for (EventDataRow event : cookedOrders) {
            CookedOrderEventDataRow cookEvent = (CookedOrderEventDataRow) event;
            Date date = getFormattedDate(cookEvent.getDate());
            String cookName = cookEvent.getCookName();
            int cookingTime = cookEvent.getCookingTimeSeconds();

            if (cookingTime > 0) {
                Map<String, Integer> cooksMap = cooksWorkloading.getOrDefault(date, new TreeMap<>());
                cooksWorkloading.put(date, cooksMap);
                cooksMap.put(cookName, cooksMap.getOrDefault(cookName, 0) + cookingTime);
            }
        }

        return cooksWorkloading;
    }

    private Date getFormattedDate(Date inputDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            return dateFormat.parse(dateFormat.format(inputDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        {
            storage.put(EventType.COOKED_ORDER, new ArrayList<>());
            storage.put(EventType.NO_AVAILABLE_VIDEO, new ArrayList<>());
            storage.put(EventType.SELECTED_VIDEOS, new ArrayList<>());
        }

        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);
        }

        private List<EventDataRow> getEventsListByType(EventType type) {
            return storage.get(type);
        }
    }
}
