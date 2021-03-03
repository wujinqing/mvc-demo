package com.jin.mvc.demo;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * @author wu.jinqing
 * @date 2020年11月10日
 */
public class MyTest2 {
    public static void main(String[] args) throws Exception {
        // 生成key
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(256);  // 确定密钥长度
        byte[] keyBytes = kg.generateKey().getEncoded();
// 格式化key
        Key key = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // 确定算法
        cipher.init(Cipher.ENCRYPT_MODE, key);    // 确定密钥
        byte[] result = cipher.doFinal("原文".getBytes());  // 加密
        System.out.println(Base64.encodeBase64String(result));  // 不进行Base64编码的话，那么这个字节数组对应的字符串就是乱码

        cipher.init(Cipher.DECRYPT_MODE, key); // 进入解密模式
        System.out.println(new String(cipher.doFinal(result))); // 解密
    }
}
