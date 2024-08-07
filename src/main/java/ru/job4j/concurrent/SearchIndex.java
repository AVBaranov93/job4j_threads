package ru.job4j.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class SearchIndex<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T target;
    private final int from;
    private final int to;

    public SearchIndex(T[] array, T target, int from, int to) {
        this.array = array;
        this.target = target;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (from == to) {
            return from;
        }
        int middle = (from + to) / 2;
        SearchIndex<T> firstHalf = new SearchIndex<>(array, target, from, middle);
        SearchIndex<T> secondHalf = new SearchIndex<>(array, target, middle + 1, to);
        firstHalf.fork();
        secondHalf.fork();
        firstHalf.join();
        secondHalf.join();
        return findArrayIndex();
    }
    public int findIndex() {
        if (array.length > 10) {
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            return forkJoinPool.invoke(new SearchIndex<T>(array, target, 0, array.length - 1));
        }
        return findArrayIndex();
    }

    private int findArrayIndex() {
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (target.equals(array[i])) {
                index = i;
                break;
            }
        }
        return index;
    }
}
