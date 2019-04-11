package com.duoxik.tasks.patterns.factory;

import com.duoxik.tasks.patterns.factory.female.FemaleFactory;
import com.duoxik.tasks.patterns.factory.male.MaleFactory;

public class FactoryProducer {

    public enum HumanFactoryType {
        MALE, FEMALE
    }

    public static AbstractFactory getFactory(HumanFactoryType type) {
        switch (type) {
            case MALE:
                return new MaleFactory();
            case FEMALE:
                return new FemaleFactory();
            default:
                return null;
        }
    }
}
