package ru.job4j.concurrent;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }


    public synchronized String getContent() throws IOException {
        return contentAll(e -> true);
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        return contentAll(e -> e < 0x80);
    }

    private synchronized String contentAll(Predicate<Integer> filter) throws IOException {
        try (InputStream input = new FileInputStream(file)) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = input.read()) != -1) {
                if (filter.test(data)) {
                    output.append(data);
                }
            }
            return output.toString();
        }
    }
}