package com.phasmidsoftware.dsaipg.sort.assignment6;

public class QuickSortDPInstru<T extends Comparable<T>> extends InsSort<T> {
    @Override
    void sort(T[] a, int lo, int hi) {
        if (hi - lo <= 1) {
            return;
        }


        T pivot1 = a[lo];
        T pivot2 = a[hi - 1];


        if (getHelper().compare(pivot1, pivot2) > 0) {
            getHelper().swap(a, lo, hi - 1);
            pivot1 = getHelper().get(a, lo);
            pivot2 = getHelper().get(a, hi - 1);
        }


        int lt = lo + 1;
        int gt = hi - 2;
        int i = lo + 1;

        while (i <= gt) {
            if (getHelper().compare(getHelper().get(a, i), pivot1) < 0) {
                getHelper().swap(a, i, lt);
                lt++;
                i++;
            } else if (getHelper().compare(getHelper().get(a, i), pivot2) > 0) {
                getHelper().swap(a, i, gt);
                gt--;
            } else {
                i++;
            }
        }


        getHelper().swap(a, lo, lt - 1);
        getHelper().swap(a, hi - 1, gt + 1);


        sort(a, lo, lt - 1);
        sort(a, lt, gt + 1);
        sort(a, gt + 2, hi);
    }
}

