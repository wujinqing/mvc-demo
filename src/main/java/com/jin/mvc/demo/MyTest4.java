package com.jin.mvc.demo;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;

/**
 * @author wu.jinqing
 * @date 2020年11月12日
 */
public class MyTest4 {
    public static void main(String[] args) throws Exception{
        String myinfo = "qwdsdsdfsf";
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        //如果设定随机产生器就用如相代码初始化
        SecureRandom secrand=new SecureRandom();
        secrand.setSeed("qcs".getBytes()); // 初始化随机产生器
        keygen.init(256, secrand);
        SecretKey deskey = keygen.generateKey();

        Cipher c1 = Cipher.getInstance("AES/ECB/PKCS5Padding");

        c1.init(Cipher.ENCRYPT_MODE,deskey);

        byte[] cipherByte=c1.doFinal(myinfo.getBytes());

        String s = Hex.encodeHexString(cipherByte);

        System.out.println(s);

    }
}
