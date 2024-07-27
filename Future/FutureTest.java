package com.example.multithreading.concurrency.Future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest {
    public static void blockingScenario(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> processTask1 = executorService.submit(() -> processTask("task-1",2000));
        Future<String> processTask2 = executorService.submit(() -> processTask("task-2",10000));
        System.out.println("Processing Task1 ");

        try {
            String res1 = processTask1.get();
            String res2 = processTask2.get();
            System.out.println("Res 1 "+ res1);
            System.out.println("Res 2 "+ res2);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            executorService.shutdown();
        }

        try {
            System.out.println("performing other task work");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            System.out.println("Done with the other task");
        }
    }

    public static void nonBlockingScenario(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> processTask1 = executorService.submit(() -> processTask("task-1",2000));
        Future<String> processTask2 = executorService.submit(() -> processTask("task-2",10000));

        System.out.println("Processing Task1 ");
        System.out.println("Doing Some other task ");

        while (!(processTask1.isDone() && processTask2.isDone())) {
            try {
                // Simulate doing other tasks
                System.out.println("performing other task work");
                Thread.sleep(500); // Check every 500 ms
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            if (processTask1.isDone()) {
                String res1 = processTask1.get();
                System.out.println("Res 1: " + res1);
            }

            if (processTask2.isDone()) {
                String res2 = processTask2.get();
                System.out.println("Res 2: " + res2);
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }

        System.out.println("Other Task Completed");

        try {
            System.out.println("performing other task work");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Done with the other task");
        }
    }
    public static void main(String[] args) {
     nonBlockingScenario();


    }
     public  static  String  processTask(String name , long time){
         System.out.println("Started Task = " + name);
         try {
             Thread.sleep(time);

         } catch (InterruptedException e) {
             throw new RuntimeException(e);
         }
         System.out.println();
         return "Completed Task " + name;
     }
}
