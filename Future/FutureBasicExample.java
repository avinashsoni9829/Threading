//package com.example.multithreading.concurrency.Future;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//public class FutureBasicExample {
//    public static void main(String[] args) {
//        ExecutorService service = Executors.newFixedThreadPool(1);
//        Future<String> f = service.submit(task); // task can be Runnable or Callable
//        // Untill this Task is not Completed Do other work
//        if(!f.isDone()){
//             doOtherTask();
//        }
//        else {
//             process(f.get());
//        }
//    }
//}
