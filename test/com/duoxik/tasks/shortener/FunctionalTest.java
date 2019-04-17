package com.duoxik.tasks.shortener;

import com.duoxik.tasks.shortener.strategy.*;
import org.junit.Assert;
import org.junit.Test;

public class FunctionalTest {

    @Test
    public void testHashMapStorageStrategy() {
        Shortener shortener = new Shortener(new HashMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testOurHashMapStorageStrategy() {
        Shortener shortener = new Shortener(new OurHashMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testFileStorageStrategy() {
        Shortener shortener = new Shortener(new FileStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testHashBiMapStorageStrategy() {
        Shortener shortener = new Shortener(new HashBiMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testDualHashBidiMapStorageStrategy() {
        Shortener shortener = new Shortener(new DualHashBidiMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testOurHashBiMapStorageStrategy() {
        Shortener shortener = new Shortener(new OurHashBiMapStorageStrategy());
        testStorage(shortener);
    }

    public void testStorage(Shortener shortener) {

        String first = Helper.generateRandomString();

        String second;
        while (true) {
            second = Helper.generateRandomString();
            if (!second.equals(first))
                break;
        }

        String third = first;

        Long firstId = shortener.getId(first);
        Long secondId = shortener.getId(second);
        Long thirdId = shortener.getId(third);

        Assert.assertNotEquals(firstId, secondId);
        Assert.assertNotEquals(thirdId, secondId);
        Assert.assertEquals(firstId, thirdId);

        String firstValue = shortener.getString(firstId);
        String secondValue = shortener.getString(secondId);
        String thirdValue = shortener.getString(thirdId);

        Assert.assertEquals(first, firstValue);
        Assert.assertEquals(second, secondValue);
        Assert.assertEquals(third, thirdValue);
    }
}
