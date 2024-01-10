package com.example.demo.learning;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLocksTest {
    private static final ReentrantLock lock = new ReentrantLock(true);  // fairness parameter = false
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
              try {
                  System.out.println(Thread.currentThread().getName() + " Started ");
                  lock.lock(); // acquire the lock
                  System.out.println(Thread.currentThread().getName() + " acquired the lock ");
                  System.out.println(Thread.currentThread().getName() + " started waiting ");
                  Thread.sleep(2000);
                  System.out.println(Thread.currentThread().getName() + " completed waiting ");
              } catch (InterruptedException e) {
                  throw new RuntimeException(e);
              } finally {
                  lock.unlock();
                  System.out.println(Thread.currentThread().getName() + " released the lock");
              }
         };

            for(int i = 0 ; i < 5; ++i){
                new Thread(task).start();
                Thread.sleep(500);
            }




    }
}
