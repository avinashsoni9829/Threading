package org.example.thread.sleep;

public class SleepExample {

    public static void main(String[] args) {
        for(int i = 0 ; i < 10 ; ++i)
        {
            System.out.println(" printing  iteration " + i);

            try {
                System.out.println("Thread Sleeping ");
                Thread.sleep(1999);
                System.out.println("Thread Awaken after 2 seconds ");
            }
            catch (InterruptedException e)
            {
                 e.printStackTrace();
            }
        }
    }
}
