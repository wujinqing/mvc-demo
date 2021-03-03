package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.security.*;

/**
 * @author wu.jinqing
 * @date 2020年11月17日
 */
public class SignatureTest {
    public static void main(String[] args) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(1024);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        System.out.println("publicKey:" + Base64.encodeBase64String(publicKey.getEncoded()));
        System.out.println("privateKey:" + Base64.encodeBase64String(privateKey.getEncoded()));

        signature.initSign(privateKey);
        signature.update("hello".getBytes("UTF-8"));
        byte[] bytes = signature.sign();

        System.out.println("sign:" + Base64.encodeBase64String(bytes));

        signature.initVerify(publicKey);
        signature.update("hello".getBytes("UTF-8"));
        boolean b = signature.verify(bytes);
        System.out.println(b);
    }
}
