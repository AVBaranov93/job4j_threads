package ru.job4j.concurrent.threadpool;

import ru.job4j.concurrent.SimpleBlockingQueue;
import java.util.*;

public class ThreadPool {
    private final SimpleBlockingQueue<Runnable> simpleBlockingQueue;
    private final List<Thread> threads = new ArrayList<>();
    private boolean isStopped = false;

    public ThreadPool(int noOfThreads, int maxNoOfTasks) {
        simpleBlockingQueue = new SimpleBlockingQueue<>(maxNoOfTasks);
        for (int i = 0; i < noOfThreads; i++) {
            Runnable runnable = () -> {
                while (!isStopped) {
                    try {
                        simpleBlockingQueue.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();
        }
    }

    public synchronized void  execute(Runnable task) {
        simpleBlockingQueue.offer(task);
    }

    public synchronized void stop() {
        this.isStopped = true;
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}
