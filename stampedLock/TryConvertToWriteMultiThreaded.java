package com.example.multithreading.stampedLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

public class TryConvertToWriteMultiThreaded {
    private final StampedLock lock = new StampedLock();
    int availableBurgers = 4;
    public int checkForAvailability(){
        long readStamp = lock.readLock();
        System.out.println("read lock acquired for checking availability");
        try{
            return availableBurgers;
        }
        finally {
            lock.unlockRead(readStamp);
            System.out.println("read lock acquired for checking availability released");
        }
    }


    public void book(int qty,String userName){
        long stamp = lock.readLock();
        System.out.println("[" + userName + "]" + "read lock acquired for booking");
        try{
            if((availableBurgers - qty) > 0){
                long writeStamp = lock.tryConvertToWriteLock(stamp);
                System.out.println("[" + userName + "]" +"conversion of lock tried ");
                if(writeStamp!=0){
                    // this means the lock is now converted
                    System.out.println("[" + userName + "]" +"lock conversion successfull");
                    stamp = writeStamp;

                    availableBurgers-=qty;
                    System.out.println("[" + userName + "]" +"Burgers Booked");
                }
                else {
                    // not able to convert
                    // so now we will try to do it by acquiring the write lock

                    lock.unlockRead(stamp);
                    long wStamp = lock.writeLock();
                    try{
                        System.out.println("[" + userName + "]" +"write lock acquired exclusively ");
                        if((availableBurgers - qty) > 0){
                            availableBurgers-=qty;
                            stamp = wStamp;
                            System.out.println("[" + userName + "]" +"burgers Booked");
                        }
                        else{
                            System.out.println("[" + userName + "]" +"booking failed !!");
                        }
                    }finally {
                        lock.unlockWrite(stamp);
                    }

                }
            }
            else{
                System.out.println("[" + userName + "]" +"Burger Out of Stock!!");
            }
        }finally {
            lock.unlock(stamp);
            System.out.println("[" + userName + "]" +"lock released finally");
        }
    }

    public static void main(String[] args) {
        TryConvertToWriteMultiThreaded mExample = new TryConvertToWriteMultiThreaded();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for(int i = 1 ; i <= 3 ; ++i){
            int finalI = i;
            executorService.submit(() -> mExample.book(2,"User" + finalI));
        }
        executorService.shutdown();
    }

}
