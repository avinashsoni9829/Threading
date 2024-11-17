package com.example.multithreading.synchronousQueue;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueExample1 {
    public static void main(String[] args) {
        SynchronousQueue<Integer> q = new SynchronousQueue<>();
        Thread producer = new Thread(() -> {
             try{
                 System.out.println("Putting 42");
                 q.put(42);
                 System.out.println("Consumer taken" + 42);
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
        });

        Thread consumer = new Thread(() -> {
             try {
                 Thread.sleep(1000);
                 System.out.println("Consumer Waiting to Take ...");
                 int val = q.take();
                 System.out.println("Consumer took " + val);
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
        });

        producer.start();
        consumer.start();
    }
}
