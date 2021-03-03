package com.jin.mvc.demo.jce;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * @author wu.jinqing
 * @date 2020年11月16日
 */
public class MyTest8 {
    public static void main(String[] args) throws Exception {
        byte[] desEdeKeyData = "hellohellohellohellohell".getBytes();
        DESedeKeySpec desEdeKeySpec = new DESedeKeySpec(desEdeKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = keyFactory.generateSecret(desEdeKeySpec);


    }
}
