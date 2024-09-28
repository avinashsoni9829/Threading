package com.example.multithreading.executorService;


import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class BlockedQueueImplementation {
     private  final  Queue<Object> queue = new LinkedList<>();

     private static final int DEFAULT_LIMIT = 10;
     private    int limit = DEFAULT_LIMIT;

     public BlockedQueueImplementation(int limit)
     {
       this.limit = limit;
     }

     public synchronized void enqueue(Object item) throws InterruptedException {
         while (this.queue.size() == this.limit)
         {
           wait();
         }

         // now add the item
         this.queue.add(item);

         // now notify the others covering scenario where some threads which are enqueuing are blocked
         // as queue was empty before
         if(this.queue.size() == 1) notifyAll();
     }


     public synchronized Object dequeue() throws InterruptedException {
         while (this.queue.isEmpty()){
           wait();
         }
         // notify all blocked threads which are blocked to dequeue from the queue as queue was FULL earlier
         if(queue.size() == this.limit) notifyAll();
         return this.queue.remove(0);
     }

 }
