package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Triple DES Encryption (also known as DES-EDE, 3DES, or Triple-DES)
 *
 * @author wu.jinqing
 * @date 2020年11月17日
 */
public class DesedeTest {
    public static void main(String[] args) throws Exception{
        String key = "123456781234567812345678";// DESede的秘钥长度必须是24位
        Cipher cipher = Cipher.getInstance("DESede");

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "DESede");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);// 加密

        cipher.update("hello".getBytes("UTF-8"));

        byte[] bytes = cipher.doFinal();

        System.out.println(Hex.encodeHexString(bytes));

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);// 解密
        cipher.update(bytes);
        bytes = cipher.doFinal();
        System.out.println(new String(bytes, "UTF-8"));
    }
}
