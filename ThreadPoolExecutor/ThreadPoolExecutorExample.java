package com.example.multithreading.ThreadPoolExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorExample {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,
                9,50,
                TimeUnit.MICROSECONDS,new LinkedBlockingQueue<>(2)
        , runnable -> new Thread(runnable,"avinash"));

        Runnable task1 = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " - Task 1");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable task2 = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " - Task 2");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable task3 = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " - Task 3");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable task4 = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " - Task 4");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };


        Runnable task5 = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " - Task 5");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        Runnable task6 = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " - Task 6");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        Runnable task7 = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " - Task 7");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        Runnable task8 = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " - Task 8");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        Runnable task9 = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " - Task 9");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        Runnable task10 = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " - Task 10");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };


        executor.execute(task1);
        executor.execute(task2);
        executor.execute(task3);
        executor.execute(task4);
        executor.execute(task5);
        executor.execute(task6);
        executor.execute(task7);
        executor.execute(task8);
        executor.execute(task9);
        executor.execute(task10);
        executor.execute(task6);
        executor.execute(task7);
        executor.execute(task8);
        executor.execute(task9);
        executor.execute(task10);
        // Shutdown the executor after the tasks are completed
        executor.shutdown();
    }
}
