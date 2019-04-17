package com.duoxik.tasks.shortener;

public class ExceptionHandler {

    public static void log(Exception e) {
        Helper.printMessage(e.toString());
    }
}
