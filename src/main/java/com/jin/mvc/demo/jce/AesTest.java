package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES_KEYSIZES = new int[]{16, 24, 32}
 *
 * AES加密的key字节数组的长度必须是16,24或者32位
 * 对应128, 192, and 256 bits.
 * @author wu.jinqing
 * @date 2020年11月17日
 */
public class AesTest {
    public static void main(String[] args) throws Exception {
        // AES加密的key字节数组的长度必须是16,24或者32位
        String key = "abcdeabcdeabcdeabcdeabcdeabcde13";
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
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
