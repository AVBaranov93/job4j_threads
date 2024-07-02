package ru.job4j.concurrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var file = new File("tmp.xml");
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            var dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                long diff = System.nanoTime() - downloadAt;
                long loadSpeed = 1024 * 1000000 / (System.nanoTime() - downloadAt);
                long speedDiff = loadSpeed / speed;
                if (speedDiff != 0) {
                    try {
                        Thread.sleep(speedDiff * 1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Read 1024 bytes : " + diff + " nano. load speed: " + loadSpeed + " byte/msec");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
