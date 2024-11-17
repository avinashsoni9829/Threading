package com.example.multithreading.delayed;


import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DelayedQueueExample {
    public static void main(String[] args) {
        DelayQueue<DelayedTask> dQ = new DelayQueue<>();

        dQ.offer(new DelayedTask(5, TimeUnit.SECONDS));
        dQ.offer(new DelayedTask(10,TimeUnit.SECONDS));
        dQ.offer(new DelayedTask(20,TimeUnit.SECONDS));


        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            while (!dQ.isEmpty()){
                try {
                    DelayedTask task = dQ.take(); // blocked Until Task is Happening
                    task.execute();
                } catch (InterruptedException e){
                     Thread.currentThread().interrupt();
                }
            }
        });

        executor.shutdown();
    }
}
