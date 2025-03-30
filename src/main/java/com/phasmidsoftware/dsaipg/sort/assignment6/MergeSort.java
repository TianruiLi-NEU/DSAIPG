package com.phasmidsoftware.dsaipg.sort.assignment6;

import java.util.Arrays;

public class MergeSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    void sort(T[] a, int lo, int hi) {
        if (hi - lo < 2) {
            return;
        }

        int mid = lo + (hi - lo) / 2;

        sort(a, lo, mid);
        sort(a, mid, hi);

        merge(a, lo, mid, hi);
    }


    private void merge(T[] a, int lo, int mid, int hi) {

        T[] aux = Arrays.copyOfRange(a, lo, hi);


        int i = 0;
        int j = mid - lo;
        int leftEnd = mid - lo;
        int rightEnd = hi - lo;

        for (int k = lo; k < hi; k++) {
            if (i >= leftEnd) {
                a[k] = aux[j++];
            } else if (j >= rightEnd) {
                a[k] = aux[i++];
            } else if (aux[i].compareTo(aux[j]) <= 0) {
                a[k] = aux[i++];
            } else {
                a[k] = aux[j++];
            }
        }
    }
}

