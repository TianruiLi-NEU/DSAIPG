package com.phasmidsoftware.dsaipg.sort.assignment6;

public class QuickSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    void sort(T[] a, int lo, int hi) {
        if (hi - lo <= 1) {
            return;
        }

        int p = partition(a, lo, hi);

        sort(a, lo, p);
        sort(a, p + 1, hi);
    }


    private int partition(T[] a, int lo, int hi) {
        T pivot = a[hi - 1];
        int i = lo;
        for (int j = lo; j < hi - 1; j++) {
            if (a[j].compareTo(pivot) < 0) {
                swap(a, i, j);
                i++;
            }
        }
        swap(a, i, hi - 1);
        return i;
    }


    private void swap(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}

