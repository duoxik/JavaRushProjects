package com.duoxik.tasks.restaurant;

import com.duoxik.tasks.restaurant.kitchen.Cook;
import com.duoxik.tasks.restaurant.kitchen.Order;
import com.duoxik.tasks.restaurant.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {

    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {

        Cook cook1 = new Cook("Amigo");
        cook1.setQueue(orderQueue);
        Cook cook2 = new Cook("Vladislav");
        cook2.setQueue(orderQueue);
        new Thread(cook1).start();
        new Thread(cook2).start();

        Waiter waiter = new Waiter();

        List<Tablet> tablets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i);
            tablet.setQueue(orderQueue);
            tablets.add(tablet);
        }

        cook1.addObserver(waiter);
        cook2.addObserver(waiter);

        new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL)).start();

//        DirectorTablet directorTablet = new DirectorTablet();
//        directorTablet.printCookWorkloading();
//        directorTablet.printActiveVideoSet();
//        directorTablet.printAdvertisementProfit();
//        directorTablet.printArchivedVideoSet();

    }
}
