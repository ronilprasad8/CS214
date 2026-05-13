package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class SearchID {
    private ArrayList<Article> articleArrayList;
    private LinkedList<Article> articleLinkedList;
    private final Scanner scanner = new Scanner(System.in);

    public SearchID(ArrayList<Article> articleArrayList, LinkedList<Article> articleLinkedList) {
        this.articleArrayList = articleArrayList;
        this.articleLinkedList = articleLinkedList;
    }

    public void performSearch() {
        System.out.print("Enter Article ID to search: ");
        String searchId = scanner.nextLine().trim();

        if (searchId.isEmpty()) {
            System.out.println("Invalid ID. Please enter a valid Article ID.");
            return;
        }

        // Exponential Search
        int resultArray = ExponentialSearch.exponentialSearchList(articleArrayList, searchId);
        if (resultArray >= 0) {
            System.out.println("Found in ArrayList (Exponential): " + articleArrayList.get(resultArray));
        } else {
            System.out.println("Not found in ArrayList (Exponential).");
        }

        int resultLinked = ExponentialSearch.exponentialSearchList(articleLinkedList, searchId);
        if (resultLinked >= 0) {
            System.out.println("Found in LinkedList (Exponential): " + articleLinkedList.get(resultLinked));
        } else {
            System.out.println("Not found in LinkedList (Exponential).");
        }

        // Linear Search
        int resultLinearArrayList = LinearSearch.linearSearchList(articleArrayList, searchId);
        if (resultLinearArrayList >= 0) {
            System.out.println("Found in ArrayList (Linear): " + articleArrayList.get(resultLinearArrayList));
        } else {
            System.out.println("Not found in ArrayList (Linear).");
        }

        int resultLinearLinked = LinearSearch.linearSearchList(articleLinkedList, searchId);
        if (resultLinearLinked >= 0) {
            System.out.println("Found in LinkedList (Linear): " + articleLinkedList.get(resultLinearLinked));
        } else {
            System.out.println("Not found in LinkedList (Linear).");
        }

        // Jump Search
        int resultJumpArray = JumpSearch.jumpSearchList(articleArrayList, searchId);
        if (resultJumpArray >= 0) {
            System.out.println("Found in ArrayList (Jump): " + articleArrayList.get(resultJumpArray));
        } else {
            System.out.println("Not found in ArrayList (Jump).");
        }

        int resultJumpLinked = JumpSearch.jumpSearchList(articleLinkedList, searchId);
        if (resultJumpLinked >= 0) {
            System.out.println("Found in LinkedList (Jump): " + articleLinkedList.get(resultJumpLinked));
        } else {
            System.out.println("Not found in LinkedList (Jump).");
        }

        // Fibonacci Search
        int resultFibArray = FibonacciSearch.fibonacciSearchList(articleArrayList, searchId);
        if (resultFibArray >= 0) {
            System.out.println("Found in ArrayList (Fibonacci): " + articleArrayList.get(resultFibArray));
        } else {
            System.out.println("Not found in ArrayList (Fibonacci).");
        }

        int resultFibLinked = FibonacciSearch.fibonacciSearchList(articleLinkedList, searchId);
        if (resultFibLinked >= 0) {
            System.out.println("Found in LinkedList (Fibonacci): " + articleLinkedList.get(resultFibLinked));
        } else {
            System.out.println("Not found in LinkedList (Fibonacci).");
        }
    }

    // Add this method to properly close the scanner when done
    public void closeScanner() {
        scanner.close();
    }
}