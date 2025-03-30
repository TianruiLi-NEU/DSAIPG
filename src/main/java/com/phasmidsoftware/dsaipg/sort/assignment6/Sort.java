package com.phasmidsoftware.dsaipg.sort.assignment6;

public abstract  class Sort<T extends Comparable<T>> {
    public void sort(T[] a){
        sort(a, 0, a.length);
    }
    abstract void sort(T[] a, int lo, int hi);
}
