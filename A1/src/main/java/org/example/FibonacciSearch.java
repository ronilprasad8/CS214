package org.example;

import java.util.List;

public class FibonacciSearch implements Searcher {

    /**
     * Generic Fibonacci search for any List<Article>
     * Fibonacci search uses Fibonacci numbers to divide the search space
     * and is particularly efficient for large arrays
     * 
     * @param list The sorted list of Articles to search
     * @param searchId The Article ID to search for
     * @return Index of the Aritcle if found, -1 otherwise
     */
    public static int fibonacciSearchList(List<Article> list, String searchId) {
        int n = list.size();

        // Initialize Fibonacci numbers
        int a = 0, b = 1, c = a + b;

        // c is the smallest Fibonacci number >= n
        while (c < n) {
            a = b;
            b = c;
            c = a + b;
        }

        int offset = -1;

        // Main Fibonacci search algorithm
        while (c > 1) {
            int i = Math.min(offset + a, n - 1);
            int cmp = list.get(i).getId().compareTo(searchId);

            if (cmp < 0) {
                c = b;
                b = a;
                a = c - b;
                offset = i;
            } else if (cmp > 0) {
                c = a;
                b = b - a;
                a = c - b;
            } else {
                return i; // found
            }
        }

        // Check the last element
        if (b != 0 && offset + 1 < n && list.get(offset + 1).getId().equals(searchId)) {
            return offset + 1;
        }

        return -1; // not found
    }

    /**
     * Implementation for arrays (to satisfy Searcher interface)
     * Required to satisfy the Searcher interface
     * 
     * @param arr The sorted integer array to search
     * @param target The integer value to search for
     */
    @Override
    public void search(int[] arr, int target) {
        int n = arr.length;

        // Initialize Fibonacci numbers
        int a = 0, b = 1, c = a + b;
        // Find the smallest Fibonacci number greater than or equal to n
        while (c < n) {
            a = b;
            b = c;
            c = a + b;
        }

        int offset = -1;

        // Mian Fibonacci search algorithm
        while (c > 1) {
            int i = Math.min(offset + a, n - 1);

            if (arr[i] < target) {
                c = b;
                b = a;
                a = c - b;
                offset = i;
            } else if (arr[i] > target) {
                c = a;
                b = b - a;
                a = c - b;
            } else {
                System.out.println("Found at index " + i);
                return;
            }
        }

        // Check the last element
        if (b != 0 && offset + 1 < n && arr[offset + 1] == target) {
            System.out.println("Found at index " + (offset + 1));
        } else {
            System.out.println("Not found");
        }
    }

    @Override
    public String getName() {
        return "Fibonacci Search";
    }
}
