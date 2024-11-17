package com.example.multithreading.aop;

import org.springframework.stereotype.Service;

@Service
public class AopService {

    public String startTransaction()
    {
        System.out.println("transaction started");
        return "hello";
    }

    public Integer endTransaction()
    {
        System.out.println("end transaction");
        return -1;
    }

    public Integer endTransactionException()
    {
        System.out.println("end transaction");
       throw new RuntimeException("helloo");
    }
}

