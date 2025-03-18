package com.phasmidsoftware.dsaipg.sort.par;


import java.util.Arrays;
import java.util.Random;

public class ParSortBenchmark {

    private static final int ITERATIONS = 10;

    public static void main(String[] args) {
        int[] arraySizes = {500_000, 1_000_000, 2_000_000};

        System.out.println("Benchmarking ParSort (cutoff)");
        int[] ratiovalues = {2, 4, 8, 16, 32, 64};
        for (int size : arraySizes) {
            System.out.println("\nArray size: " + size);
            int[] cutoffValues = new int[ratiovalues.length];
            for (int i = 0; i < cutoffValues.length; i++) {
                cutoffValues[i] = size / ratiovalues[i];
            }
            for (int cutoff : cutoffValues) {
                ParSort.cutoff = cutoff;
                long totalTime = 0;
                for (int i = 0; i < ITERATIONS; i++) {
                    int[] arr = generateRandomArray(size);
                    long start = System.nanoTime();
                    ParSort.sort(arr, 0, arr.length);
                    long elapsed = System.nanoTime() - start;
                    if (!isSorted(arr)) {
                        System.err.println("ParSort: Array not sorted for cutoff " + cutoff);
                    }
                    totalTime += elapsed;
                }
                double avgTimeMs = totalTime / ITERATIONS / 1_000_000.0;
                System.out.println("  Cutoff = " + cutoff + " average time: " + avgTimeMs + " ms");
            }
        }

        System.out.println("\nBenchmarking ParSortRecurDep (recursion depth)");
        int[] threadValues = {2, 4, 8, 16, 32};
        for (int size : arraySizes) {
            System.out.println("\nArray size: " + size);
            for (int threads : threadValues) {
                ParSortRecurDep.maxThreads = threads;
                ParSortRecurDep.maxDepth = (int) (Math.log(threads) / Math.log(2));
                long totalTime = 0;
                for (int i = 0; i < ITERATIONS; i++) {
                    int[] arr = generateRandomArray(size);
                    long start = System.nanoTime();
                    ParSortRecurDep.sort(arr, 0, arr.length);
                    long elapsed = System.nanoTime() - start;
                    if (!isSorted(arr)) {
                        System.err.println("ParSortRecurDep: Array not sorted for maxThreads " + threads);
                    }
                    totalTime += elapsed;
                }
                double avgTimeMs = totalTime / ITERATIONS / 1_000_000.0;
                System.out.println("  maxThreads = " + threads +
                        " (maxDepth = " + ParSortRecurDep.maxDepth + ") average time: " + avgTimeMs + " ms");
            }
        }

        System.out.println("\nBenchmarking unparallel with Array.sort");
        for (int size : arraySizes) {
            System.out.println("\nArray size: " + size);
            long totalTime = 0;
            for (int i = 0; i < ITERATIONS; i++) {
                int[] arr = generateRandomArray(size);
                long start = System.nanoTime();
                Arrays.sort(arr);
                long elapsed = System.nanoTime() - start;
                if (!isSorted(arr)) {
                    System.err.println("Arrays.sort: Array not sorted for size " + size);
                }
                totalTime += elapsed;
            }
            double avgTimeMs = totalTime / ITERATIONS / 1_000_000.0;
            System.out.println("  Arrays.sort average time: " + avgTimeMs + " ms");
        }
    }

    private static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt();
        }
        return arr;
    }

    private static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }
}

