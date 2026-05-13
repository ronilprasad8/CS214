package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class raceAlgorithmsSimulataneously {
    private final ArrayList<Article> articleArrayList;
    private final LinkedList<Article> articleLinkedList;
    private final Random random = new Random();

    public raceAlgorithmsSimulataneously(ArrayList<Article> arrayList, LinkedList<Article> linkedList) {
        this.articleArrayList = arrayList;
        this.articleLinkedList = linkedList;
    }

    /**
     * Main execution method that races all search algorithms simultaneously
     * Compares performance of different search algorithms on both ArrayList and LinkedList
     */
    public void execute() {
        System.out.println("Racing all algorithms simultaneously...");

        // Generate 5 random positive integer test cases
        List<String> testCases = generateRandomPositiveIntegerTestCases(5);

        // Collect average execution times for ArrayList and LinkedList
        List<AverageResult> arrayListResults = new ArrayList<>();
        List<AverageResult> linkedListResults = new ArrayList<>();

        for (String testCase : testCases) {
            System.out.println("\nTesting with ID: " + testCase);

            // Run algorithms and collect results
            List<SearchResult> results = raceAlgorithms(testCase);

            // Separate results for ArrayList and LinkedList
            for (SearchResult result : results) {
                if (result.getAlgorithmName().contains("ArrayList")) {
                    addToAverageResults(arrayListResults, result);
                } else if (result.getAlgorithmName().contains("LinkedList")) {
                    addToAverageResults(linkedListResults, result);
                }
            }
        }

        // Print performance results in the terminal
        System.out.println("\n=== ArrayList Algorithm Performance ===");
        printPerformanceResults(arrayListResults);

        System.out.println("\n=== LinkedList Algorithm Performance ===");
        printPerformanceResults(linkedListResults);

        // Visualize results using bar charts
        createAndShowBarChart(arrayListResults, "ArrayList Algorithm Performance");
        createAndShowBarChart(linkedListResults, "LinkedList Algorithm Performance");
    }

    /**
     * Generates random positive integer test cases from available Articles IDs
     * 
     * @param count Number of test cases to generate
     * @return List of test case IDs
     */
    private List<String> generateRandomPositiveIntegerTestCases(int count) {
        List<String> testCases = new ArrayList<>();
        List<String> allIds = new ArrayList<>();

        // Collect all positive integer IDs from the article list
        for (Article article : articleArrayList) {
            if (isPositiveInteger(article.getId())) {
                allIds.add(article.getId());
            }
        }

        // Generate random test cases from the available IDs
        for (int i = 0; i < count; i++) {
            testCases.add(allIds.get(random.nextInt(allIds.size())));
        }

        return testCases;
    }

    /**
     * Checks if a string represents a positive integer
     * 
     * @param id The string to check
     * @return true if the string is a positive integer, false otherwise
     */
    private boolean isPositiveInteger(String id) {
        try {
            int value = Integer.parseInt(id);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Runs all search algorithms concurrently on a test case
     * Uses multithreading to execute searches simulataneously
     * 
     * @param searchId The ID to search for
     * @return List of search results with execution times
     */
    private List<SearchResult> raceAlgorithms(String searchId) {
        ExecutorService executor = Executors.newFixedThreadPool(8);
        List<Callable<SearchResult>> tasks = new ArrayList<>();

        // Create tasks for each search algorithm on ArrayList
        tasks.add(() -> {
            long startTime = System.nanoTime();
            int result = ExponentialSearch.exponentialSearchList(articleArrayList, searchId);
            long endTime = System.nanoTime();
            return new SearchResult("ArrayList Exponential", result, endTime - startTime);
        });

        tasks.add(() -> {
            long startTime = System.nanoTime();
            int result = LinearSearch.linearSearchList(articleArrayList, searchId);
            long endTime = System.nanoTime();
            return new SearchResult("ArrayList Linear", result, endTime - startTime);
        });

        tasks.add(() -> {
            long startTime = System.nanoTime();
            int result = JumpSearch.jumpSearchList(articleArrayList, searchId);
            long endTime = System.nanoTime();
            return new SearchResult("ArrayList Jump", result, endTime - startTime);
        });

        tasks.add(() -> {
            long startTime = System.nanoTime();
            int result = FibonacciSearch.fibonacciSearchList(articleArrayList, searchId);
            long endTime = System.nanoTime();
            return new SearchResult("ArrayList Fibonacci", result, endTime - startTime);
        });

        // Create tasks for each search algorithm on LinkedList
        tasks.add(() -> {
            long startTime = System.nanoTime();
            int result = ExponentialSearch.exponentialSearchList(articleLinkedList, searchId);
            long endTime = System.nanoTime();
            return new SearchResult("LinkedList Exponential", result, endTime - startTime);
        });

        tasks.add(() -> {
            long startTime = System.nanoTime();
            int result = LinearSearch.linearSearchList(articleLinkedList, searchId);
            long endTime = System.nanoTime();
            return new SearchResult("LinkedList Linear", result, endTime - startTime);
        });

        tasks.add(() -> {
            long startTime = System.nanoTime();
            int result = JumpSearch.jumpSearchList(articleLinkedList, searchId);
            long endTime = System.nanoTime();
            return new SearchResult("LinkedList Jump", result, endTime - startTime);
        });

        tasks.add(() -> {
            long startTime = System.nanoTime();
            int result = FibonacciSearch.fibonacciSearchList(articleLinkedList, searchId);
            long endTime = System.nanoTime();
            return new SearchResult("LinkedList Fibonacci", result, endTime - startTime);
        });
        
        try {
            // Execute all tasks concurrently
            List<Future<SearchResult>> futures = executor.invokeAll(tasks);
            List<SearchResult> results = new ArrayList<>();

            for (Future<SearchResult> future : futures) {
                results.add(future.get());
            }

            return results;

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error during algorithm race: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            executor.shutdown();
        }
    }

    /**
     * Adds a search result to the average results list
     * 
     * @param averageResults List to add to
     * @param result Search results to add
     */
    private void addToAverageResults(List<AverageResult> averageResults, SearchResult result) {
        for (AverageResult avgResult : averageResults) {
            if (avgResult.getAlgorithmName().equals(result.getAlgorithmName())) {
                avgResult.addTime(result.getTimeTaken());
                return;
            }
        }
        averageResults.add(new AverageResult(result.getAlgorithmName(), result.getTimeTaken()));
    }

    /**
     * Creates and displays a bar chart of performance results
     * 
     * @param averageResults Results to visualize
     * @param title Chart title
     */
    private void createAndShowBarChart(List<AverageResult> averageResults, String title) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (AverageResult avgResult : averageResults) {
            dataset.addValue(avgResult.getAverageTime(), "Average Time (ns)", avgResult.getAlgorithmName());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                title,
                "Algorithm",
                "Average Time (nanoseconds)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        chart.getCategoryPlot().setBackgroundPaint(Color.WHITE);

        ChartFrame frame = new ChartFrame(title, chart);
        frame.pack();
        frame.setVisible(true);

        System.out.println("Close the chart to continue");
    }

    /** 
     * Prints performance results to the terminal
     * 
     * @param averageResults Results to print
     */
    private void printPerformanceResults(List<AverageResult> averageResults) {
        for (AverageResult avgResult : averageResults) {
            System.out.printf("%-25s: Average Time = %.2f ns%n",
                    avgResult.getAlgorithmName(), (double) avgResult.getAverageTime());
        }
    }

    //  Inner class to store individual search results
    static class SearchResult {
        private final String algorithmName;
        private final int index;
        private final long timeTaken;

        public SearchResult(String algorithmName, int index, long timeTaken) {
            this.algorithmName = algorithmName;
            this.index = index;
            this.timeTaken = timeTaken;
        }

        public String getAlgorithmName() {
            return algorithmName;
        }

        public long getTimeTaken() {
            return timeTaken;
        }

        public int getIndex() {
            return index;
        }
    }

    /**
     * Inner class to store average results across multiple tests
     */
    static class AverageResult {
        private final String algorithmName;
        private long totalTime;
        private int count;

        public AverageResult(String algorithmName, long time) {
            this.algorithmName = algorithmName;
            this.totalTime = time;
            this.count = 1;
        }

        public void addTime(long time) {
            this.totalTime += time;
            this.count++;
        }

        public String getAlgorithmName() {
            return algorithmName;
        }

        public long getAverageTime() {
            return totalTime / count;
        }
    }
}