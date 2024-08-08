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
        if (to - from <= 10) {
            return lineSearch();
        }
        int middle = (from + to) / 2;
        SearchIndex<T> firstHalf = new SearchIndex<>(array, target, from, middle);
        SearchIndex<T> secondHalf = new SearchIndex<>(array, target, middle + 1, to);
        firstHalf.fork();
        secondHalf.fork();
        firstHalf.join();
        secondHalf.join();
        return Math.max(firstHalf.lineSearch(), secondHalf.lineSearch());
    }
    public int findIndex() {
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            return forkJoinPool.invoke(new SearchIndex<>(array, target, 0, array.length - 1));
    }

    private int lineSearch() {
        int index = -1;
        for (int i = from; i < to; i++) {
            if (target.equals(array[i])) {
                index = i;
                break;
            }
        }
        return index;
    }
}
