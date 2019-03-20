package com.duoxik.tasks.restaurant;

import com.duoxik.tasks.restaurant.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {

    private static BufferedReader reader
            = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return reader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {

        List<Dish> dishes = new ArrayList<>();
        writeMessage(Dish.allDishesToString());
        String choice = null;
        while (!(choice = readString()).equals("exit")) {
           try {
               dishes.add(Dish.valueOf(choice));
           } catch (IllegalArgumentException e) {
               writeMessage("Данного блюда нет в списке.");
           }
        }
        return dishes;
    }
}
