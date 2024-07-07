package com.example.multithreading.ThreadFactory;

import java.util.concurrent.ThreadFactory;

public class HandlingUncaughtException {
    public static void main(String[] args) {
        Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Thread With Exception = " + t.getName());
                 e.printStackTrace();
                 // May be we can send Email To dev team
                 // Log the Error

            }
        };

        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setUncaughtExceptionHandler(handler);
                return thread;
            }
        };
    }
}
