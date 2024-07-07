package com.example.multithreading.ThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class NamingThreadExample {
    public static void main(String[] args) {
        // App thread Factory
        ThreadFactory appThreadFactory = new ThreadFactory() {
            private   int counter = 0;
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Application-" + counter++);

                return  thread;
            }
        };

        // User thread Factory
        ThreadFactory userThreadFactory = new ThreadFactory() {
            private int counter = 0;
            @Override
            public Thread newThread(Runnable r) {

                Thread thread = new Thread(r);
                thread.setName("User -" + counter++);
                return thread;
            }
        };

        ExecutorService appService = Executors.newFixedThreadPool(5,appThreadFactory);

        ExecutorService userService = Executors.newFixedThreadPool(5,userThreadFactory);

        // Doing APP level task
        for(int i = 0 ; i < 10 ; ++i){
            appService.submit(() -> {
                String threadname = Thread.currentThread().getName();
                System.out.println("Current Running Thread [ APP ] -> " + threadname);
            });

        }

        appService.shutdown();
        // Doing User Level Task
        for(int i = 0 ; i < 10 ; ++i){
            userService.submit(() -> {
                String threadname = Thread.currentThread().getName();
                System.out.println("Current Running Thread [ USER ] -> " + threadname);
            });

        }

        userService.shutdown();

    }
}