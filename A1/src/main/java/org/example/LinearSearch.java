package org.example;

import java.util.List;

public class LinearSearch implements Searcher {

    /** 
     * Generic linear search for any List<Article>
     * Linear search checks each element sequentially until the target is found
     * Simple but inefficient for large datasets (0(n) time complexity)
     * 
     * @param list The list of Articles to search (does not need to be sorted)
     * @param searchId The Article ID to search for
     * @return Index of the Article ID to search for
     * @return Index of the Article if found, -1 otherwise
     */
    public static int linearSearchList(List<Article> list, String searchId) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(searchId)) {
                return i; // return index if found
            }
        }
        return -1; // not found
    }

    /**
     * Implementation for arrays (to satisfy Searcher interface)
     * Requiredt to satisfy the Searcher interface
     * 
     * @param arr The interger array to search
     * @param target The integer value to search for
     */
    @Override
    public void search(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                System.out.println("Found at index " + i);
                return;
            }
        }
        System.out.println("Not found");
    }

    @Override
    public String getName() {
        return "Linear Search";
    }
}
