package com.jin.mvc.demo;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * @author wu.jinqing
 * @date 2021年06月16日
 */
public class JasyptHelper {
    private static  final PooledPBEStringEncryptor encryptor;
    
    static {
        encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("tZ6dBG3HIC6cgy64B73Ir55UI23N3QLwJEubc6DV88mx622T6yLS");// 盐
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName(null);
        config.setProviderClassName(null);
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
    }

    public JasyptHelper() {

    }

    /** 
     * 加密
     *
     * @param message
     * @return
     */
    public static String encrypt(final String message)
    {
        return encryptor.encrypt(message);
    }

    /**
     * 解密
     * @param encryptedMessage
     * @return
     */
    public static String decrypt(final String encryptedMessage)
    {
        return encryptor.decrypt(encryptedMessage);
    }

    public static void main(String[] args) {
        System.out.println(encrypt("test543213333"));
        System.out.println(decrypt("IvtU7LqgZQPyruLMW5AkY6FvecC4rdFK"));
    }
}
