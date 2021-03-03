package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Hex;

import java.security.*;

/**
 * @author wu.jinqing
 * @date 2020年11月17日
 */
public class KeyPairGeneratorTest {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

        secureRandom.setSeed("hello".getBytes("UTF-8"));

        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        System.out.println(publicKey.getEncoded().length);
        System.out.println(privateKey.getEncoded().length);
        System.out.println(Hex.encodeHexString(publicKey.getEncoded()));
        System.out.println(Hex.encodeHexString(privateKey.getEncoded()));

    }
}
