package com.diplom.sptor.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.apache.log4j.Logger;
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
}
