package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Data structue to hold Article objects
        ArrayList<Article> articleArrayList = new ArrayList<>();
        LinkedList<Article> articleLinkedList = new LinkedList<>();

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("Article.csv")) {
            if (inputStream == null) {
                System.out.println("File not found in resources!");
                return;
            }

            // Read and parse the CSV file
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;

                // Skip header
                br.readLine();
                
                // Process each line of the CSV file
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",", -1);

                    if (values.length < 3) continue;

                    // Create Article objexts from CSV data
                    Article article = new Article(
                            values[0].trim(), // ID
                            values[1].trim(), // Title
                            values[2].trim()  // Abstract
                    );

                    // Add to both data structure
                    articleArrayList.add(article);
                    articleLinkedList.add(article);
                }
            }

            // Sort both lists by ID
            Comparator<Article> byId = Comparator.comparing(Article::getId);
            articleArrayList.sort(byId);
            articleLinkedList.sort(byId);

            Scanner scanner = new Scanner(System.in);

            // Main program loop
            while (true) {
                System.out.println("\nSelect an option:");
                System.out.println("1. Race all algorithms simultaneously.");
                System.out.println("2. Run algorithms 30 times with random keys (best/mean/worst)");
                System.out.println("3. Determine the worst-case time complexity graphically");
                System.out.println("4. Search for a specific Article ID using all algorithms");
                System.out.println("0. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                // Execute selected option
                switch (choice) {
                    case 1:
                        raceAlgorithmsSimulataneously option1 = new raceAlgorithmsSimulataneously(articleArrayList, articleLinkedList);
                        option1.execute();
                        break;

                    case 2:
                        RandomKeyRace.runRandomKeyRace(articleArrayList, articleLinkedList);
                        break;

                    case 3:
                        determineWorstCaseComplexity option3 = new determineWorstCaseComplexity();
                        option3.execute();
                        break;

                    case 4:              
                        SearchID searchOption = new SearchID(articleArrayList, articleLinkedList);
                        searchOption.performSearch();
                        break;

                    case 0:
                        System.out.println("Exiting...");
                        scanner.close(); // Close the scanner
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
