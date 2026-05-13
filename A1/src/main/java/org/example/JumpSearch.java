package org.example;

import java.util.List;

public class JumpSearch implements Searcher {

    /**
     * Generic jump search for any List<Article>
     * Jump search works by jumping ahead fixed steps and then performing
     * linear search in the identified block
     * 
     * @param list The sorted list of Articles to search
     * @param searchID The Article ID to search for
     * @return Index of the Article if found, -1 otherwise
     */
    public static int jumpSearchList(List<Article> list, String searchId) {
        int n = list.size();
        int step = (int) Math.floor(Math.sqrt(n));
        int prev = 0;

        // Find the block where the element may be present
        while (prev < n && list.get(Math.min(step, n) - 1).getId().compareTo(searchId) < 0) {
            prev = step;
            step += (int) Math.floor(Math.sqrt(n));
            if (prev >= n) {
                return -1;
            }
        }

        // Linear search within the identified block
        while (prev < n && list.get(prev).getId().compareTo(searchId) < 0) {
            prev++;
            if (prev == Math.min(step, n)) {
                return -1;
            }
        }

        // Check if found
        if (prev < n && list.get(prev).getId().equals(searchId)) {
            return prev;
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
        int step = (int) Math.floor(Math.sqrt(n));
        int prev = 0;

        // Find the block
        while (prev < n && arr[Math.min(step, n) - 1] < target) {
            prev = step;
            step += (int) Math.floor(Math.sqrt(n));
            if (prev >= n) {
                System.out.println("Not found");
                return;
            }
        }

        // Linear search within block
        while (prev < n && arr[prev] < target) {
            prev++;
            if (prev == Math.min(step, n)) {
                System.out.println("Not found");
                return;
            }
        }

        // Check if found
        if (prev < n && arr[prev] == target) {
            System.out.println("Found at index " + prev);
        } else {
            System.out.println("Not found");
        }
    }

    @Override
    public String getName() {
        return "Jump Search";
    }
}
