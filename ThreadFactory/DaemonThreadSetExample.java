package com.example.multithreading.ThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class DaemonThreadSetExample {

    public static void main(String[] args) {
        // Daemon thread Factory
        ThreadFactory daemonThreadFactory = new ThreadFactory() {
            private   int counter = 0;
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                thread.setName("Background-" + counter++);

                return  thread;
            }
        };







    }
}
