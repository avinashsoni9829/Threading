package com.example.multithreading.LinkedBlockingQueue;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueExample {
    public static void main(String[] args) {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(3);

        Thread producer = new Thread(() -> {
            try {
                q.put(1);
                System.out.println("Produced 1 ");
                q.put(2);
                System.out.println("Produced 2");

                q.put(3);
                System.out.println("Produced 3");
                Thread.sleep(1000);
                q.put(4);
                System.out.println("Produced 4");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumer = new Thread(() -> {
             try {

                 System.out.println("Consumer took " + q.take());
                 Thread.sleep(2000);
                 System.out.println("Consumer took " + q.take());
                 System.out.println("Consumer took " + q.take());
                 System.out.println("Consumer took " + q.take());
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
        });

        producer.start();
        consumer.start();


    }
}
