package org.example.thread.yield;

public class YieldMethod extends  Thread{
    public void run()
    {
         for(int i = 0 ;  i < 5 ; ++i)
         {
             System.out.println( Thread.currentThread().getName() +  " : Yield Method Check" + i);
             Thread.yield();
         }
    }

    public static void main(String[] args) {
        YieldMethod th1 = new YieldMethod();
        YieldMethod th2 = new YieldMethod();
        th1.setName("thread1 ");
        th2.setName("thread2");
        th1.start();
        th2.start();
    }
}
