package org.adp_implementatie;

public class PerformanceBenchmark {
    private long startTime;
    private long endTime;
    private long operations;

    public void start() {
        startTime = System.nanoTime();
        operations = 0;
    }

    public void recordOperation() {
        operations++;
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public double getElapsedTimeInSeconds() {
        return (endTime - startTime) / 1e9;
    }

    public long getOperations() {
        return operations;
    }

    public double getOperationsPerSecond() {
        return operations / getElapsedTimeInSeconds();
    }

    public void printElapsedTime() {
        double elapsedTime = getElapsedTimeInSeconds();
        System.out.printf("%.9f seconden %n", elapsedTime);
    }

    public String getElapsedTime() {
        double elapsedTime = getElapsedTimeInSeconds();

        return String.format("%.9f seconden", elapsedTime);
    }
}
