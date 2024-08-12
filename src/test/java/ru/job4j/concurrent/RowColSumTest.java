package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RowColSumTest {
    @Test
    public void whenSerialThan() {
        int[][] source = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertThat(RowColSum.sum(source)[1].getColSum()).isEqualTo(15);
    }

    @Test
    public void whenAsyncThanGetSameResult() {
        int[][] source = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        try {
            assertThat(RowColSum.asyncSum(source)[1].getColSum()).isEqualTo(15);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}