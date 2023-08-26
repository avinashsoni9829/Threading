package org.example.thread.Interrupt;

public class IsInterruptedExample {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    // Perform some work
                    System.out.println("Working...");
                    Thread.sleep(1000); // Sleep for 1 second
                }
                System.out.println(Thread.interrupted());
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted!ss");
                System.out.println(Thread.currentThread().isInterrupted());
            }
        });

        thread.start();

        try {
            Thread.sleep(5000); // Sleep for 5 seconds
            thread.interrupt(); // Interrupt the thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
