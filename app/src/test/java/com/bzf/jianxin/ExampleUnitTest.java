package com.bzf.jianxin;

import com.bzf.jianxin.commonutils.RSACoderTool;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

    }

    @Test
    public void testInetAddress(){
        try {
            String host = "www.baidu.com";
            InetAddress inetAddress = InetAddress.getByName(host);
            System.out.println("baidu是否可达"+inetAddress.isReachable(5000));
            System.out.println("此IP的完全限定名："+inetAddress.getCanonicalHostName());
            System.out.println("IP地址字符串："+inetAddress.getHostAddress());
            InetAddress[] inetAddresses = InetAddress.getAllByName(host);
            for(InetAddress ia :inetAddresses){
                System.out.println("IP地址字符串："+ia.getHostAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testURLEncoderDecoder(){
        String content = "张三";
        String enc = "utf-8";
        try {
            content = URLEncoder.encode(content, enc);
            System.out.println("Encoder:"+content);
            content = URLDecoder.decode(content, enc);
            System.out.println("Decoder:"+content);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRSA(){
        try {
            Map<String, Object> map = RSACoderTool.initKey();
            String content = "Java加密与解密";
            System.out.println("公钥加密，私钥解密：--------------------------");
            byte[] encryptContent = RSACoderTool.encryptByPublicKey(content.getBytes(), RSACoderTool.getPublicKey(map));
            System.out.println("加密后："+ new String(encryptContent));
            byte[] decryptContent = RSACoderTool.decryptByPrivateKey(encryptContent, RSACoderTool.getPrivateKey(map));
            System.out.println("解密后："+new String(decryptContent));

            System.out.println("私钥加密，公钥解密：--------------------------");
            encryptContent = RSACoderTool.encryptByPrivateKey(content.getBytes(),RSACoderTool.getPrivateKey(map));
            System.out.println("私钥加密后："+ new String(encryptContent));

            decryptContent = RSACoderTool.decryptByPublicKey(encryptContent, RSACoderTool.getPublicKey(map));
            System.out.println("公钥解密后："+ new String(decryptContent));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}