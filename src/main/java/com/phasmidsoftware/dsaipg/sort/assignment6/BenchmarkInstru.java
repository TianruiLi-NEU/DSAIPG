package com.phasmidsoftware.dsaipg.sort.assignment6;

import java.util.function.Supplier;

public class BenchmarkInstru<T extends Comparable<T>> {
    private int runs;

    public BenchmarkInstru(int runs) {
        this.runs = runs;
    }

    public Result run(InsSort<T> sorter, Supplier<T[]> supplier) {
        long totalSwaps = 0;
        long totalUpdates = 0;
        long totalGets = 0;
        long totalCompares = 0;

        for (int i = 0; i < runs; i++) {
            T[] array = supplier.get();
            long[] before = sorter.getStatistics();
            sorter.sort(array, 0, array.length);
            long[] after = sorter.getStatistics();

            totalSwaps += (after[0] - before[0]);
            totalUpdates += (after[1] - before[1]);
            totalGets += (after[2] - before[2]);
            totalCompares += (after[3] - before[3]);
        }

        long avgSwaps = totalSwaps / runs;
        long avgUpdates = totalUpdates / runs;
        long avgGets = totalGets / runs;
        long avgCompares = totalCompares / runs;

        return new Result(avgSwaps, avgUpdates, avgGets, avgCompares);
    }


    public static class Result {
        public final long averageSwaps;
        public final long averageUpdates;
        public final long averageGets;
        public final long averageCompares;

        public Result(long averageSwaps, long averageUpdates, long averageGets, long averageCompares) {
            this.averageSwaps = averageSwaps;
            this.averageUpdates = averageUpdates;
            this.averageGets = averageGets;
            this.averageCompares = averageCompares;
        }

        @Override
        public String toString() {
            return String.format("Swaps: %d, Updates: %d, Gets: %d, Compares: %d",
                    averageSwaps, averageUpdates, averageGets, averageCompares);
        }
    }
}

