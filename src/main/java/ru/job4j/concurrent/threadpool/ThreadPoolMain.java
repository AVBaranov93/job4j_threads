package ru.job4j.concurrent.threadpool;

public class ThreadPoolMain {

    public static void main(String[] args) throws Exception {

        ThreadPool threadPool = new ThreadPool();

        for (int i = 0; i < 20; i++) {
            int taskNo = i;
            threadPool.work(() -> {
                String message =
                        Thread.currentThread().getName()
                                + ": Task " + taskNo;
                System.out.println(message);
            });
        }
        threadPool.shutdown();
    }
}