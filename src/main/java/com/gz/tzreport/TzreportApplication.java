package com.gz.tzreport;

import com.gz.tzreport.uitls.WHEncryptTools;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@MapperScan("com.gz.tzreport.dao")

@SpringBootApplication
public class TzreportApplication {

    public static void main(String[] args) {
        SpringApplication.run(TzreportApplication.class, args);

//        try {
//            KeyPair keyPair = WHEncryptTools.genKeyPair(1024);
//            PrivateKey privateKey = keyPair.getPrivate();
//            WHEncryptTools.saveKeyAsPemFormat(privateKey, "rsa_private_key.pem");
//            System.out.println("新建私钥是:" + new String(Base64.getEncoder().encode(privateKey.getEncoded())));
//
//            PublicKey publicKey = keyPair.getPublic();
//            WHEncryptTools.saveKeyAsPemFormat(publicKey, "rsa_public_key.pem");
//            System.out.println("新建公钥是:" + new String(Base64.getEncoder().encode(publicKey.getEncoded())));
//
//            PrivateKey privateKey2 = WHEncryptTools.getPemPrivateKey("rsa_private_key.pem", "RSA");
//            System.out.println("读取私钥是:" + new String(Base64.getEncoder().encode(privateKey2.getEncoded())));
//
//            PublicKey publicKey2 = WHEncryptTools.getPemPublicKey("rsa_public_key.pem");
//            System.out.println("读取公钥是:" + new String(Base64.getEncoder().encode(publicKey2.getEncoded())));
//
//            String teststr = "leewihong";
//            String testhello = WHEncryptTools.RSAEncrypt(teststr, publicKey);
//            System.out.println(testhello);
//
//            String decryhello = WHEncryptTools.RSADecrypt(testhello, privateKey2);
//            System.out.println("解密后:" + decryhello);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

//    public class SpringBootStartApplication extends SpringBootServletInitializer{
//        @Override
//        protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
//            return builder.sources(TzreportApplication.class);
//        }
//    }
}
