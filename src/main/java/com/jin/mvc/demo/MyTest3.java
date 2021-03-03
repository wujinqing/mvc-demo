package com.jin.mvc.demo;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 * @author wu.jinqing
 * @date 2020年11月12日
 */
public class MyTest3 {
    public static void main(String[] args) throws Exception{
        java.security.KeyPairGenerator keygen=java.security.KeyPairGenerator.getInstance("DSA");

//如果设定随机产生器就用如相代码初始化
        SecureRandom secrand=new SecureRandom();
        secrand.setSeed("tttt".getBytes()); // 初始化随机产生器
        keygen.initialize(512,secrand);     // 初始化密钥生成器

//否则
        keygen.initialize(512);

//生成密钥公钥 pubkey 和私钥 prikey
        KeyPair keys=keygen.generateKeyPair(); // 生成密钥组
        PublicKey pubkey=keys.getPublic();
        PrivateKey prikey=keys.getPrivate();

//分别保存在 myprikey.dat 和 mypubkey.dat 中 , 以便下次不在生成
//( 生成密钥对的时间比较长
        java.io.ObjectOutputStream out=new java.io.ObjectOutputStream(
                new java.io.FileOutputStream("myprikey.dat"));
        out.writeObject(prikey);
        out.close();
        out=new java.io.ObjectOutputStream(new java.io.FileOutputStream("mypubkey.dat"));
        out.writeObject(pubkey);
        out.close();
    }
}
