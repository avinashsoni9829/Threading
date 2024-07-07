package com.example.multithreading.ThreadFactory;

import java.util.concurrent.ThreadFactory;

public class AssignThreadGroup {
    public static void main(String[] args) {
        ThreadGroup appThreadGroup = new ThreadGroup("Application Threads");
        ThreadGroup uiThreadGroup = new ThreadGroup("Ui Threads");

        ThreadFactory appFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(appThreadGroup,r);
            }
        };

        ThreadFactory uiThreadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(uiThreadGroup,r);
            }
        };
    }
}
