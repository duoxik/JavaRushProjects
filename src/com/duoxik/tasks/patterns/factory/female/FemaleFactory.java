package com.duoxik.tasks.patterns.factory.female;

import com.duoxik.tasks.patterns.factory.AbstractFactory;
import com.duoxik.tasks.patterns.factory.Human;

public class FemaleFactory implements AbstractFactory {

    @Override
    public Human getPerson(int age) {

        if (age <= KidGirl.MAX_AGE) {
            return new KidGirl();
        } else if (age <= TeenGirl.MAX_AGE) {
            return new TeenGirl();
        } else {
            return new Woman();
        }
    }
}
