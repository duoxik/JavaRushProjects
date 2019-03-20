package com.duoxik.tasks.restaurant.kitchen;

import com.duoxik.tasks.restaurant.ConsoleHelper;
import com.duoxik.tasks.restaurant.Tablet;
import com.duoxik.tasks.restaurant.statistic.StatisticManager;
import com.duoxik.tasks.restaurant.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook extends Observable implements Runnable {

    private String name;
    private boolean busy;
    private LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();

    public Cook(String name) {
        this.name = name;
    }

    public void startCookingOrder(Order order) {

        busy = true;
        Tablet tablet = order.getTablet();

        ConsoleHelper.writeMessage(
                String.format("Start cooking - %s, cooking time %smin",
                        order, order.getTotalCookingTime()));

        try {
            Thread.sleep(order.getTotalCookingTime() * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        StatisticManager.getInstance().register(new CookedOrderEventDataRow(tablet.toString(), name, order.getTotalCookingTime() * 60, order.getDishes()));

        setChanged();
        notifyObservers(order);
        busy = false;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void run() {
        while (true) {

            Order order = queue.poll();
            if (order != null)
                startCookingOrder(order);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
