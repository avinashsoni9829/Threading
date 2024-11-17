package com.example.multithreading.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component

public class AopAspect {


    @Before("execution(* com.example.multithreading.aop.AopService.*(..))")
    public void beforeLog(JoinPoint joinPoint)
    {
        System.out.println("doing a log before the method call");
    }

    @After("execution(* com.example.multithreading.aop.AopService.*(..))")
    public void afterLog(JoinPoint joinPoint)
    {
        System.out.println("doing a log after the method call");
    }

    @AfterReturning(pointcut ="execution(* com.example.multithreading.aop.AopService.*(..))",returning = "result")
    public void addReturnLog(JoinPoint joinPoint,Object result)
    {
        System.out.println("after returning log" + joinPoint.getSignature().getName());
        System.out.println("retured value" + result);
    }

    @Around("execution(* com.example.multithreading.aop.AopService.*(..))")
    public Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;


    }

    @AfterThrowing(pointcut = "execution(* com.example.multithreading.aop.AopService.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        System.out.println("Exception in method: " + joinPoint.getSignature().getName());
        System.out.println("Exception: " + exception.getMessage());
    }
}
