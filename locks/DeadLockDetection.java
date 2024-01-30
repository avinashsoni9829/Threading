package org.example.semaphores;

import java.util.concurrent.locks.ReentrantLock;

public class DeadLockTest {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock1=  new ReentrantLock(true);
        ReentrantLock lock2 = new ReentrantLock(true);

        Thread first = createThread("first",lock1,lock2);
        Thread second = createThread("second",lock2,lock1);

        first.start();
        second.start();
        first.join();
        second.join();
    }

    private static Thread createThread(String threadName, ReentrantLock lock1, ReentrantLock lock2) {
        return  new Thread(() -> {
            System.out.println("thread Being Created = " + threadName );
            lock1.lock();
            System.out.println( threadName + " lock is Acquired on top ");
            synchronized (DeadLockTest.class){
                System.out.println( threadName + " entered the critical Section  ");
                DeadLockTest.class.notify();

                if(!lock2.isLocked()){

                    try {
                        DeadLockTest.class.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }

                lock2.lock();
                System.out.println( threadName + " second lock is acquired  in critical section ");
            }

            System.out.println(threadName + "completed");
            lock1.unlock();
            lock2.unlock();
        });
    }
}
