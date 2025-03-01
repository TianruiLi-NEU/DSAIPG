package com.phasmidsoftware.dsaipg.adt.pq;

import com.phasmidsoftware.dsaipg.adt.pq.PriorityQueue;
import com.phasmidsoftware.dsaipg.adt.pq.FourAryPriorityQueue;
//import com.phasmidsoftware.dsaipg.adt.pq.FibonacciHeap;
import com.phasmidsoftware.dsaipg.util.Stopwatch;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class BenchmarkPQ {

    public static abstract class BenchmarkBase implements Supplier<Double> {
        protected final int capacity;
        protected final int insertNum;
        protected final int removeNum;
        protected final boolean floyd;
        protected Integer spilled;

        public BenchmarkBase(int capacity, int insertNum, int removeNum, boolean floyd) {
            this.capacity = capacity;
            this.insertNum = insertNum;
            this.removeNum = removeNum;
            this.floyd = floyd;
        }
    }

    public static class BenchmarkBinaryPQ extends BenchmarkBase {
        public BenchmarkBinaryPQ(int capacity, int insertNum, int removeNum, boolean floyd) {
            super(capacity, insertNum, removeNum, floyd);
        }

        @Override
        public Double get() {
            Random random = new Random();
            Comparator<Integer> comparator = Integer::compareTo;
            PriorityQueue<Integer> pq = new PriorityQueue<>(capacity, true, comparator, floyd);
            double prop = ((double) insertNum) / (insertNum + removeNum);
            int i = 0, r = 0;
            double elapsed = 0.0;

            while (i < insertNum || r < removeNum) {
                if (i >= insertNum) {
                    try (Stopwatch stopwatch = new Stopwatch()) {
                        pq.take();
                        elapsed += stopwatch.lap();
                        r++;
                    } catch (PQException e) {
                        throw new RuntimeException(e);
                    }
                } else if (r >= removeNum || pq.isEmpty() || random.nextDouble() < prop) {
                    int rand = random.nextInt(insertNum * 2);
                    try (Stopwatch stopwatch = new Stopwatch()) {
                        pq.give(rand);
                        elapsed += stopwatch.lap();
                        i++;
                    }
                } else {
                    try (Stopwatch stopwatch = new Stopwatch()) {
                        pq.take();
                        elapsed += stopwatch.lap();
                        r++;
                    } catch (PQException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            spilled = pq.highest;
            return elapsed;
        }
    }

    public static class BenchmarkFourAryPQ extends BenchmarkBase {
        public BenchmarkFourAryPQ(int capacity, int insertNum, int removeNum, boolean floyd) {
            super(capacity, insertNum, removeNum, floyd);
        }

        @Override
        public Double get() {
            Random random = new Random();
            Comparator<Integer> comparator = Integer::compareTo;
            FourAryPriorityQueue<Integer> pq = new FourAryPriorityQueue<>(capacity, true, comparator, floyd);
            double prop = ((double) insertNum) / (insertNum + removeNum);
            int i = 0, r = 0;
            double elapsed = 0.0;

            while (i < insertNum || r < removeNum) {
                if (i >= insertNum) {
                    try (Stopwatch stopwatch = new Stopwatch()) {
                        pq.take();
                        elapsed += stopwatch.lap();
                        r++;
                    }
                } else if (r >= removeNum || pq.isEmpty() || random.nextDouble() < prop) {
                    int rand = random.nextInt(insertNum * 2);
                    try (Stopwatch stopwatch = new Stopwatch()) {
                        pq.give(rand);
                        elapsed += stopwatch.lap();
                        i++;
                    }
                } else {
                    try (Stopwatch stopwatch = new Stopwatch()) {
                        pq.take();
                        elapsed += stopwatch.lap();
                        r++;
                    }
                }
            }
            spilled = pq.highest;
            return elapsed;
        }
    }

    public static class Ben implements Function<BenchmarkBase, Double> {
        private final int warmup;
        private final int repeatTimes;

        public Ben(int warmup, int repeatTimes) {
            this.warmup = warmup;
            this.repeatTimes = repeatTimes;
        }

        @Override
        public Double apply(BenchmarkBase benchmark) {
            double total = 0.0;
            for (int i = 0; i < warmup; i++) {
                benchmark.get();
            }
            for (int i = 0; i < repeatTimes; i++) {
                total += benchmark.get();
            }
            return total / repeatTimes;
        }
    }

    public static void test(String desc, Ben ben, BenchmarkBase benchmark) {
        System.out.println(desc);
        System.out.println("Average time: " + ben.apply(benchmark));
        System.out.println("The highest of the spilled elements during the last experiment: " + benchmark.spilled);
        System.out.println();
    }

    public static void main(String[] args) {
        Ben ben = new Ben(30, 60);
        int capacity = 4095;
        int insertNum = 16000;
        int removeNum = 4000;

        for (int i = 1; i <= 11; i++) {
            test("Test binary PQ on capacity " + (capacity * i), ben, new BenchmarkBinaryPQ(capacity * i, insertNum * i, removeNum * i, false));
            test("Test binary PQ (Floyd) on capacity " + (capacity * i), ben, new BenchmarkBinaryPQ(capacity * i, insertNum * i, removeNum * i, true));
            test("Test 4-ary PQ on capacity " + (capacity * i), ben, new BenchmarkFourAryPQ(capacity * i, insertNum * i, removeNum * i, false));
            test("Test 4-ary PQ (Floyd) on capacity " + (capacity * i), ben, new BenchmarkFourAryPQ(capacity * i, insertNum * i, removeNum * i, true));
        }
    }
}

