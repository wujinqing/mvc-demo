package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

/**
 * @author wu.jinqing
 * @date 2020年11月13日
 */
public class MyTest3 {
    public static void main(String[] args) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        sha.update("hello".getBytes());
        sha.update("world".getBytes());
//        sha.update("nihao".getBytes());
//        byte[] hash = sha.digest();
        byte[] hash = sha.digest("nihao".getBytes());// 两种方式等价
        System.out.println(hash.length);
        System.out.println(Hex.encodeHexString(hash));

    }
}
