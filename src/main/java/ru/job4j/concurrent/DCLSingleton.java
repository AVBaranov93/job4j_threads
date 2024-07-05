package ru.job4j.concurrent;

public final class DCLSingleton {

    private volatile static DCLSingleton instance;

    public static DCLSingleton getInstance() {
        if (instance == null) {
            instance = new DCLSingleton();
        }
        return instance;
    }

    private DCLSingleton() {
    }

}