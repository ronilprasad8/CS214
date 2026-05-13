# CS214 Coursework

This repository contains the assignments and projects for CS214, focusing on algorithm design, implementation, and empirical analysis. The repository is structured into two main Maven-based Java projects: Assignment 1 (A1) and Assignment 2 (A2).

## Repository Structure

### [A1: Search Algorithms and Complexity Analysis](./A1)
This project explores various search algorithms and compares their performance and complexity.

**Key Components:**
* **Search Algorithms**: Implementations of multiple search techniques, including:
    * Linear Search (`LinearSearch.java`)
    * Jump Search (`JumpSearch.java`)
    * Fibonacci Search (`FibonacciSearch.java`)
    * Exponential Search (`ExponentialSearch.java`)
* **Benchmarking & Analysis**: Tools to simulate races between algorithms (`raceAlgorithmsSimulataneously.java`, `RandomKeyRace.java`) and determine their worst-case complexities (`determineWorstCaseComplexity.java`).
* **Data Processing**: Reads and processes article data from a CSV dataset (`Article.csv`).

### [A2: Traveling Salesperson Problem (TSP) Solvers](./A2)
This project tackles the Traveling Salesperson Problem using both exact and heuristic algorithmic approaches, along with tools for empirical testing.

**Key Components:**
* **Algorithms**: 
    * Dynamic Programming Approach (`TSPDP.java`)
    * Genetic Algorithm Approach (`TSPGA.java`)
* **Data Parsing**: A robust parser (`TSPParser.java`) to read standard `.tsp` and `.atsp` benchmark files (e.g., `burma14.tsp`, `br17.atsp`, `ft53.atsp`).
* **Empirical Testing & Visualization**: Includes benchmarking utilities (`BenchmarkRunner.java`, `EmpiricalTesting.java`) and a charting tool (`RealTimeChart.java`) to visualize the performance of the solvers.

## Prerequisites
* **Java**: JDK 11 or higher recommended.
* **Maven**: Both A1 and A2 are configured as Maven projects (`pom.xml`), which handle dependencies and build lifecycles.

## Building and Running
Navigate to either the `A1` or `A2` directory and use Maven to clean, compile, and run the applications.

```bash
# Example for Assignment 1
cd A1
mvn clean install
mvn exec:java -Dexec.mainClass="org.example.Main"

# Example for Assignment 2
cd ../A2
mvn clean install
mvn exec:java -Dexec.mainClass="org.example.App"
