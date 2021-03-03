package com.jin.mvc.demo.jce;

import java.security.Provider;
import java.security.Security;

/**
 * @author wu.jinqing
 * @date 2020年11月13日
 */
public class MyTest1 {
    public static void main(String[] args) {
        for (Provider provider : Security.getProviders()) {
//            System.out.println(provider.getName());
            provider.getServices().forEach(s -> {
                System.out.println(s.getAlgorithm() + "=" + s.getClassName() + "=" + s.getProvider());
            });
        }
    }
}
