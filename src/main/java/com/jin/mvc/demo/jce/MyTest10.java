package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

/**
 * @author wu.jinqing
 * @date 2020年11月16日
 */
public class MyTest10 {
    public static void main(String[] args) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        messageDigest.update("1sd@#$#$cvsdsdfASDAFERDF".getBytes("UTF-8"));
        byte[] bytes = messageDigest.digest();

        System.out.println(Hex.encodeHexString(bytes));
    }
}
