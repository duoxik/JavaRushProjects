package com.duoxik.tasks.logparser;

public enum Status {
    OK,
    FAILED,
    ERROR;

    public static Status getInstance(String name) {
        for (Status status : values()) {
            if (status.toString().equals(name)) {
                return status;
            }
        }

        return null;
    }
}