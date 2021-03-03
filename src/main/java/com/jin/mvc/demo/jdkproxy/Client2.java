package com.jin.mvc.demo.jdkproxy;

import java.lang.reflect.Proxy;

/**
 * @author wu.jinqing
 * @date 2021年02月03日
 */
public class Client2 {
    public static void main(String[] args) {
        MyInvocationHandler2 invocationHandler2 = new MyInvocationHandler2();

        MyInterface myInterface = (MyInterface)Proxy.newProxyInstance(MyInvocationHandler2.class.getClassLoader(), invocationHandler2.getClass().getInterfaces(), invocationHandler2);

        System.out.println("myInterface" + myInterface.hashCode());
        myInterface.dosomething();

    }
}
