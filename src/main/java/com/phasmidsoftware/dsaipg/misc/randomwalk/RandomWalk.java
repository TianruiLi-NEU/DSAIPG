/*
 * Copyright (c) 2017-2024. Robin Hillyard
 */

package com.phasmidsoftware.dsaipg.misc.randomwalk;

import java.util.Random;

/**
 * The RandomWalk class simulates a two-dimensional random walk. A "drunkard"
 * moves in a random direction for a specified number of steps, and the distance
 * from the starting point is measured. Additionally, multiple random walk
 * experiments can be performed to compute average distances.
 */
public class RandomWalk {

    /**
     * Method to compute the distance from the origin (the lamp-post where the drunkard starts) to his current position.
     *
     * @return the (Euclidean) distance from the origin to the current position.
     */
    public double distance() {
        // TO BE IMPLEMENTED 
        return Math.sqrt((long) x * x + (long) y * y);
        // END SOLUTION
    }

    /**
     * Private method to move the current position, that's to say the drunkard moves
     *
     * @param dx the distance he moves in the x direction
     * @param dy the distance he moves in the y direction
     */
    private void move(int dx, int dy) {
        // TO BE IMPLEMENTED  do move
        x += dx;
        y += dy;
        // END SOLUTION
    }

    /**
     * Perform a random walk of m steps
     *
     * @param m the number of steps the drunkard takes
     */
    private void randomWalk(int m) {
        // TO BE IMPLEMENTED 
        for (int i = 0; i < m; i++) {
            randomMove();
        }
    }

    /**
     * Private method to generate a random move according to the rules of the situation.
     * That's to say, moves can be (+-1, 0) or (0, +-1).
     */
    private void randomMove() {
        boolean ns = random.nextBoolean();
        int step = random.nextBoolean() ? 1 : -1;
        move(ns ? step : 0, ns ? 0 : step);
    }

    private int x = 0;
    private int y = 0;

    private final Random random = new Random();

    /**
     * Perform multiple random walk experiments, returning the mean distance.
     *
     * @param m the number of steps for each experiment
     * @param n the number of experiments to run
     * @return the mean distance
     */
    public static double randomWalkMulti(int m, int n) {
        double totalDistance = 0;
        for (int i = 0; i < n; i++) {
            RandomWalk walk = new RandomWalk();
            walk.randomWalk(m);
            totalDistance = totalDistance + walk.distance();
        }
        return totalDistance / n;
    }

    /**
     * The main method serves as the entry point to the RandomWalk program. It performs
     * either a single random walk experiment or several experiments, based on the
     * provided input arguments, and prints the mean distance.
     *
     * @param args command-line arguments where:
     *             args[0] specifies the number of steps for a random walk (required),
     *             and args[1] optionally specifies the number of experiments (default is 30).
     *             If args is empty, the method throws a RuntimeException indicating invalid syntax.
     */
    public static void main(String[] args) {
        int[] mValues = {10, 20, 50, 100, 500, 1000, 5000, 10000};
        int n = 1000;

        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0; // For linear regression

        if (args.length == 0) {
            for (int m : mValues) {
                double meanDistance = randomWalkMulti(m, n);
                double sqrtM = Math.sqrt(m);
                double ratio = meanDistance / sqrtM;

                System.out.println(m + " steps mean distance: " + meanDistance + " over " + n +
                        " experiments, sqrt(m) = " + Math.sqrt(m) + ", ratio between d and sqrt of m is " + ratio);

                // Collect data for linear regression
                sumX += sqrtM;
                sumY += meanDistance;
                sumXY += sqrtM * meanDistance;
                sumX2 += sqrtM * sqrtM;
            }

            // Compute slope (k) using Least Squares Regression
            int numPoints = mValues.length;
            double slope = (numPoints * sumXY - sumX * sumY) / (numPoints * sumX2 - sumX * sumX);

            System.out.println("\nSo, the relationship is : d ≈ " + slope + " * sqrt(m)");

        } else {
            int m = Integer.parseInt(args[0]);
            int nExp = (args.length > 1) ? Integer.parseInt(args[1]) : 1000;

            double meanDistance = randomWalkMulti(m, nExp);
            System.out.println(m + " steps mean distance: " + meanDistance + " over " + nExp +
                    " experiments, sqrt(m) = " + Math.sqrt(m));
        }
    }
}