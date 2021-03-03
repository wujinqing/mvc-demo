package com.jin.mvc.demo.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author wu.jinqing
 * @date 2021年02月03日
 */
public class MyInvocationHandler implements InvocationHandler {
    private MyInterface myInterface;

    public MyInvocationHandler(MyInterface myInterface) {
        this.myInterface = myInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object res = method.invoke(myInterface, args);

        System.out.println("after");
        return res;
    }
}
