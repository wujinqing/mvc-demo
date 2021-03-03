package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Base64;
import sun.security.rsa.RSAPublicKeyImpl;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author wu.jinqing
 * @date 2020年11月17日
 */
public class RSATest3 {
    // 加密
    public static void main(String[] args) throws Exception{
        // 公钥
        String pbk = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6tUg/nAFyFGFtOp6dV112RpHLmQVq9AWlUsH4GcEgRlK14M1LA4l4driy+j3u46416Zq6mkGmMnvtA0HkM7Q/17PKxEuPcTN7lcfkhAWpmmeMgO9qbKdCOTNAhh5ME+Huy76gU6aN+PI+iw7HMYFcDny2qutLHbt9DxEmjBS2DwIDAQAB";

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(pbk));

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        cipher.update("helloworld张三啊".getBytes("UTF-8"));

        byte[] bytes = cipher.doFinal();

        System.out.println("密文:" + Base64.encodeBase64String(bytes));



    }
}
