package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Hex;

import java.security.SecureRandom;

/**
 * @author wu.jinqing
 * @date 2020年11月13日
 */
public class MyTest2 {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();

        random.setSeed("hello".getBytes());

        byte[] bytes = random.generateSeed(32);

        System.out.println(Hex.encodeHexString(bytes));
    }
}
