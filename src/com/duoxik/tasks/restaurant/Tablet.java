package com.duoxik.tasks.restaurant;

import com.duoxik.tasks.restaurant.ad.AdvertisementManager;
import com.duoxik.tasks.restaurant.ad.NoVideoAvailableException;
import com.duoxik.tasks.restaurant.kitchen.Order;
import com.duoxik.tasks.restaurant.kitchen.TestOrder;
import com.duoxik.tasks.restaurant.statistic.StatisticManager;
import com.duoxik.tasks.restaurant.statistic.event.NoAvailableVideoEventDataRow;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet{

    final int number;
    private Order order;
    private static Logger logger
            = Logger.getLogger(Tablet.class.getName());

    private LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();

    public Tablet(int number) {
        this.number = number;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public void createOrder() {
        try {
            order = new Order(this);
            supportMethod();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
    }

    public void createTestOrder() {
        try {
            order = new TestOrder(this);
            supportMethod();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
    }

    private void supportMethod(){
        if (!order.isEmpty()) {
            try {
                new AdvertisementManager(order.getTotalCookingTime() * 60).processVideos();
                queue.put(order);
            } catch (NoVideoAvailableException e) {
                StatisticManager.getInstance().register(new NoAvailableVideoEventDataRow(order.getTotalCookingTime() * 60));
                logger.log(Level.INFO, "No video is available for the order " + order);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Tablet{number=%d}", number);
    }
}
