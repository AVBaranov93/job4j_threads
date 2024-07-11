package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) {
            while (queue.size() == limit) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            queue.offer(value);
            this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
            while (queue.isEmpty()) {
                this.wait();
            }
            T result = queue.poll();
            this.notifyAll();
            return result;
    }

    public synchronized boolean isEmpty() {
            return queue.isEmpty();
    }
}