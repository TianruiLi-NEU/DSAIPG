/*
 * Copyright (c) 2024. Robin Hillyard
 */
package com.phasmidsoftware.dsaipg.sort.elementary;

import com.phasmidsoftware.dsaipg.sort.Helper;
import com.phasmidsoftware.dsaipg.sort.Sort;
import com.phasmidsoftware.dsaipg.sort.SortWithHelper;
import com.phasmidsoftware.dsaipg.util.Config;
import com.phasmidsoftware.dsaipg.util.Config_Benchmark;
import com.phasmidsoftware.dsaipg.util.Benchmark_Timer;
import com.phasmidsoftware.dsaipg.sort.HelperFactory;

import java.io.IOException;
import java.util.Comparator;
import java.util.*;
import java.util.function.Supplier;

import static com.phasmidsoftware.dsaipg.sort.InstrumentedComparatorHelper.getRunsConfig;
import static com.phasmidsoftware.dsaipg.util.Config_Benchmark.setupConfigFixes;

/**
 * A class for performing insertion sort using a comparator, extending functionality from SortWithHelper.
 * This includes methods for initialization and invocation of insertion sort,
 * along with specific utilities like counting inversions.
 *
 * @param <X> the type of elements to be sorted, which can be compared using a provided comparator.
 */
public class InsertionSortComparator<X> extends SortWithHelper<X> {
    /**
     * Constructor for InsertionSortComparator, which initializes the comparator with the provided helper.
     *
     * @param helper the Helper object to be used for managing the sorting process.
     */
    public InsertionSortComparator(Helper<X> helper) {
        super(helper);
    }

    /**
     * Constructor for any subclasses to use.
     *
     * @param description the description.
     * @param comparator  the comparator to use.
     * @param N           the number of elements expected.
     * @param nRuns       the number of runs to be expected (this is only significant when instrumenting).
     * @param config      the configuration.
     */
    protected InsertionSortComparator(String description, Comparator<X> comparator, int N, int nRuns, Config config) {
        super(description, comparator, N, nRuns, config);
    }

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number elements we expect to sort.
     * @param nRuns  the number of runs to be expected (this is only significant when instrumenting).
     * @param config the configuration.
     */
    public InsertionSortComparator(Comparator<X> comparator, int N, int nRuns, Config config) {
        this(DESCRIPTION, comparator, N, nRuns, config);
    }

    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();

        // TO BE IMPLEMENTED

        for (int i = from + 1; i < to; i++) {
                X key = xs[i];
                int j = i - 1;
                while (j >= from && helper.compare(xs[j], key) > 0) {
                    xs[j + 1] = xs[j];
                    j--;
                }
                xs[j + 1] = key;
        }
    }

    public static final String DESCRIPTION = "Insertion sort";

    /**
     * Sorts the given array in-place using the provided insertion sort comparator.
     *
     * @param <T> the generic type parameter that extends Comparable.
     * @param ts  the array of elements to be sorted, where elements must implement {@code Comparable}.
     *            The method modifies this array directly to produce the sorted order.
     * @throws RuntimeException if an IOException occurs during the sorting process.
     */
    public static <T extends Comparable<T>> void sort(T[] ts) {
        try (InsertionSortComparator<T> sort = new InsertionSortComparator<>(DESCRIPTION, Comparable::compareTo, ts.length, 1, Config.load(InsertionSortComparator.class))) {
            sort.mutatingSort(ts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a case-insensitive string sorter using an insertion sort comparator.
     *
     * @param n      the expected number of elements to be sorted.
     * @param config the configuration object containing necessary settings.
     * @return a {@code SortWithHelper<String>} instance configured for case-insensitive string sorting.
     */
    public static Sort<String> stringSorterCaseInsensitive(int n, Config config) {
        return new InsertionSortComparator<>(DESCRIPTION, String.CASE_INSENSITIVE_ORDER, n, getRunsConfig(config), config);
    }

    /**
     * This method is designed to count inversions in quadratic time, using insertion sort.
     *
     * @param ts  an array of comparable T elements.
     * @param <T> the underlying type of the elements.
     * @return the number of inversions in ts, which remains unchanged.
     */
    public static <T> long countInversions(T[] ts, Comparator<T> comparator) {
        final Config config = setupConfigFixes();
        try (InsertionSortComparator<T> sorter = new InsertionSortComparator<>(comparator, ts.length, getRunsConfig(config), config)) {
            Helper<T> helper = sorter.getHelper();
            sorter.sort(ts, true);
            return helper.getFixes();
        }
    }

    public static void main(String[] args) {
        int n = 100;
        int testTimes = 100;

        for (int i = 1; i <= 7; i++) {
            Comparator<Integer> comparator = Integer::compareTo;
            Helper<Integer> helper = HelperFactory.createGeneric("Test " + n, comparator, n, 1, setupConfigFixes());
            InsertionSortComparator<Integer> sorter = new InsertionSortComparator<>(helper);

            runBenchmark("Ordered Test", n, new OrderedIntegerArraySupplier(n), sorter, testTimes);
            runBenchmark("Reverse Ordered Test", n, new ReverseOrderedIntegerArraySupplier(n), sorter, testTimes);
            runBenchmark("Random Test", n, new RandomIntegerArraySupplier(n), sorter, testTimes);
            runBenchmark("Partially Ordered Test", n, new PartiallyOrderedIntegerArraySupplier(n), sorter, testTimes);

            n *= 2;
        }
    }

    private static void runBenchmark(String testName, int n, Supplier<Integer[]> supplier, InsertionSortComparator<Integer> sorter, int testTimes) {
        Benchmark_Timer<Integer[]> benchmark = new Benchmark_Timer<>(testName + " " + n, a -> sorter.sort(a, 0, a.length));
        double time = benchmark.runFromSupplier(supplier, testTimes);
        System.out.printf("%-25s Size: %-8d Time: %.2f ms%n", testName, n, time);
    }



    public static class OrderedIntegerArraySupplier implements Supplier<Integer[]> {
        private final int n;
        public OrderedIntegerArraySupplier(int n) {
            this.n = n;
        }
        @Override
        public Integer[] get() {
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i++) {
                a[i] = i;
            }
            return a;
        }
    }

    public static class ReverseOrderedIntegerArraySupplier implements Supplier<Integer[]> {
        private final int n;
        public ReverseOrderedIntegerArraySupplier(int n) {
            this.n = n;
        }
        @Override
        public Integer[] get() {
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i++) {
                a[i] = n - i - 1;
            }
            return a;
        }
    }

    public static class RandomIntegerArraySupplier implements Supplier<Integer[]> {
        private final int n;
        public RandomIntegerArraySupplier(int n) {
            this.n = n;
        }
        @Override
        public Integer[] get() {
            Random random = new Random();
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i++) {
                a[i] = random.nextInt(n * 2);
            }
            return a;
        }
    }

    public static class PartiallyOrderedIntegerArraySupplier implements Supplier<Integer[]> {
        private final int n;
        public PartiallyOrderedIntegerArraySupplier(int n) {
            this.n = n;
        }
        @Override
        public Integer[] get() {
            Random random = new Random();
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i += 2) {
                a[i] = i;
            }
            for (int i = 1; i < n; i += 2) {
                a[i] = random.nextInt(n * 2);
            }
            return a;
        }
    }
}


