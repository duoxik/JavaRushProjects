package com.duoxik.tasks.restaurant;

import java.util.List;

public class RandomOrderGeneratorTask implements Runnable {

    private final List<Tablet> tablets;
    private final int delay;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int delay) {
        this.tablets = tablets;
        this.delay = delay;
    }

    @Override
    public void run() {
        while (true) {
            tablets.get((int) (Math.random() * tablets.size())).createTestOrder();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
