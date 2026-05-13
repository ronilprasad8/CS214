package org.example;

import java.awt.Color;
import java.awt.Dimension;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;

public class determineWorstCaseComplexity {

    public void execute() {
        System.out.println("Analyzing worst-case time complexity...");

        // Generate a range of sizes with step size
        int[] sizes = generateSizes(0, 5000, 5); // Start at 0, end at 5000, step by 5
        Map<String, List<Long>> arrayListResults = new HashMap<>();
        Map<String, List<Long>> linkedListResults = new HashMap<>();

        String[] algorithms = {"Exponential", "Linear", "Jump", "Fibonacci"};

        // Initialize results for each algorithm
        for (String algo : algorithms) {
            arrayListResults.put(algo, new ArrayList<>());
            linkedListResults.put(algo, new ArrayList<>());
        }

        // Measure execution times for each algorithm and data structure
        for (int size : sizes) {
            List<Article> testArrayList = generateTestData(size);
            List<Article> testLinkedList = new LinkedList<>(testArrayList);

            testArrayList.sort(Comparator.comparing(Article::getId));
            testLinkedList.sort(Comparator.comparing(Article::getId));

            String worstCaseKey = "Non Existent Key";

            // Measure times for ArrayList
            arrayListResults.get("Exponential").add(measureTime(() -> ExponentialSearch.exponentialSearchList(testArrayList, worstCaseKey)));
            arrayListResults.get("Linear").add(measureTime(() -> LinearSearch.linearSearchList(testArrayList, worstCaseKey)));
            arrayListResults.get("Jump").add(measureTime(() -> JumpSearch.jumpSearchList(testArrayList, worstCaseKey)));
            arrayListResults.get("Fibonacci").add(measureTime(() -> FibonacciSearch.fibonacciSearchList(testArrayList, worstCaseKey)));

            // Measure times for LinkedList
            linkedListResults.get("Exponential").add(measureTime(() -> ExponentialSearch.exponentialSearchList(testLinkedList, worstCaseKey)));
            linkedListResults.get("Linear").add(measureTime(() -> LinearSearch.linearSearchList(testLinkedList, worstCaseKey)));
            linkedListResults.get("Jump").add(measureTime(() -> JumpSearch.jumpSearchList(testLinkedList, worstCaseKey)));
            linkedListResults.get("Fibonacci").add(measureTime(() -> FibonacciSearch.fibonacciSearchList(testLinkedList, worstCaseKey)));
        }

        // Create charts for each algorithm and data structure
        Map<String, JFreeChart> charts = new HashMap<>();
        for (String algorithm : algorithms) {
            charts.put("ArrayList - " + algorithm, createChart(sizes, createSingleEntryMap(algorithm, arrayListResults.get(algorithm)), "ArrayList - " + algorithm));
            charts.put("LinkedList - " + algorithm, createChart(sizes, createSingleEntryMap(algorithm, linkedListResults.get(algorithm)), "LinkedList - " + algorithm));
        }

        // Display all charts in a single JFrame with tabs
        showCharts(charts);
    }

    private static int[] generateSizes(int start, int end, int step) {
        int sizeCount = ((end - start) / step) + 1;
        int[] sizes = new int[sizeCount];
        for (int i = 0; i < sizeCount; i++) {
            sizes[i] = start + (i * step);
        }
        return sizes;
    }

    private static List<Article> generateTestData(int size) {
        List<Article> testData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String id = String.valueOf(i);
            testData.add(new Article(id, "Test Title " + i, "Test Abstract " + i));
        }
        return testData;
    }

    private static long measureTime(Runnable algorithm) {
        long startTime = System.nanoTime();
        algorithm.run();
        return System.nanoTime() - startTime;
    }

    private static JFreeChart createChart(int[] sizes, Map<String, List<Long>> results, String title) {
        DefaultXYDataset dataset = new DefaultXYDataset();

        for (String algorithm : results.keySet()) {
            List<Long> times = results.get(algorithm);
            double[][] seriesData = new double[2][sizes.length];

            for (int i = 0; i < sizes.length; i++) {
                seriesData[0][i] = sizes[i];
                seriesData[1][i] = times.get(i); // Times are in nanoseconds
            }

            dataset.addSeries(algorithm, seriesData);
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Input Size",
                "Time (nanoseconds)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinePaint(Color.BLACK);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesShapesVisible(i, true);
            renderer.setSeriesShapesFilled(i, true);
        }
        plot.setRenderer(renderer);

        return chart;
    }

    private static void showCharts(Map<String, JFreeChart> charts) {
        JFrame frame = new JFrame("Worst-Case Search Time Complexity");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();

        for (Map.Entry<String, JFreeChart> entry : charts.entrySet()) {
            String tabName = entry.getKey();
            JFreeChart chart = entry.getValue();
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(800, 600));
            tabbedPane.addTab(tabName, chartPanel);
        }

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private static Map<String, List<Long>> createSingleEntryMap(String key, List<Long> value) {
        Map<String, List<Long>> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}
