package org.example.thread.sleep;

public class InterruptHandler implements  Runnable {


    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " :  " + "Task Started");
            System.out.println(Thread.currentThread().getName() + " :  "+ "Sleeping the thread for 5 seconds");
            Thread.sleep(5000);
            System.out.println("Task Ended");
        }
        catch (InterruptedException e)
        {
             e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new InterruptHandler());
        thread.start();
         thread.setName("MyThread");
        try {
            System.out.println(Thread.currentThread().getName() + " :  " +  "Sleeping the thread for 2 seconds " );
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " :  " + "Calling Interrupt");
            thread.interrupt();
        }
        catch (InterruptedException e)
        {
            System.out.println(Thread.currentThread().getName() + " :  " + "Printing Stack Trace ");
             e.printStackTrace();
        }
    }
}
