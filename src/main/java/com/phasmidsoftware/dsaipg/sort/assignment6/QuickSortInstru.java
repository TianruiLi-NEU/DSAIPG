package com.phasmidsoftware.dsaipg.sort.assignment6;

public class QuickSortInstru<T extends Comparable<T>> extends InsSort<T> {

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

            if (getHelper().compare(getHelper().get(a, j), pivot) < 0) {
                getHelper().swap(a, i, j);
                i++;
            }
        }

        getHelper().swap(a, i, hi - 1);
        return i;
    }
}

