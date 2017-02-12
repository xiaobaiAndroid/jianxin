package com.bzf.jianxin.commonutils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * RSA加密解密工具
 * com.bzf.jianxin.commonutils
 * Author: baizhengfu
 * Email：709889312@qq.com
 */

public class RSACoderTool {

    /**
     * 加密算法
     */
    private static final String KEY_ALGORITHM = "RSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";

    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA秘钥长度默认是1024,秘钥长度必须是64的倍数，返回在512~65536。
     */
    private static final int KEY_SIZE = 512;

    /**
     * 生成公钥和私钥对
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static final Map<String,Object> initKey() throws NoSuchAlgorithmException {
        //KeyPairGenerator 类用于生成公钥和私钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //确定密钥大小的密钥对生成器
        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put(PUBLIC_KEY,publicKey);
        keyMap.put(PRIVATE_KEY,privateKey);
        return keyMap;

    }

    /**
     * 取得私钥
     * @param keyMap
     * @return
     */
    public static byte[] getPrivateKey(Map<String,Object> keyMap){
        PrivateKey privateKey = (PrivateKey) keyMap.get(PRIVATE_KEY);
        return privateKey.getEncoded();
    }

    /**
     * 取得公钥
     * @param keyMap
     * @return
     */
    public static byte[] getPublicKey(Map<String,Object> keyMap){
        PublicKey publicKey = (PublicKey) keyMap.get(PUBLIC_KEY);
        return publicKey.getEncoded();
    }


    /**
     * 公钥加密
     * @param data 待加密数据
     * @param key 公钥
     * @return
     */
    public static byte[] encryptByPublicKey(byte[] data,byte[] key) throws Exception {
        return decryptOrEncryptData(Cipher.ENCRYPT_MODE,data,generatePublicKey(key));
    }

    /**
     * 私钥解密
     * @param data 待解密数据
     * @param key 私钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data,byte[] key) throws Exception {
        return decryptOrEncryptData(Cipher.DECRYPT_MODE,data, generatePrivateKey(key));
    }


    /**
     * 公钥解密
     * @param data
     * @param key 公钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data,byte[] key) throws Exception {
        return decryptOrEncryptData(Cipher.DECRYPT_MODE,data, generatePublicKey(key));
    }

    /**
     * 私钥加密
     * @param data
     * @param key 私钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data,byte[] key) throws Exception {
        return decryptOrEncryptData(Cipher.ENCRYPT_MODE,data,generatePrivateKey(key));
    }

    /**
     * 生成私钥对象
     * @param key
     * @return
     * @throws Exception
     */
    private static PrivateKey generatePrivateKey(byte[] key) throws Exception{
        //按照 ASN.1 类型 PrivateKeyInfo 进行编码的专用密钥的 ASN.1 编码
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(pkcs8KeySpec);
    }



    /**
     * 解密数据
     * @param CiperMode    Cipher模式
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] decryptOrEncryptData(int CiperMode,byte[] data, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(key.getAlgorithm());
        cipher.init(CiperMode,key);
        return cipher.doFinal(data);
    }

    /**
     * 创建公钥对象
     * @param key
     * @return
     * @throws Exception
     */
    private static PublicKey generatePublicKey(byte[] key) throws Exception {
        //根据 ASN.1 类型 SubjectPublicKeyInfo 进行编码的公用密钥的 ASN.1 编码
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //返回转换指定算法的 public/private 关键字的 KeyFactory 对象。
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(x509KeySpec);
    }

}
