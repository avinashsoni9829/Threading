package com.example.multithreading.synchronousQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

public class MultiProducerConsumer {
    public static void main(String[] args) {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Producers
        for (int i = 1; i <= 2; i++) {
            final int producerId = i;
            executor.submit(() -> {
                try {
                    String message = "Message from Producer " + producerId;
                    System.out.println("Producer " + producerId + ": Putting - " + message);
                    queue.put(message); // Block until a consumer takes it
                    System.out.println("Producer " + producerId + ": Message taken");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Consumers
        for (int j = 1; j <= 2; j++) {
            final int consumerId = j;
            executor.submit(() -> {
                try {
                    Thread.sleep(500); // Simulate some delay
                    System.out.println("Consumer " + consumerId + ": Waiting to take...");
                    String message = queue.take(); // Block until a producer puts a message
                    System.out.println("Consumer " + consumerId + ": Took - " + message);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
    }
}
