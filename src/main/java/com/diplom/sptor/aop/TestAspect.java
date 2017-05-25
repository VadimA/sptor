package com.diplom.sptor.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * Created by user on 28.10.2016.
 */
@Aspect
public class TestAspect {

    private Logger logger = Logger.getLogger(TestAspect.class);
    @Pointcut("execution(* com.diplom.sptor.web.ServiceController.getStarted(..))")
    public void performance(){}

    @Before("performance()")
    public void printBefore(){
        System.out.println("Print before direct on /sptor");
    }

    @AfterReturning("execution(* com.diplom.sptor.web.ServiceController.getStarted(..))")
    public void printAfter(){
        System.out.println("Print after direct on /sptor");
    }

    @Around("performance()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Enter: {}.{}() with argument[s] = {}" + joinPoint.getSignature().getDeclaringTypeName()+
                    joinPoint.getSignature().getName()+ Arrays.toString(joinPoint.getArgs()));
        try {
            Object result = joinPoint.proceed();
                System.out.println("Exit: {}.{}() with result = {}" + joinPoint.getSignature().getDeclaringTypeName() +
                        joinPoint.getSignature().getName() + result);
            return result;
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: {} in {}.{}()" + Arrays.toString(joinPoint.getArgs()) +
                    joinPoint.getSignature().getDeclaringTypeName() + joinPoint.getSignature().getName());

            throw e;
        }
    }
}
