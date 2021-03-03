package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

/**
 * @author wu.jinqing
 * @date 2020年11月17日
 */
public class Sha1Test {
    public static void main(String[] args) throws Exception{
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

        byte[] bytes = messageDigest.digest("hello".getBytes("UTF-8"));

        System.out.println(messageDigest.getProvider());
        System.out.println(Hex.encodeHexString(bytes));

        System.out.println("===================");

        MessageDigest messageDigest1 = MessageDigest.getInstance("SHA-224");

        byte[] bytes1 = messageDigest1.digest("hello".getBytes("UTF-8"));

        System.out.println(Hex.encodeHexString(bytes1));

        System.out.println("===================");

        MessageDigest messageDigest2 = MessageDigest.getInstance("SHA-256");

        byte[] bytes2 = messageDigest2.digest("hello".getBytes("UTF-8"));

        System.out.println(Hex.encodeHexString(bytes2));

        System.out.println("===================");

        MessageDigest messageDigest3 = MessageDigest.getInstance("SHA-384");

        byte[] bytes3 = messageDigest3.digest("hello".getBytes("UTF-8"));

        System.out.println(Hex.encodeHexString(bytes3));

        System.out.println("===================");

        MessageDigest messageDigest4 = MessageDigest.getInstance("SHA-512");

        byte[] bytes4 = messageDigest4.digest("hello".getBytes("UTF-8"));

        System.out.println(Hex.encodeHexString(bytes4));

        System.out.println("===================");

        MessageDigest messageDigest5 = MessageDigest.getInstance("SHA-512/256");

        byte[] bytes5 = messageDigest5.digest("hello".getBytes("UTF-8"));

        System.out.println(Hex.encodeHexString(bytes5));

    }
}
