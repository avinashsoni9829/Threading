package org.example.thread.yield;

public class PriorityYieldExample  extends  Thread{
     private int priority;

     public PriorityYieldExample(String name, int priority) {
          super(name);
          this.priority = priority;
     }

     public void run() {
          Thread.currentThread().setPriority(priority);
          for (int i = 1; i <= 5; i++) {
               System.out.println(Thread.currentThread().getName() + " : " + i);
               Thread.yield(); // Yield the processor
          }
     }


     public static void main(String[] args) {
          PriorityYieldExample highPriority = new PriorityYieldExample("High Priority", Thread.MAX_PRIORITY);
          PriorityYieldExample lowPriority = new PriorityYieldExample("Low Priority", Thread.MIN_PRIORITY);
          highPriority.setName("Thread1");
          lowPriority.setName("Thread2");
          highPriority.start();
          lowPriority.start();
     }
}


