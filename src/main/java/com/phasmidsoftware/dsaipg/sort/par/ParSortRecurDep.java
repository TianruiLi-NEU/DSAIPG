package com.phasmidsoftware.dsaipg.sort.par;


import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

final class ParSortRecurDep {

    public static int maxThreads = 8;

    public static int maxDepth = (int)(Math.log(maxThreads) / Math.log(2));

    public static void sort(int[] array, int from, int to, int depth) {
        if (to - from <= 1) {
            return;
        }

        if (depth < maxDepth) {
            int mid = (from + to) / 2;

            CompletableFuture<int[]> completableFuture1 = asyncSort(array, from, mid, depth + 1);
            CompletableFuture<int[]> completableFuture2 = asyncSort(array, mid, to, depth + 1);

            CompletableFuture<int[]> completableFuture = completableFuture1.thenCombine(completableFuture2, ParSort::doMerge);
            completableFuture.whenComplete((result, throwable) -> System.arraycopy(result, 0, array, from, result.length));
            completableFuture.join();
        } else {
            Arrays.sort(array, from, to);
        }
    }

    public static void sort(int[] array, int from, int to) {
        sort(array, from, to, 0);
    }

    static int[] sortRecursive(int[] array, int from, int to, int depth) {
        int[] result = new int[to - from];
        System.arraycopy(array, from, result, 0, to - from);
        sort(result, 0, result.length, depth);
        return result;
    }

    static int[] doMerge(int[] xs1, int[] xs2) {
        int[] result = new int[xs1.length + xs2.length];
        int i = 0;
        int j = 0;
        for (int k = 0; k < result.length; k++) {
            if (i >= xs1.length) result[k] = xs2[j++];
            else if (j >= xs2.length) result[k] = xs1[i++];
            else if (xs2[j] < xs1[i]) result[k] = xs2[j++];
            else result[k] = xs1[i++];
        }
        return result;
    }

    static CompletableFuture<int[]> asyncSort(int[] array, int from, int to, int depth) {
        return CompletableFuture.supplyAsync(
                () -> sortRecursive(array, from, to, depth)
        );
    }

    static CompletableFuture<int[]> asyncSort(int[] array, int from, int to) {
        return asyncSort(array, from, to, 0);
    }
}
