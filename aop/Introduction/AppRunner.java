package com.example.multithreading.aop.Introduction;


import com.example.multithreading.aop.AopRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;


@SpringBootApplication
@EnableAspectJAutoProxy
public class AppRunner implements CommandLineRunner {

    @Autowired
    private PaymentService service;


    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (service instanceof PaymentLogging logging)
        {
            logging.logMessage("Hello World");
        }
    }
}
