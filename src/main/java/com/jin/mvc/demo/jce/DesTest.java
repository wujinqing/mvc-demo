package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author wu.jinqing
 * @date 2020年11月17日
 */
public class DesTest {
    public static void main(String[] args) throws Exception{
        String key = "12345678";// DES的秘钥长度必须是8位
        Cipher cipher = Cipher.getInstance("DES");

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);// 加密

        cipher.update("hello".getBytes("UTF-8"));

        byte[] bytes = cipher.doFinal();

        System.out.println(bytes.length);
        System.out.println(Hex.encodeHexString(bytes));

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);// 解密
        cipher.update(bytes);
        bytes = cipher.doFinal();
        System.out.println(new String(bytes, "UTF-8"));
    }
}
