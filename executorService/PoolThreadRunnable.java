package com.example.multithreading.executorService;


import java.util.concurrent.BlockingQueue;

public class PoolThreadRunnable implements Runnable{
    private Thread thread = null;
    private BlockingQueue<Object> queue = null;
    private boolean isStopped = false;

    public PoolThreadRunnable(BlockingQueue<Object> q){
        queue = q;
    }


    @Override
    public void run() {
        this.thread = Thread.currentThread();
        while (!isStopped){
             try {
                 Runnable r = (Runnable)  queue.take();
                 r.run();
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
        }
    }

    public synchronized boolean isStopped(){
         return  isStopped;
    }

    public  synchronized void doStop(){
         isStopped = true;
         this.thread.interrupt();
    }
}
