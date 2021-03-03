package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Hex;

import java.security.*;

/**
 * @author wu.jinqing
 * @date 2020年11月13日
 */
public class MyTest6 {
    public static void main(String[] args) throws Exception {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");

//        keyGen.initialize(2048);

        SecureRandom random = new SecureRandom();
        random.setSeed("hello".getBytes());
        keyGen.initialize(2048, random);

        KeyPair pair = keyGen.generateKeyPair();

        Signature dsa = Signature.getInstance("SHA256withDSA");

        PrivateKey priv = pair.getPrivate();
        System.out.println(Hex.encodeHexString(priv.getEncoded()));
        dsa.initSign(priv);

        dsa.update("helloworld".getBytes());
        byte[] sig = dsa.sign();

        System.out.println("sig:" + Hex.encodeHexString(sig));

        PublicKey pub = pair.getPublic();

        System.out.println(Hex.encodeHexString(pub.getEncoded()));
        dsa.initVerify(pub);

        dsa.update("helloworld".getBytes());
        boolean verifies = dsa.verify(sig);
        System.out.println("signature verifies: " + verifies);
    }
}
