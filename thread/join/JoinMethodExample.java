package org.example.thread.join;

public class JoinMethodExample {

    public static void main(String[] args) {


        Thread thread1 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Thread 1: " + i);
                try {
                    Thread.sleep(1000); // Sleep for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        Thread thread2 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Thread 2: " + i);
                try {
                    Thread.sleep(800); // Sleep for 800 milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        thread1.start();
        thread2.start();


        try {

            thread2.join();
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
