package org.example.thread.onSpinWait;

public class SpinWaitExample {

    public static void main(String[] args) {
        Thread t1 = new Thread( () -> {
            for(int  i = 0 ; i < 10 ; ++i)
            {
                System.out.println("Thread 1 " + i);
                Thread.onSpinWait();

            }
        });

        Thread t2 = new Thread( () -> {
             for(int i = 0 ; i < 10 ; ++i)
             {
                 System.out.println("Thread 2 "  + i);
                 Thread.onSpinWait();

                 if(i == 3)
                 {
                     try {
                         Thread.sleep(3000);
                     } catch (InterruptedException e) {
                         throw new RuntimeException(e);
                     }
                 }
             }
        });

        t1.start();
        t2.start();
    }
}
