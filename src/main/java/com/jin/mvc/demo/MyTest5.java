package com.jin.mvc.demo;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author wu.jinqing
 * @date 2020年11月12日
 */
public class MyTest5 {
    public static void main(String[] args) throws Exception {
        String charsetName = "UTF-8";
        String key = "7bfcffa0a5d262396e0bf5e3fc905df8e4101f2763f3d38aeef6b5d095b25285";

        SecretKeySpec secretKeySpec = new SecretKeySpec(hexStringToByte(key), "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        byte[] cipherByte = cipher.doFinal("zhangsan".getBytes(charsetName));

        System.out.println(Hex.encodeHexString(cipherByte));

    }

    public static byte[] hexStringToByte(String hex) {

        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toUpperCase().toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
