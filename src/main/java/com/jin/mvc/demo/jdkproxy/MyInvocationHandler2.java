package com.jin.mvc.demo.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author wu.jinqing
 * @date 2021年02月03日
 */
public class MyInvocationHandler2 implements InvocationHandler, MyInterface {
    @Override
    public String dosomething() {
        System.out.println("do some thing2");
        return "do some thing2.";
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object res = method.invoke(this, args);
        System.out.println("after");
        return res;
    }
}
