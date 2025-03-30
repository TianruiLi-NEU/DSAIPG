package com.phasmidsoftware.dsaipg.sort.assignment6;

public class QuickSortDualPivot<T extends Comparable<T>> extends Sort<T> {
    @Override
    void sort(T[] a, int lo, int hi) {
        if (hi - lo <= 1) {
            return;
        }


        T pivot1 = a[lo];
        T pivot2 = a[hi - 1];


        if (pivot1.compareTo(pivot2) > 0) {
            swap(a, lo, hi - 1);
            pivot1 = a[lo];
            pivot2 = a[hi - 1];
        }


        int lt = lo + 1;
        int gt = hi - 2;
        int i = lo + 1;


        while (i <= gt) {
            if (a[i].compareTo(pivot1) < 0) {
                swap(a, i, lt);
                lt++;
                i++;
            } else if (a[i].compareTo(pivot2) > 0) {
                swap(a, i, gt);
                gt--;

            } else {
                i++;
            }
        }


        swap(a, lo, lt - 1);
        swap(a, hi - 1, gt + 1);

        sort(a, lo, lt - 1);
        sort(a, lt, gt + 1);
        sort(a, gt + 2, hi);
    }


    private void swap(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}

