package com.jin.mvc.demo.jce;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author wu.jinqing
 * @date 2020年11月16日
 */
public class MyTest9 {
    public static void main(String[] args) throws Exception {
        byte[] aesKeyData = "hello".getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(aesKeyData, "AES");

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("AES");
        SecretKey secretKey = keyFactory.generateSecret(secretKeySpec);
    }
}
