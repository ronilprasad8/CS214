CS214 Assignment One 
Semester 2, 2025
University of the South Pacific

Contributers - Ronil Prasad (S11231541)
             - Praheel Kumar (S11229535)
             - Shivan Prasad (S11231502)

                    Project Overview
This assignment is developed to investigate and analyze the performance of different search algorithms on ArrayList and LinkedList data structure, evaluate their efficiency and visualize results using a third party graphics library (JFreeChart).

The Project Fulfills the Following assignment task:

1. Is the implementation of 4 different search algorithms (Linear Search, Jump Search, Exponential Search and Fibonacci Search) on both ArrayList and LinkedList.

2. Race and compare the algorithms using random test cases and later visualize using JFreeChart.

3. Randomly selecting keys across multiple runs (including keys that are not present) to analyse best, mean and worst case results 

4. Determine the worst case time complexity analysis with grapical comparison.

                    Structure:
App.java - is the entry point for running experiments.

Article.java - it represents the dataset records for both ArrayList and LinkedList.

Searcher.java - An interface for all search algorithm.

LinearSearch - implements linear search for both lists.

JumpSearch.java - implements jump search.

ExponentialSearch.java - implements Exponential search.

FibonacciSearch.java - implements Fibonnaci search.

RaceVisualizer.java - runs and races all algorithm on random arrays, then visualizes results with JFreeChart.

RandomKeyRace.java - Executes algorithm 30 times with random keys (including missing ones) and then collects and reports the best/mean/worst cases.

DetermineWorstCaseComplexity.java - it analyses and plots worst case time complexity of each algorithm

SearchID.java - Handles searching in dataset (Using ID field).

                        How to Run

Open the project in an IDE preferably - VS Code/ Netbeans.
Refresh the pom.xml file to download all dependencies.
Run the main class corresponding to the task you wish to execute (Refer to Structure section for detailed explanations).

                        Dependencies

Java 17+
Maven 3.8+
JFreeChart 1.5.4
JCommon 1.0.24
JUnit 3.8.1 (for testing)

                        Build System
Maven is used for managing dependencies, compiling and running this project.

                        Resources
Urban Sound Dataset (Kaggle) - Dataset used to populate Article.java in both ArrayList and LinkedList.

                        Project Structure
```
CS214/
├── Group_Members
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── App.java
│   │       ├── Article.java
│   │       ├── search/
│   │       │   ├── Searcher.java
│   │       │   ├── LinearSearch.java 
│   │       │   ├── JumpSearch.java
│   │       │   ├── ExponentialSearch.java
│   │       │   ├── FibonacciSearch.java
│   │       │   └── SearchID.java
│   │       └── visualization/
│   │           ├── RaceVisualizer.java
│   │           ├── RandomKeyRace.java
│   │           └── DetermineWorstCaseComplexity.java
│   └── test/
│       └── java/
│           └── AppTest.java
├── pom.xml
└── README.md
```

