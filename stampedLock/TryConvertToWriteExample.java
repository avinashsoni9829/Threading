package com.example.multithreading.stampedLock;

import java.util.concurrent.locks.StampedLock;

public class TryConvertToWriteExample {

    /*
       Consider an App Like Swiggy where the user is trying to go through the menu and wants to have the read access
       then it makes up it mind and then wants to go and make a purchase ( write).
       so here using the stamped lock we can try to convert the same read lock to convert into the write lock considering the condition that
       if the stamped lock is read lock and the write lock is available then we release the read lock and return the write stamp
       otherwise if the stamped lock is a optimistic read then we return the write stamp only if immediately available
     */

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


    public void book(int qty){
         long stamp = lock.readLock();
        System.out.println("read lock acquired for booking");
         try{
              if((availableBurgers - qty) > 0){
                   long writeStamp = lock.tryConvertToWriteLock(stamp);
                  System.out.println("conversion of lock tried ");
                   if(writeStamp!=0){
                       // this means the lock is now converted
                       System.out.println("lock conversion successfull");
                       stamp = writeStamp;

                       availableBurgers-=qty;
                       System.out.println("Burgers Booked");
                   }
                   else {
                        // not able to convert
                       // so now we will try to do it by acquiring the write lock
                       long wStamp = lock.writeLock();
                       System.out.println("write lock acquired exclusively ");
                       if((availableBurgers - qty) > 0){
                            availableBurgers-=qty;
                            stamp = wStamp;
                           System.out.println("burgers Booked");
                       }
                       else{
                           System.out.println("booking failed !!");
                       }
                   }
              }
              else{
                  System.out.println("Burger Out of Stock!!");
              }
         }finally {
             lock.unlock(stamp);
             System.out.println("lock released finally");
         }
    }

    public static void main(String[] args) {
        TryConvertToWriteExample example = new TryConvertToWriteExample();
        example.checkForAvailability();
        example.book(2);
    }

}
