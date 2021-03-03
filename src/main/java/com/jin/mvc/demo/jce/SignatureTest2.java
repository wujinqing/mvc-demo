package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Base64;
import sun.security.rsa.RSAPublicKeyImpl;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author wu.jinqing
 * @date 2020年11月17日
 */
public class SignatureTest2 {
    // 验证数据签名
    public static void main(String[] args) throws Exception {
        // 公钥
        String pbk = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMYBzsg+Nd8oILBgRSQ2VGOkhv6O+7++2tmpChJHdR5TTWLaQBo0bc0BYdHQzV7hdT8NAAu3+V9R1E5SDjZfJ/Wt7PMjlMOl+6DLj7quDutrQ1dQDOFa+i+DRlYepUi94usyr36FVe6+G4lB255OwuH0H5nvdzMSiD7nUvTJ8zYQIDAQAB";
        String sign = "TW83OMTNnkkHisd36InHfBudjJ9d/FOq6sLj5qiX1FiNiimF2ip9GvIpByy5Bfw+W5ZoJ8DZn5yvfT7+v4E+gjvpqKH9o7SHBcYt9fKkf3ysUaSGGHZuncUtS+zryvptPi+CEfWwJUICSRpzHtBUm9trtmt+pslBdpoVYltNvAk=";

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(pbk));

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance("SHA256withRSA");

        signature.initVerify(publicKey);
        signature.update(SignatureTest1.body.getBytes("UTF-8"));

        boolean verify = signature.verify(Base64.decodeBase64(sign));

        System.out.println(verify);
    }
}
