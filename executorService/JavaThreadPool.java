package com.example.multithreading.executorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class JavaThreadPool {
   private BlockingQueue<Object> q = null;
   private List<PoolThreadRunnable> tasks = new ArrayList<>();

   private boolean isStopped = false;
   public JavaThreadPool(int noOfThreads , int maxNoOfTasks){
        q  = new ArrayBlockingQueue<>(maxNoOfTasks);
        for(int i = 0 ; i < noOfThreads ; ++i){
             PoolThreadRunnable runnable = new PoolThreadRunnable(q);
             tasks.add(runnable);
        }

        for(PoolThreadRunnable r : tasks){
             new Thread(r).start();
        }

   }

   public synchronized void execute(Runnable task){
        if(this.isStopped) throw new IllegalArgumentException("thread pool is stopped");
        this.q.offer(task);
   }

   public synchronized void stop(){
        this.isStopped = true;
        for(PoolThreadRunnable r : tasks){
             r.doStop();
        }
   }
   public synchronized void waitUntilAllTasksFinished(){
        while (!this.q.isEmpty()){
             try {
                 Thread.sleep(1);
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
        }
   }

    public static void main(String[] args) {
        JavaThreadPool pool = new JavaThreadPool(3,10);
        for(int i = 0 ; i < 10 ; ++i){
             int taskNo = i;
             pool.execute(() -> {
                 String msg = Thread.currentThread().getName() + "is running" + " task = " + taskNo;
                 System.out.println(msg);
             });
        }
        pool.waitUntilAllTasksFinished();
        pool.stop();
    }
}
