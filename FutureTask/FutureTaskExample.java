package com.example.multithreading.FutureTask;


import java.util.concurrent.*;

public class FutureTaskExample {
    public static void main(String[] args) {
        executorCallable();
    }


    public static  void executorCallable(){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Callable<Integer> callableTask1 = () -> {
            Thread.sleep(1000);
            return 1;
        };
        Callable<Integer> callableTask2 = () -> {
            Thread.sleep(2000);
            return 2;
        };
        FutureTask<Integer> futureTask1 = new FutureTask<>(callableTask1);
        FutureTask<Integer> futureTask2 = new FutureTask<>(callableTask2);

        executor.submit(futureTask1);
        executor.submit(futureTask2);


        try {
            System.out.println("Result from FutureTask1: " + futureTask1.get());
            System.out.println("Result from FutureTask2: " + futureTask2.get());
        }catch (Exception e) {
        e.printStackTrace();
       }

        executor.shutdown();
    }
    public static void cancelTask(){
        Callable<Integer> callable = () -> {

            Thread.sleep(5000);
            return 10;
        };

        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();

        try {
            Thread.sleep(1000);
            boolean cancelled = futureTask.cancel(true);
            System.out.println("Task cancelled: " + cancelled);
            System.out.println("Is task cancelled? " + futureTask.isCancelled());
            System.out.println("result = " + futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    public static void callableTask(){
        Callable<Integer> callable = () -> {
            Thread.sleep(2000);
            return 10;
        };

        FutureTask<Integer> futureTask = new FutureTask<>(callable);

        Thread thread = new Thread(futureTask);
        thread.start();

        System.out.println("doing other work");

        try {
            Integer res = futureTask.get();
            System.out.println("result" + res);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
