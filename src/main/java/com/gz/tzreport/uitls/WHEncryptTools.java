package com.gz.tzreport.uitls;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.util.io.pem.PemObject;


public class WHEncryptTools {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    private static final String hexDigIts[] = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    /**
    * @description: MD5加密,用于存储在数据库中的密码是MD5加密的
    *
    * @return: md5加密后的32位字符串
    **/
    public static String MD5Encode(String orign,String charsetname){
        String resultString = null;
        try {
            resultString = new String(orign);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || charsetname.equals("")){
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            }
            else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resultString;
    }

    public static String byteArrayToHexString(byte b[]){
        StringBuffer resultSb = new StringBuffer();
        for(int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    public static String byteToHexString(byte b){
        int n = b;
        if(n < 0){
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }

    /**
    * @description: RSA加密,生成密钥对
    *
    * @return:
    **/
    public static KeyPair genKeyPair(int KeyLength) throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }

    /**
    * @description: 把生成的公钥私钥文件保存为PEM文件
    *
    * @return: 
    **/
    public static void saveKeyAsPemFormat(Key key, String pem) throws IOException {
        PEMWriter pemWriter = new PEMWriter(new FileWriter(pem));
        pemWriter.writeObject(key);
        pemWriter.flush();
        pemWriter.close();
    }

    /**
    * @description: 从项目文件结构中获取私钥pem文件
    *
    * @return: 
    **/
    public static PrivateKey getPemPrivateKey(String filename, String algorithm) throws Exception {

        InputStream in = WHEncryptTools.class.getClassLoader().getResource(filename).openStream();
//        BufferedReader br = new BufferedReader(new FileReader(filename));
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        Security.addProvider(new BouncyCastleProvider());

        PEMReader pp = new PEMReader(br);
        PemObject pem = pp.readPemObject();
        byte[] content = pem.getContent();
        pp.close();

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(content);
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        return kf.generatePrivate(spec);
    }

    /**
    * @description: 从项目文件夹获取公钥pem文件
    *
    * @return:
    **/
    public static PublicKey getPemPublicKey(String pubKeyStr) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        InputStream in = WHEncryptTools.class.getClassLoader().getResource(pubKeyStr).openStream();
//        BufferedReader br = new BufferedReader(new FileReader(pubKeyStr));
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        Security.addProvider(new BouncyCastleProvider());
        PEMReader pp = new PEMReader(br);
        PemObject pem = pp.readPemObject();
        byte[] content = pem.getContent();
        pp.close();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(content);
        RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(pubSpec);
        return pubKey;
    }

    /**
    * @description: 公钥加密返回加密数据
    *
    * @return:
    **/

    public static String RSAEncrypt(String content, PublicKey publicKey) throws Exception {

        Cipher cipher=Cipher.getInstance("RSA");//java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        int splitLength=((RSAPublicKey)publicKey).getModulus().bitLength()/8-11;
        byte[][] arrays=splitBytes(content.getBytes(), splitLength);
        StringBuffer sb=new StringBuffer();
        for(byte[] array : arrays){
            sb.append(bytesToHexString(cipher.doFinal(array)));
        }
        return sb.toString();
    }


    /**
    * @description: // 私钥解密得到返回的真实数据
    *
    * @return:
    **/

    public static String RSADecrypt(String content, PrivateKey privateKey) throws Exception {
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        int splitLength=((RSAPrivateKey)privateKey).getModulus().bitLength()/8;
        byte[] contentBytes=hexString2Bytes(content);
        byte[][] arrays=splitBytes(contentBytes, splitLength);
        StringBuffer sb=new StringBuffer();
        for(byte[] array : arrays){
            sb.append(new String(cipher.doFinal(array)));
        }
        return sb.toString();
    }

    //拆分byte数组
    public static byte[][] splitBytes(byte[] bytes, int splitLength){
        int x; //商，数据拆分的组数，余数不为0时+1
        int y; //余数
        y=bytes.length%splitLength;
        if(y!=0){
            x=bytes.length/splitLength+1;
        }else{
            x=bytes.length/splitLength;
        }
        byte[][] arrays=new byte[x][];
        byte[] array;
        for(int i=0; i<x; i++){

            if(i==x-1 && bytes.length%splitLength!=0){
                array=new byte[bytes.length%splitLength];
                System.arraycopy(bytes, i*splitLength, array, 0, bytes.length%splitLength);
            }else{
                array=new byte[splitLength];
                System.arraycopy(bytes, i*splitLength, array, 0, splitLength);
            }
            arrays[i]=array;
        }
        return arrays;
    }

    //byte数组转十六进制字符串
    public static String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    //十六进制字符串转byte数组
    public static byte[] hexString2Bytes(String hex) {
        int len = (hex.length() / 2);
        hex=hex.toUpperCase();
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }
    
}
