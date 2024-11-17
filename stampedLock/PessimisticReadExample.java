package com.example.multithreading.stampedLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PessimisticReadExample {
    private  int data = 0;
    private  Lock lock = new ReentrantLock();

    public void writeData(int newValue) throws InterruptedException {
         lock.lock();
        System.out.println("Writer Thread Lock Acquired");
         try{
              data = newValue;
             System.out.println("Writer Thread Operation Completed");
             Thread.sleep(2000);
         }finally {
             lock.unlock();
             System.out.println("Writer Thread Released Lock");
         }


    }

    public void readData(){
         lock.lock();
        System.out.println("Reader thread lock acquired");
         try {
             System.out.println("read value = "+ data);
             System.out.println("Reader thread Operation completed");

         }finally {
             lock.unlock();
             System.out.println("Reader Thread Released Lock");
         }
    }


        public static void main(String[] args) {
            PessimisticReadExample example = new PessimisticReadExample();
            // Creating the writer thread

            Thread writerThread = new Thread(() -> {
                try {
                    example.writeData(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            Thread readerThread = new Thread(example::readData);

            writerThread.start(); // writer thread has delay of 2s

            try {
                Thread.sleep(1000); // do a delay of 1s and see if the reader thread is able to read the value
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            readerThread.start();

        }
    }

