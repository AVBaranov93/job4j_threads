package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.*;

class CASCountTest {
    @Test
    public void whenFirstThreadIncrement10AndSecondThreadIncrement10Than20() {
        CASCount count = new CASCount();
        int expected = 20;
        Thread first = new Thread(
                () -> IntStream.rangeClosed(1, 10).forEach(e -> count.increment())
        );
        Thread second = new Thread(
                () -> IntStream.rangeClosed(1, 10).forEach(e -> count.increment())
        );
        first.start();
        second.start();
        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertThat(count.get()).isEqualTo(expected);
    }

    @Test
    public void whenFirstThreadIncrement10AndSecondThreadIncrement100Than110() {
        CASCount count = new CASCount();
        int expected = 110;
        Thread first = new Thread(
                () -> IntStream.rangeClosed(1, 100).forEach(e -> count.increment())
        );
        Thread second = new Thread(
                () -> IntStream.rangeClosed(1, 10).forEach(e -> count.increment())
        );
        first.start();
        second.start();
        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertThat(count.get()).isEqualTo(expected);
    }
}