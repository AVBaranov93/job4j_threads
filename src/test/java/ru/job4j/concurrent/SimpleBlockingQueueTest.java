package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {
    @Test
    public void whenProducerAdd10ThenConsumerReturn10() {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        int expected = 10;
        Thread producer = new Thread(
                () -> {
                    queue.offer(10);
                }
        );
        Thread consumer = new Thread(
                () -> {
                    assertThat(queue.poll()).isEqualTo(expected);
                }
        );
        producer.start();
        consumer.start();
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}