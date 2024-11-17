package com.example.multithreading.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AopRunner implements CommandLineRunner {
    @Autowired
    private AopService service;
    public static void main(String[] args) {
        SpringApplication.run(AopRunner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        service.startTransaction();
        Thread.sleep(2000);
        service.endTransaction();
        Thread.sleep(3000);
        service.endTransactionException();
    }
}
