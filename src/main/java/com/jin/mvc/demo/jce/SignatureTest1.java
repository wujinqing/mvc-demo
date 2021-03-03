package com.jin.mvc.demo.jce;

import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author wu.jinqing
 * @date 2020年11月17日
 */
public class SignatureTest1 {
    public static final String body = "hello张三";

    // 生成数据签名
    public static void main(String[] args) throws Exception {
        // 私钥
        String pvk = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIxgHOyD413yggsGBFJDZUY6SG/o77v77a2akKEkd1HlNNYtpAGjRtzQFh0dDNXuF1Pw0AC7f5X1HUTlIONl8n9a3s8yOUw6X7oMuPuq4O62tDV1AM4Vr6L4NGVh6lSL3i6zKvfoVV7r4biUHbnk7C4fQfme93MxKIPudS9MnzNhAgMBAAECgYBIAvZTjsUQ6Ns1dHy7Br/pavTGFCUHtpGTQXt4bxCW/pJAXWGJRY2QsV7myIRRKrbk5OCPv6AP+ZJuSWy5uw7O6Sfu4ODt/O66FWZVpAuT97nIXCcV+obzqRvNa99U8lT7+myzWJVIiWS2cfFzJJfN7+JzLcJmf5FUYYdBx7KfwQJBAODDCuizCOJi/vbsctv6LebKI0K5NjzFIOJdRcsr0r765ZSNfiH7ZRmzUA0lcFI0dpDssldOFbSeAd+1JauyUN0CQQCf4qCNwIHru9zU64xK8qG4Q/uNI+kY3vKoaJovXSBaMtMRkux4V/UEywZfAnuKC4x9m9ORcw52mmCIMG7hESJVAkBnXcvakNv5IYYYkU2RlVEe3r+wo/UppY8mC7dZZOIbKtACrb/gaPlqBGlAYoNxiucsZ27tb9/TnQdHlsuoVu2RAkEAmkJuw7Vr5IRx0SCeUCV+8KjuyENQf8WDfAbIVFBWiZAl4vvNmWLLrN8xe3OKvCreVsaEvv2yg6342w1XvWDprQJBAIHZ3jwn9qp9h/Di6XEy8cy3TfD/zOkHpKaH/BqzHPiUnFD21E9o4AyJCfrHP6CoejJsFKrNY+6iRwmb3ns+u0U=";

        Signature signature = Signature.getInstance("SHA256withRSA");

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(pvk));

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        signature.initSign(privateKey);
        signature.update(body.getBytes("UTF-8"));
        byte[] bytes = signature.sign();

        System.out.println("sign:" + Base64.encodeBase64String(bytes));
    }
}
