package com.phasmidsoftware.dsaipg.sort.assignment6;

import java.util.function.Supplier;

public class Benchmark<T extends Comparable<T>> {
    private int runs;

    public Benchmark(int runs) {
        this.runs = runs;
    }

    public double run(Sort<T> sorter, Supplier<T[]> supplier) {
        long totalTime = 0;
        for (int i = 0; i < runs; i++) {

            T[] array = supplier.get();
            long startTime = System.nanoTime();
            sorter.sort(array, 0, array.length);
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }

        double averageNano = totalTime / (double) runs;
        return averageNano / 1e6;
    }
}

