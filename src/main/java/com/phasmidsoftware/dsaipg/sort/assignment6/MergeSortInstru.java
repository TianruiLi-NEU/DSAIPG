package com.phasmidsoftware.dsaipg.sort.assignment6;

public class MergeSortInstru<T extends Comparable<T>> extends InsSort<T> {
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
        InstrumentHelper<T> helper = getHelper();
        T[] aux = java.util.Arrays.copyOfRange(a, lo, hi);
        int i = 0;
        int j = mid - lo;
        int leftEnd = mid - lo;
        int rightEnd = hi - lo;


        for (int k = lo; k < hi; k++) {
            if (i >= leftEnd) {
                helper.update(a, k, helper.get(aux, j++));
            } else if (j >= rightEnd) {
                helper.update(a, k, helper.get(aux, i++));
            } else if (helper.compare(aux, i, j) <= 0) {
                helper.update(a, k, helper.get(aux, i++));
            } else {
                helper.update(a, k, helper.get(aux, j++));
            }
        }
    }

}

