package ru.job4j.concurrent.rowcolsum;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RowColSumTest {
    @Test
    public void whenSerialThan() {
        int[][] source = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] expected = new Sums[] {new Sums(12, 6), new Sums(15, 15), new Sums(18, 24)};
        assertArrayEquals(expected, RowColSum.sum(source));
    }

    @Test
    public void whenAsyncThanGetSameResult() {
        int[][] source = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] expected = new Sums[] {new Sums(12, 6), new Sums(15, 15), new Sums(18, 24)};
        try {
            assertArrayEquals(expected, RowColSum.asyncSum(source));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}