package org.adp_implementatie;

import java.text.DecimalFormat;

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

    public String formatElapsedTime(double elapsedTime) {
        // Gebruik DecimalFormat om exponentiële notatie te voorkomen
        DecimalFormat df = new DecimalFormat("#.########");
        return df.format(elapsedTime) + " seconden";
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
}
