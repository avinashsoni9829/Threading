package org.example.thread.Interrupt;

public class InterruptedMethodExample {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Working... (Interrupted status: " + Thread.currentThread().isInterrupted() + ")");
            }
            System.out.println("Thread interrupted. (Interrupted status: " + Thread.currentThread().isInterrupted() + ")");
        });

        thread.start();

        try {
            Thread.sleep(1000);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
