package com.example.multithreading.aop.Introduction;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PaymentIntroductionAspect {


    // + is there to include all the subclasses
    @DeclareParents(value = "com.example.multithreading.aop.Introduction.PaymentService+",defaultImpl = DefaultPaymentLogging.class )
    public static  PaymentLogging mixin;
    public static class DefaultPaymentLogging implements PaymentLogging{

        @Override
        public void logMessage(String message) {
            System.out.println("logging -> " + message);
        }
    }
}
