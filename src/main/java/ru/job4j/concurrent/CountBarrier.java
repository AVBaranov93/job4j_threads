package ru.job4j.concurrent;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            if (count >= total) {
                this.notifyAll();
            }
            count++;
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}