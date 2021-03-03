package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import sun.security.rsa.RSAPrivateKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author wu.jinqing
 * @date 2020年11月17日
 */
public class RSATest2 {
    // 解密
    public static void main(String[] args) throws Exception {
        // 私钥
        String pvk = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALq1SD+cAXIUYW06np1XXXZGkcuZBWr0BaVSwfgZwSBGUrXgzUsDiXh2uLL6Pe7jrjXpmrqaQaYye+0DQeQztD/Xs8rES49xM3uVx+SEBamaZ4yA72psp0I5M0CGHkwT4e7LvqBTpo348j6LDscxgVwOfLaq60sdu30PESaMFLYPAgMBAAECgYAA1yYPGz2qbMsPec8S+Dy0dpbbasHqfFS2LMwwuLT01zwyE2P5LXfl8erc62Fx/NZg6b9HjcAz5lBGfdhDFbR+/xWXdnsz7l6gcSHcMs7QGR6LHOaCK+jN7eDwaIf6NpwZOxZ3f+kwQvJQHKHWxQQLJssXA3tQiuCbIZm7mzm9YQJBAPwPqazjtW2nXesLXgi/ZMO7D6PLW4vQswG0VG+kZHv+l3MHHU7oY9Z8/4HkQDgpJTIeI1BzrHw7AhxHUSICJj8CQQC9oC7ps9we1Syg7bPlIsVU0LvT7HTqZ58ctVgnf1VLQFVzA14+Sbrwfr3IH8PYh8xDbX7l5c8PlUzGphMQY5wxAkEAlDh5pEr6wxJqLe8vSqGRlW+IkN7iZGNDADuUc1oOJMPfNyr11xLcsqIqda1M/jljwAbs6UA8K4lfglWFpiPxbQJAIsXnFHApI3ZQEsrEPHad3SnR59Dqt7l80hTnL4cIKx6HDOpHNADtSQDU6If6sBBnAs5ngN0dlWv5gkweHtlFYQJBAIhZtGHD2FLNQCE7gXH6+QltmqSjG+XbQWZ6s0yuMhdY8PMPoCkBwI7o3WQNPDTmAK+CxNoDviWr69QhMI5k0+I=";
        String sc = "ijhzIgjNwt+u/PXN5YO/5b1Mcqdlc1kcFJFklZo1vxLiDcb2NMGyG/jcQgv7y5junAuEr6HFqwYL/wY+iyDsc7zDInjrwX49vPojU5kaTqI8lsoBdUwvZ/zgQ9iA2xmQf02t3E+0XbHycfrZPYvwTIr8lc8/HQPqYz9rRt1BAbk=";

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(pvk));

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.DECRYPT_MODE, privateKey);// 加密

        cipher.update(Base64.decodeBase64(sc));

        byte[] bytes = cipher.doFinal();

        System.out.println(new String(bytes, "UTF-8"));
    }
}
