package com.phasmidsoftware.dsaipg.sort.assignment6;

public class HeapSortInstru<T extends Comparable<T>> extends InsSort<T> {
    @Override
    void sort(T[] a, int lo, int hi) {
        int n = hi - lo;


        for (int i = n / 2 - 1; i >= 0; i--) {
            sink(a, i, n, lo);
        }


        for (int i = n - 1; i > 0; i--) {
            getHelper().swap(a, lo, lo + i);
            sink(a, 0, i, lo);
        }
    }


    private void sink(T[] a, int i, int n, int lo) {
        int r = i;
        InstrumentHelper<T> helper = getHelper();
        while (2 * r + 1 < n) {
            int j = 2 * r + 1;

            if (j + 1 < n && helper.compare(a, lo + j, lo + j + 1) < 0) {
                j++;
            }

            if (helper.compare(a, lo + r, lo + j) >= 0) {
                break;
            }

            helper.swap(a, lo + r, lo + j);
            r = j;
        }
    }
}

