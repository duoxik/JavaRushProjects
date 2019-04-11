package com.duoxik.tasks.patterns.factory.male;

import com.duoxik.tasks.patterns.factory.AbstractFactory;
import com.duoxik.tasks.patterns.factory.Human;

public class MaleFactory implements AbstractFactory {

    @Override
    public Human getPerson(int age) {

        if (age <= KidBoy.MAX_AGE) {
            return new KidBoy();
        } else if (age <= TeenBoy.MAX_AGE) {
            return new TeenBoy();
        } else {
            return new Man();
        }
    }
}
