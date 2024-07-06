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
        return content(e -> true);
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        return content(e -> e < 0x80);
    }

    private synchronized String content(Predicate<Integer> filter) throws IOException {
        try (InputStream input = new FileInputStream(file)) {
            String output = "";
            int data;
            while ((data = input.read()) > 0) {
                if (filter.test(data)) {
                    output += (char) data;
                }
            }
            return output;
        }
    }
}