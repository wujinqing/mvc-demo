package com.jin.mvc.demo.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author wu.jinqing
 * @date 2020年10月29日
 */
@Aspect
@Component
public class MyAop {
    @Around("execution(public java.lang.String com.jin.mvc.demo.controller.MyController.getUser(org.springframework.ui.Model, int))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable
    {
        System.out.println("Before around.");
        Object obj = pjp.proceed();
        System.out.println("After around.");

        return obj;
    }

    @Around("execution(public com.jin.mvc.demo.User com.jin.mvc.demo.controller.MyController2.getUser(com.jin.mvc.demo.User))")
    public Object around2(ProceedingJoinPoint pjp) throws Throwable
    {
        System.out.println("Before around.");
        Object obj = pjp.proceed();
        System.out.println("After around.");

        return obj;
    }
}
