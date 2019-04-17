package com.duoxik.tasks.shortener;

import com.duoxik.tasks.shortener.strategy.HashBiMapStorageStrategy;
import com.duoxik.tasks.shortener.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {

    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids) {

        Date startTimer = new Date();
        for (String str : strings)
            ids.add(shortener.getId(str));
        Date endTimer = new Date();

        return endTimer.getTime() - startTimer.getTime();

    }

    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        Date startTimer = new Date();
        for (Long id : ids)
            strings.add(shortener.getString(id));
        Date endTimer = new Date();

        return endTimer.getTime() - startTimer.getTime();
    }

    @Test
    public void testHashMapStorage() {

        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

        Set<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            origStrings.add(Helper.generateRandomString());
        }

        Set<Long> ids1 = new HashSet<>();
        Set<Long> ids2 = new HashSet<>();
        long timeToGetIds1 = getTimeToGetIds(shortener1, origStrings, ids1);
        long timeToGetIds2 = getTimeToGetIds(shortener2, origStrings, ids2);

        Assert.assertTrue(timeToGetIds1 - timeToGetIds2 > 0);

        long timeToGetStrings1 = getTimeToGetStrings(shortener1, ids1, new HashSet<>());
        long timeToGetStrings2 = getTimeToGetStrings(shortener2, ids2, new HashSet<>());

        Assert.assertEquals(timeToGetStrings1, timeToGetStrings2, 30);
    }
}
