package com.example.multithreading.FutureTask;

import java.util.concurrent.*;

public class DataFetchExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Callable<String> fetchUserData = () -> {
            // Simulate data fetching from a user database
            Thread.sleep(1000);
            return "User Data";
        };

        Callable<String> fetchOrderData = () -> {
            // Simulate data fetching from an order service
            Thread.sleep(1500);
            return "Order Data";
        };

        Callable<String> fetchInventoryData = () -> {
            // Simulate data fetching from an inventory service
            Thread.sleep(1200);
            return "Inventory Data";
        };

        FutureTask<String> userTask = new FutureTask<>(fetchUserData);
        FutureTask<String> orderTask = new FutureTask<>(fetchOrderData);
        FutureTask<String> inventoryTask = new FutureTask<>(fetchInventoryData);
        Long timeStart = System.currentTimeMillis();
        executor.submit(userTask);
        executor.submit(orderTask);
        executor.submit(inventoryTask);

        try {
            Thread.sleep(1300);
            boolean cancelled =  orderTask.cancel(true);
            System.out.println(cancelled);
            if(userTask.isDone()){
                System.out.println(userTask.get());
            }
            if(inventoryTask.isDone()){
                System.out.println(inventoryTask.get());
            }
            if(orderTask.isDone()){
                System.out.println(orderTask.get());
            }

//            System.out.println("Fetched Data: " + userTask.get() + ", " + orderTask.get() + ", " + inventoryTask.get());
//            Long timeEnd = System.currentTimeMillis();
//            System.out.println("time taken = " + (timeEnd - timeStart));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();

    }
}
