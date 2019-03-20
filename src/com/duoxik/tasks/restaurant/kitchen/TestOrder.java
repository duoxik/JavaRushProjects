package com.duoxik.tasks.restaurant.kitchen;

import com.duoxik.tasks.restaurant.Tablet;

import java.io.IOException;
import java.util.ArrayList;

public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() throws IOException {

        dishes = new ArrayList<>();
        while (true) {
            int randomNumber = (int) (Math.random() * (Dish.values().length + 1));
            if (randomNumber == Dish.values().length) {
                if (dishes.isEmpty()) {
                    continue;
                } else {
                    break;
                }
            }
            dishes.add(Dish.values()[randomNumber]);
        }
    }
}
