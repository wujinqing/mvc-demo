package com.jin.mvc.demo.jdkproxy;

import java.lang.reflect.Proxy;

/**
 * @author wu.jinqing
 * @date 2021年02月03日
 */
public class Client {
    public static void main(String[] args) {
        MyInterface m = () -> {
            System.out.println("do some thing1.");
        return "do some thing.";
        };

        MyInvocationHandler invocationHandler = new MyInvocationHandler(m);

        MyInterface myInterface = (MyInterface)Proxy.newProxyInstance(invocationHandler.getClass().getClassLoader(), new Class[]{MyInterface.class}, invocationHandler);

        myInterface.dosomething();
    }
}
