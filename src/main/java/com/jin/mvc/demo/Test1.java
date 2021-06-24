package com.jin.mvc.demo;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * @author wu.jinqing
 * @date 2021年06月16日
 */
public class Test1 {
    public static void main(String[] args) {

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("CB8BE63B16E9608B92D76CBBB68E1B7C");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName(null);
        config.setProviderClassName(null);
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);


        String s = encryptor.encrypt("1234abcd");
        System.out.println(s);
//        String s2 = encryptor.decrypt("80UrJEYulgLkr/A1w1T/fVqYwaXcxFog");
//        System.out.println(s2);
    }
}
