package ru.job4j.concurrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String path;

    public Wget(String url, int speed, String path) {
        this.url = url;
        this.speed = speed;
        this.path = path;
    }

    @Override
    public void run() {
        var file = new File(path);
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            var dataBuffer = new byte[512];
            int bytesRead;
            int bytesCount = 0;
            var msStart = System.currentTimeMillis();
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    if (bytesCount >= speed) {
                        var diff = System.currentTimeMillis() - msStart;
                        if (diff < 1000) {
                            try {
                                Thread.sleep(diff);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            bytesCount = 0;
                            msStart = System.currentTimeMillis();
                        }
                    }
                    output.write(dataBuffer[i]);
                    bytesCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean validate(String url, int speed, String path) {
        boolean isValid = true;
        /*check if url is valid*/
        try {
            URL addr = new URL(url);
            HttpURLConnection huc = (HttpURLConnection) addr.openConnection();
            if (HttpURLConnection.HTTP_OK != huc.getResponseCode()) {
                isValid = false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*check if speed is not set*/
        if (speed == 0) {
            isValid = false;
        }
        /*check if file exists*/
        File f = new File(path);
        if (!f.exists() || f.isDirectory()) {
            isValid = false;
        }
        return isValid;
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String path = args[2];
        if (validate(url, speed, path)) {
            Thread wget = new Thread(new Wget(url, speed, path));
            wget.start();
            wget.join();
        }
    }
}
