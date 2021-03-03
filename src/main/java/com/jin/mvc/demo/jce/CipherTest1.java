package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author wu.jinqing
 * @date 2020年11月17日
 */
public class CipherTest1 {
    public static void main(String[] args) throws Exception {
        String key = "12345678123456781234567813";
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        // 设置加盐和迭代次数，盐必须是8位
        PBEParameterSpec pbeParameterSpec=new PBEParameterSpec("must8bit".getBytes("UTF-8"),1000 );


        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "PBEWithMD5AndDES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, pbeParameterSpec);// 加密

        cipher.update("hello".getBytes("UTF-8"));

        byte[] bytes = cipher.doFinal();

        System.out.println(Hex.encodeHexString(bytes));

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, pbeParameterSpec);// 解密
        cipher.update(bytes);
        bytes = cipher.doFinal();
        System.out.println(new String(bytes, "UTF-8"));
    }
}
