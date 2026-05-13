package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class RandomKeyRace {
    private RandomKeyRace() {} // Private constructor to prevent instantiation

    /**
     * Runs a benchmark test of all search algorithms with random keys
     * Tests each algorithm 30 times with a mix of valid and invalid keys
     * Displays how many times faster each algorithm is compared to others
     * 
     * @param articleArrayList ArrayList of Articles to test
     * @param articleLinkedList LinkedList of Articles to test
     */
    public static void runRandomKeyRace(ArrayList<Article> articleArrayList,
                                        LinkedList<Article> articleLinkedList) {

        if (articleArrayList == null || articleArrayList.isEmpty()) {
            System.out.println("No data loaded into ArrayList!");
            return;
        }

        // Collect integer IDs
        List<Integer> existingIds = new ArrayList<>();
        for (Article a : articleArrayList) {
            try {
                int id = Integer.parseInt(a.getId());
                if (id > 0) existingIds.add(id);
            } catch (NumberFormatException ignored) {}
        }

        if (existingIds.isEmpty()) {
            System.out.println("Dataset has no positive integer IDs.");
            return;
        }

        int maxExisting = Collections.max(existingIds);
        int runs = 30; // Keep the required 30 runs
        Random rand = new Random();
        int[] targets = new int[runs];

        // Half valid, half invalid positive integers
        for (int i = 0; i < runs; i++) {
            if (i % 2 == 0) {
                targets[i] = existingIds.get(rand.nextInt(existingIds.size())); // Will be found
            } else {
                targets[i] = maxExisting + 1000 + rand.nextInt(10000); // Not found
            }
        }

        // Benchmark all searchers
        Map<String, Stats> results = new LinkedHashMap<>();

        System.out.println("Starting benchmarks...");
        // Benchmark ArrayList algorithms
        runBench(results, "ArrayList Linear",
                t -> LinearSearch.linearSearchList(articleArrayList, String.valueOf(t)), targets);
        runBench(results, "ArrayList Jump",
                t -> JumpSearch.jumpSearchList(articleArrayList, String.valueOf(t)), targets);
        runBench(results, "ArrayList Fibonacci",
                t -> FibonacciSearch.fibonacciSearchList(articleArrayList, String.valueOf(t)), targets);
        runBench(results, "ArrayList Exponential",
                t -> ExponentialSearch.exponentialSearchList(articleArrayList, String.valueOf(t)), targets);

                // Benchmarks LinkedList algorithms
        runBench(results, "LinkedList Linear",
                t -> LinearSearch.linearSearchList(articleLinkedList, String.valueOf(t)), targets);
        runBench(results, "LinkedList Jump",
                t -> JumpSearch.jumpSearchList(articleLinkedList, String.valueOf(t)), targets);
        runBench(results, "LinkedList Fibonacci",
                t -> FibonacciSearch.fibonacciSearchList(articleLinkedList, String.valueOf(t)), targets);
        runBench(results, "LinkedList Exponential",
                t -> ExponentialSearch.exponentialSearchList(articleLinkedList, String.valueOf(t)), targets);

        // === Print analysis summary ===
        System.out.println("\n=== RandomKeyRace Analysis (30 runs) ===");
        double[] meanTimes = new double[results.size()];
        int index = 0;
        for (Map.Entry<String, Stats> e : results.entrySet()) {
            Stats s = e.getValue();
            System.out.printf("%-24s -> Best: %.3f ms, Mean: %.3f ms, Worst: %.3f ms%n",
                    e.getKey(),
                    s.best / 1_000_000.0,
                    s.mean() / 1_000_000.0,
                    s.worst / 1_000_000.0);
            meanTimes[index++] = s.mean() / 1_000_000.0; // Store mean times in milliseconds
        }

        // Compare algorithms
        compareAlgorithms(meanTimes);
    }

    // Tiny helper class for statistics
    private static class Stats {
        long best = Long.MAX_VALUE;
        long worst = Long.MIN_VALUE;
        long total = 0;
        int count = 0;

        void add(long time) {
            if (time < best) best = time;
            if (time > worst) worst = time;
            total += time;
            count++;
        }

        long mean() { return (count == 0) ? 0 : total / count; }
    }

    @FunctionalInterface
    private interface Runner {
        void run(int target);
    }

    private static void runBench(Map<String, Stats> out, String name,
                             Runner fn, int[] targets) {
        Stats stats = new Stats();
        System.out.println("Running benchmark for: " + name);
        for (int i = 0; i < targets.length; i++) {
            int t = targets[i];
            long start = System.nanoTime();
            fn.run(t); // Perform the search
            long dur = System.nanoTime() - start;
            stats.add(dur);

            // Log the time taken for this target
            System.out.printf("  Target %d/%d (%d): %.3f ms%n", i + 1, targets.length, t, dur / 1_000_000.0);
        }
        out.put(name, stats);
        System.out.println("Completed benchmark for: " + name);
    }

    private static void compareAlgorithms(double[] times) {
        String[] algorithmNames = {
            "ArrayList Linear", "ArrayList Jump", "ArrayList Fibonacci", "ArrayList Exponential",
            "LinkedList Linear", "LinkedList Jump", "LinkedList Fibonacci", "LinkedList Exponential"
        };

        String[] categories = { "ArrayList", "LinkedList" };
        int[][] ranges = { { 0, 4 }, { 4, 8 } }; // Start and end indices for ArrayList and LinkedList

        for (int k = 0; k < categories.length; k++) {
            System.out.printf("\n=== %s Algorithms Comparison ===%n", categories[k]);
            int start = ranges[k][0];
            int end = ranges[k][1];

            for (int i = start; i < end; i++) {
                for (int j = i + 1; j < end; j++) {
                    double speedup = times[j] / times[i];
                    if (times[i] < times[j]) {
                        System.out.printf("%s is %.2fx faster than %s%n",
                                algorithmNames[i], speedup, algorithmNames[j]);
                    } else {
                        speedup = times[i] / times[j];
                        System.out.printf("%s is %.2fx faster than %s%n",
                                algorithmNames[j], speedup, algorithmNames[i]);
                    }
                }
            }
        }
    }
}