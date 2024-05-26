package com.example.multithreading.stampedLock;

import java.util.concurrent.atomic.AtomicInteger;

public class OptimisticReadExample {
    private AtomicInteger data = new AtomicInteger(0);


    public void writeData(int newValue) throws InterruptedException {
        data.set(newValue);
        System.out.println("Writer Thread Wrote Some Data ");

        try {
            Thread.sleep(2000);
        } finally {

            System.out.println("Writer Thread Completed Operation");
        }


    }

    public void readData() {
        System.out.println("read value = " + data);

    }

    public static void main(String[] args) {
        OptimisticReadExample example = new OptimisticReadExample();
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


