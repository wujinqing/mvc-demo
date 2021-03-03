package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.InvalidKeySpecException;

/**
 * @author wu.jinqing
 * @date 2020年11月13日
 */
public class MyTest7 {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        DESKeySpec desKeySpec = new DESKeySpec("hellohellohelloh".getBytes());


        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");

        SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
        Mac mac = Mac.getInstance("HmacSHA1");

        mac.init(secretKey);

        mac.update("zhangsan".getBytes());

        byte[] bytes = mac.doFinal();

        System.out.println(Hex.encodeHexString(bytes));




    }
}
