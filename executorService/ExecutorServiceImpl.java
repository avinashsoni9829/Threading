package com.example.multithreading.executorService;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceImpl {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // ## Submit Method in Executor Service Returns a Future<?> object

        ExecutorService service = Executors.newFixedThreadPool(2);
//        // submitting runnable tasks to the executor service
//        service.submit(() -> {
//            System.out.println(" task  - 1 performed by  = " + Thread.currentThread().getName());
//        });
//
//        // submitting runnable tasks to the executor service
//        service.submit(() -> {
//             System.out.println(" task - 2 performed by = " + Thread.currentThread().getName());
//        });




        // ## Submit Callable Object
//
//        Future<Integer> future = service.submit(() -> {
//            System.out.println("callable task executed by " + Thread.currentThread().getName());
//            return 10;
//        });
//
//       Integer result = future.get();
//
//        System.out.println("result = "  + result);
//      //  service.shutdown();
//
//       // ## Invoke ALL
        List<Callable<String>> tasks = Arrays.asList(

                () -> { Thread.sleep(200); return "Task 2"; },
                () -> { Thread.sleep(300); return "Task 3"; },
                () -> { Thread.sleep(300); return "Task 4"; },
                () -> { Thread.sleep(100); return "Task 1"; },
                () -> { Thread.sleep(300); return "Task 5"; },
                () -> { Thread.sleep(300); return "Task 6"; }
        );

        String  resultAny = service.invokeAny(tasks);
        System.out.println("result any = " + resultAny);

//     // ## invoke any

//        List<Future<String>> results = service.invokeAll(tasks);
//        for (Future<String> res : results) {
//            System.out.println("Result: " + res.get());
//        }
       // previous tasks will be executed but no new tasks will be taken for execution
//
//        service.shutdown();
//        // returns the list of current tasks left and stops all the current ongoing tasks
//        service.shutdownNow();
//
//        // is ShutDown  () || is Terminated ()
//        // awaitTermination ()

    }


}
