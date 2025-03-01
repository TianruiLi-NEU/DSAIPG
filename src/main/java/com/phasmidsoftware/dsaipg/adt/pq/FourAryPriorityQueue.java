package com.phasmidsoftware.dsaipg.adt.pq;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class FourAryPriorityQueue<K> implements Iterable<K> {

    private final boolean max;
    private final int first;
    private final Comparator<K> comparator;
    private final K[] heap;
    private int last;
    private final boolean floyd;
    public K highest;

    @SuppressWarnings("unchecked")
    public FourAryPriorityQueue(int n, boolean max, Comparator<K> comparator, boolean floyd) {
        this.max = max;
        this.first = 1;
        this.comparator = comparator;
        this.last = 0;
        this.floyd = floyd;
        this.heap = (K[]) new Object[n + first];
    }

    public boolean isEmpty() {
        return last == 0;
    }

    public int size() {
        return last;
    }

    public void give(K key) {
        if (last == heap.length - first){
            if (highest == null ||
                    ((comparator.compare(highest, heap[last + first - 1]) > 0) ^ max)) {
                highest = heap[last + first -1];
            }
            last--;
        }
        heap[++last + first - 1] = key;
        swimUp(last + first - 1);
    }

    public K take() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue is empty");
        return floyd ? doTake(this::snake) : doTake(this::sink);
    }

    private K doTake(Consumer<Integer> f) {
        K result = heap[first];
        swap(first, last-- + first - 1);
        f.accept(first);
        heap[last + first] = null;
        return result;
    }

    private void sink(int k) {
        doHeapify(k, (a, b) -> !unordered(a, b));
    }

    private void snake(int k) {
        swimUp(doHeapify(k, (a, b) -> false));
    }

    private void swimUp(int k) {
        int i = k;
        while (i > first && unordered(parent(i), i)) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private boolean unordered(int i, int j) {
        if (heap[i] == null || heap[j] == null) {
            return false; // Prevents NullPointerException when comparing null elements
        }
        return (comparator.compare(heap[i], heap[j]) > 0) ^ max;
    }

    private int doHeapify(int k, BiPredicate<Integer, Integer> p) {
        int i = k;
        while (firstChild(i) <= last + first - 1) {
            int j = firstChild(i);
            int maxChild = j;
            for (int d = 1; d < 4; d++) {
                if (j + d < last + first && unordered(maxChild, j + d)) maxChild = j + d;
            }
            if (p.test(i, maxChild)) break;
            swap(i, maxChild);
            i = maxChild;
        }
        return i;
    }

    private void swap(int i, int j) {
        K temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private int parent(int k) {
        return (k + 1 - first) / 4 + first - 1;
    }

    private int firstChild(int k) {
        return (k + 1 - first) * 4 + first - 1;
    }

    @Override
    public Iterator<K> iterator() {
        List<K> copy = new ArrayList<>(Arrays.asList(Arrays.copyOf(heap, last + first)));
        Iterator<K> result = copy.iterator();
        if (first > 0) result.next();
        return result;
    }
}

