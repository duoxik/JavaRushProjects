package com.duoxik.tasks.logparser;

public enum Event {
    LOGIN,
    DOWNLOAD_PLUGIN,
    WRITE_MESSAGE,
    SOLVE_TASK,
    DONE_TASK;

    public static Event getInstance(String name) {
       for (Event event : values()) {
           if (event.toString().equals(name)) {
               return event;
           }
       }

       return null;
    }
}