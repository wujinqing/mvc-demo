package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author wu.jinqing
 * @date 2020年11月17日
 */
public class Md5Test {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        byte[] bytes = messageDigest.digest("hello".getBytes("UTF-8"));

        System.out.println(messageDigest.getProvider());
        System.out.println(Hex.encodeHexString(bytes));

        MessageDigest messageDigest1 = MessageDigest.getInstance("MD2");

        byte[] bytes1 = messageDigest1.digest("hello".getBytes("UTF-8"));

        System.out.println(Hex.encodeHexString(bytes1));
    }
}
