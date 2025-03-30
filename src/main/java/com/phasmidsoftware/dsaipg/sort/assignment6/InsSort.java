package com.phasmidsoftware.dsaipg.sort.assignment6;

public abstract class InsSort<T extends Comparable<T>> extends Sort<T> {
    private final InstrumentHelper<T> helper = new InstrumentHelper<>();

    protected InstrumentHelper<T> getHelper() {
        return helper;
    }


    public long[] getStatistics() {
        return new long[] {
                helper.getSwapCount(),
                helper.getUpdateCount(),
                helper.getGetCount(),
                helper.getCompareCount()
        };
    }
}
