package ru.job4j.concurrent;

public final class Cache {
    private static Cache cache;

    public static Cache getInstance() {
        synchronized (Cache.class) {
            if (cache == null) {
                cache = new Cache();
            }
            return cache;
        }
    }
}
