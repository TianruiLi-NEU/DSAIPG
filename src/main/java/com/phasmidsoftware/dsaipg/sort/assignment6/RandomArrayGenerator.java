package com.phasmidsoftware.dsaipg.sort.assignment6;

import java.util.Random;
import java.util.function.Supplier;


public class RandomArrayGenerator {
    public static Integer[] generate(int size) {
        Integer[] array = new Integer[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100000);
        }
        return array;
    }
}
