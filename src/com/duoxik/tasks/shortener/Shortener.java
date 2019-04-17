package com.duoxik.tasks.shortener;

import com.duoxik.tasks.shortener.strategy.StorageStrategy;

public class Shortener {

    private Long lastId;
    private StorageStrategy storageStrategy;

    public Shortener(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
        this.lastId = 0l;
    }

    public synchronized Long getId(String string) {

        Long id = storageStrategy.getKey(string);
        if (id != null) {
            return id;
        } else {
            lastId++;
            storageStrategy.put(lastId, string);
            return lastId;
        }
    }

    public synchronized String getString(Long id) {
        
        return storageStrategy.getValue(id);
    }


}
