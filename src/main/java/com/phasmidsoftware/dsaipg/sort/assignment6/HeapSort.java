package com.phasmidsoftware.dsaipg.sort.assignment6;

public class HeapSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    void sort(T[] a, int lo, int hi) {
        int n = hi - lo;


        for (int i = n / 2 - 1; i >= 0; i--) {
            sink(a, i, n, lo);
        }


        for (int i = n - 1; i > 0; i--) {
            swap(a, lo, lo + i);
            sink(a, 0, i, lo);
        }
    }


    private void sink(T[] a, int i, int n, int lo) {
        int r = i;
        while (2 * r + 1 < n) {
            int j = 2 * r + 1;

            if (j + 1 < n && a[lo + j].compareTo(a[lo + j + 1]) < 0) {
                j++;
            }

            if (a[lo + r].compareTo(a[lo + j]) >= 0) {
                break;
            }

            swap(a, lo + r, lo + j);
            r = j;
        }
    }


    private void swap(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}

