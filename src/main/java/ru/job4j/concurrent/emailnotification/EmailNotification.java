package ru.job4j.concurrent.emailnotification;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newSingleThreadExecutor();
    public synchronized void emailTo(User user) {
        String subject = String.format("Notification %s to email %s", user.getUsername(), user.getEmail());
        String body = String.format("Add a new event to %s", user.getUsername());
        pool.submit(() -> {
            send(subject, body, user.getEmail());
        });
    }

    public synchronized void close() {
        pool.shutdown();
    }

    public synchronized void send(String subject, String body, String email) {

    }
}
