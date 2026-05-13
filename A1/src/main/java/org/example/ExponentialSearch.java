package org.example;

import java.util.List;

public class ExponentialSearch implements Searcher {

    /**
     * Generic exponential search for any List<Article>
     * Exponential search works by doubling the range until the target is within range
     * then performing binary search within that range
     * 
     * @param list The sorted list of Article to search4
     * @param x The Article ID to search for
     * @return Index of the Artice if found, -1 otherwise
     */ 
    
    public static int exponentialSearchList(List<Article> list, String x) {
        int n = list.size();
        if (n == 0) return -1;

        // Check first element
        if (list.get(0).getId().equals(x)) return 0;

        // Find range by repeated doubling (exponential growth)
        int i = 1;
        while (i < n && list.get(i).getId().compareTo(x) <= 0) {
            i = i * 2;
        }

        // Call binary search in found range
        return binarySearch(list, i / 2, Math.min(i, n - 1), x);
    }

    /** Private binary search helper for any List<Article>
     * 
     * @param list The sorted list to search
     * @param left Left boundary of search range
     * @param right Right boundary of search range
     * @param x The Article ID to search for
     * @return Index of the Article if found, -1 otherwise
    */
    private static int binarySearch(List<Article> list, int left, int right, String x) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = list.get(mid).getId().compareTo(x);

            if (cmp == 0) return mid;
            if (cmp < 0) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    /**
    * Implementation for arrays (to satisfy Searcher interface)
    * Required to satisfy the Searcher interface
    * @param arr The sorted integer array to search
    * @param target The integer value to search for
    */
    @Override
    public void search(int[] arr, int target) {
        int n = arr.length;
        if (n == 0) {
            System.out.println("Not found");
            return;
        }

        if (arr[0] == target) {
            System.out.println("Found at index 0");
            return;
        }

        int i = 1;
        while (i < n && arr[i] <= target) {
            i = i * 2;
        }

        // Binary search in found range
        int result = binarySearch(arr, i / 2, Math.min(i, n - 1), target);
        if (result != -1) {
            System.out.println("Found at index " + result);
        } else {
            System.out.println("Not found");
        }
    }

    /**
     *  Private binary search helper for int[] 
     * 
     * @param arr The sorted array to search 
     * @param left Left boundary of search range
     * @param right Right boundary of search range
     * @param target The value to search for
     * @return Index of the target if found, -1 otherwise 
     */ 
    private int binarySearch(int[] arr, int left, int right, int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) return mid;
            if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    @Override
    public String getName() {
        return "Exponential Search";
    }
}
