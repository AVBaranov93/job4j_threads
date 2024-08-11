package ru.job4j.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }
    }

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
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int[][] source = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        for (int[] arr : source) {
            for (int value : arr) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        for (Sums value : sum(source)) {
            System.out.println(value.getColSum() + " " + value.getRowSum());
        }
    }
}
