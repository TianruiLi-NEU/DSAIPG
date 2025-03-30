package com.phasmidsoftware.dsaipg.sort.assignment6;

import java.util.function.Supplier;


public class Experiment {
    public static void main(String[] args) {
        int initialSize = 10000;
        int iterations = 5;
        int runs = 10;

        Benchmark<Integer> timeBenchmark = new Benchmark<>(runs);
        BenchmarkInstru<Integer> instruBenchmark = new BenchmarkInstru<>(runs);

        HeapSort<Integer> heapSort = new HeapSort<>();
        MergeSort<Integer> mergeSort = new MergeSort<>();
        QuickSort<Integer> quickSort = new QuickSort<>();
        QuickSortDualPivot<Integer> quickSortDualPivot = new QuickSortDualPivot<>();
        HeapSortInstru<Integer> heapSortInstru = new HeapSortInstru<>();
        MergeSortInstru<Integer> mergeSortInstru = new MergeSortInstru<>();
        QuickSortInstru<Integer> quickSortInstru = new QuickSortInstru<>();
        QuickSortDPInstru<Integer> quickSortDPInstru = new QuickSortDPInstru<>();


        for (int i = 0; i < iterations; i++) {
            int size = initialSize * (1 << i);
            System.out.println("length：" + size);

            Supplier<Integer[]> supplier = () -> RandomArrayGenerator.generate(size);


            double timeHeap = timeBenchmark.run(heapSort, supplier);
            double timeMerge = timeBenchmark.run(mergeSort, supplier);
            double timeQuick = timeBenchmark.run(quickSort, supplier);
            double timeQuickDP = timeBenchmark.run(quickSortDualPivot, supplier);

            System.out.println("Average Time:");
            System.out.printf("HeapSort: %.3f ms\n", timeHeap);
            System.out.printf("MergeSort: %.3f ms\n", timeMerge);
            System.out.printf("QuickSort: %.3f ms\n", timeQuick);
            System.out.printf("QuickSortDualPivot: %.3f ms\n", timeQuickDP);

            BenchmarkInstru.Result resultHeap = instruBenchmark.run(heapSortInstru, supplier);
            BenchmarkInstru.Result resultMerge = instruBenchmark.run(mergeSortInstru, supplier);
            BenchmarkInstru.Result resultQuick = instruBenchmark.run(quickSortInstru, supplier);
            BenchmarkInstru.Result resultQuickDP = instruBenchmark.run(quickSortDPInstru, supplier);

            System.out.println("Average Statistics:");
            System.out.println("HeapSortInstru: " + resultHeap);
            System.out.println("MergeSortInstru: " + resultMerge);
            System.out.println("QuickSortInstru: " + resultQuick);
            System.out.println("QuickSortDPInstru: " + resultQuickDP);

            System.out.println("-------------------------------");
        }
    }
}

