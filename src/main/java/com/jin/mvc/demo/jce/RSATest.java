package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

/**
 * @author wu.jinqing
 * @date 2020年11月17日
 */
public class RSATest {
    public static void main(String[] args) throws Exception{

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(1024);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();


        System.out.println("publicKey:" + Base64.encodeBase64String(publicKey.getEncoded()));
        System.out.println("privateKey:" + Base64.encodeBase64String(privateKey.getEncoded()));


        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);// 加密

        cipher.update("hello".getBytes("UTF-8"));

        byte[] bytes = cipher.doFinal();

        System.out.println("密文:" + Base64.encodeBase64String(bytes));

        cipher.init(Cipher.DECRYPT_MODE, privateKey);// 解密
        cipher.update(bytes);
        bytes = cipher.doFinal();
        System.out.println(new String(bytes, "UTF-8"));
    }
}
