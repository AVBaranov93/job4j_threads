package ru.job4j.concurrent.rowcolsum;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                rowSum += matrix[j][i];
                colSum += matrix[i][j];
            }
            result[i] = new Sums(rowSum, colSum);
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = new Sums(getRowSum(matrix, i).get(), getColumnSum(matrix, i).get());
        }
        return result;
    }
    private static CompletableFuture<Integer> getColumnSum(int[][] data, int startRow) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int i = 0; i < data.length; i++) {
                sum += data[startRow][i];
            }
            return sum;
        });
    }

    private static CompletableFuture<Integer> getRowSum(int[][] data, int startCol) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int i = 0; i < data.length; i++) {
                sum += data[i][startCol];
            }
            return sum;
        });
    }
}
