package com.example.multithreading.aop.Introduction;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public void startTxn(String accNum)
    {
        System.out.println("txn started for " + accNum);

    }

    public void endTxn(String accNum)
    {
        System.out.println("txn ended for" + accNum);
    }
}
