package com.example.demo.learning;

import java.util.concurrent.locks.ReentrantLock;

public class DeadLockDetection {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock firstLock = new ReentrantLock(true);
        ReentrantLock secondLock = new ReentrantLock(true);

        Thread firstThread = createThread("thread - 1" , firstLock , secondLock);
        Thread secondThread = createThread("thread - 2", secondLock,firstLock);

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();
    }

    public static Thread createThread (String name , ReentrantLock primaryLock,ReentrantLock secondaryLock)
    {
         return new Thread(() -> {
              primaryLock.lock(); // take the primary lock

             // enter  the  critical section
              synchronized (DeadLockDetection.class){
                  // notify the threads
                   DeadLockDetection.class.notify();

                  // check if the secondary lock is not allowed to take a lock
                  // or not
                  if(!secondaryLock.isLocked()){


                      // if the lock is not acquired then wait
                        try{
                             DeadLockDetection.class.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        // now take the lock
                        secondaryLock.lock();

                        System.out.println(name + "created");
                        // release the locks
                        primaryLock.unlock();
                        secondaryLock.unlock();
                   }
              }
         });



    }
}
