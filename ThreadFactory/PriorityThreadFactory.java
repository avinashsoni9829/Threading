package com.example.multithreading.ThreadFactory;

import java.util.concurrent.ThreadFactory;

public class PriorityThreadFactory {
    public static void main(String[] args) {
        ThreadFactory highPriorityThreads = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                 thread.setPriority(Thread.MAX_PRIORITY);
                 return thread;
            }
        };

        ThreadFactory normPriorityThreads = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setPriority(Thread.NORM_PRIORITY);
                return thread;
            }
        };


        ThreadFactory lowPriorityThreads = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setPriority(Thread.MIN_PRIORITY);
                return thread;
            }
        };

    }
}
