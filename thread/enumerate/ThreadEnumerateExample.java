package org.example.thread.enumerate;

public class ThreadEnumerateExample {
    public static void main(String[] args) {

        Thread main = Thread.currentThread();

        Thread myThread = new Thread( () -> {
            for(int  i = 0 ; i < 5 ; ++i)
            {
                System.out.println("MyThread " + i);
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e)
                {
                     e.printStackTrace();
                }
            }
        });

        myThread.start();


        Thread myThread2 = new Thread(() -> {
             for(int  i = 0 ; i < 5 ; ++i)
             {
                 System.out.println("Thread 2 " + i);
                 try {
                     Thread.sleep(700);
                 }
                 catch (InterruptedException e)
                 {
                      e.printStackTrace();
                 }
             }
        });

        myThread2.start();

        Thread []  allThreads = new Thread[Thread.activeCount()];

        Thread.enumerate(allThreads);

        System.out.println("Active Threads ");

        for(Thread t : allThreads)
        {
             if(t!=null) System.out.println(t.getName());
        }
    }
}
