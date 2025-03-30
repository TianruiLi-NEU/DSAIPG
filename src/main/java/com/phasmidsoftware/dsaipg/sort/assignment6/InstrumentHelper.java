package com.phasmidsoftware.dsaipg.sort.assignment6;

public class InstrumentHelper<T extends Comparable<T>> {
    private long swapCount = 0;
    private long updateCount = 0;
    private long getCount = 0;
    private long compareCount = 0;


    public void swap(T[] array, int i, int j) {
        swapCount++;
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    public void update(T[] array, int index, T value) {
        updateCount++;
        array[index] = value;
    }


    public T get(T[] array, int index) {
        getCount++;
        return array[index];
    }


    public int compare(T a, T b) {
        compareCount++;
        return a.compareTo(b);
    }


    public int compare(T[] array, int i, int j) {
        return compare(get(array, i), get(array, j));
    }



    public long getSwapCount() {
        return swapCount;
    }


    public long getUpdateCount() {
        return updateCount;
    }


    public long getGetCount() {
        return getCount;
    }


    public long getCompareCount() {
        return compareCount;
    }
}

