package com.example.multithreading.FutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class LogCleaner {

    private final FutureTask<Void> cleanupTask;

    public LogCleaner() {
        Callable<Void> task = () -> {
            // Simulate log cleanup
            System.out.println("Cleaning up logs...");
            Thread.sleep(1000);
            return null;
        };

        cleanupTask = new FutureTask<>(task);
    }

    public void performCleanup() {
        for (int i = 0; i < 5; i++) { // Perform cleanup 5 times
            cleanupTask.runAndReset();
        }
        cleanupTask.run(); // Final run to complete the task
        try {
            cleanupTask.get(); // Get final status
            System.out.println("Final cleanup completed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LogCleaner cleaner = new LogCleaner();
        cleaner.performCleanup();
    }
}
