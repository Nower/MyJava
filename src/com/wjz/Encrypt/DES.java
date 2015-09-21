package com.wjz.Encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by wujiazhi on 15/8/19.
 */
public class DES {

    /**
     *
     * @return DES算法密钥
     */
    public static byte[] generateKey() {
        try {

            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 生成一个DES算法的KeyGenerator对象
            KeyGenerator kg = KeyGenerator.getInstance("DES");
            kg.init(sr);

            // 生成密钥
            SecretKey secretKey = kg.generateKey();

            // 获取密钥数据
            byte[] key = secretKey.getEncoded();

            return key;
        } catch (NoSuchAlgorithmException e) {
            System.err.println("DES算法，生成密钥出错!");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 加密函数
     *
     * @param data
     *            加密数据
     * @param key
     *            密钥
     * @return 返回加密后的数据
     */
    public static byte[] encrypt(byte[] data, byte[] key) {

        try {

            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            // using DES in ECB mode
//            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");

            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

            // 执行加密操作
            byte encryptedData[] = cipher.doFinal(data);

            return encryptedData;
        } catch (Exception e) {
            System.err.println("DES算法，加密数据出错!");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解密函数
     *
     * @param data
     *            解密数据
     * @param key
     *            密钥
     * @return 返回解密后的数据
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // byte rawKeyData[] = /* 用某种方法获取原始密匙数据 */;

            // 从原始密匙数据创建一个DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            // using DES in ECB mode
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);

            // 正式执行解密操作
            byte decryptedData[] = cipher.doFinal(data);

            return decryptedData;
        } catch (Exception e) {
            System.err.println("DES算法，解密出错。");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 加密函数
     *
     * @param data
     *            加密数据
     * @param key
     *            密钥
     * @return 返回加密后的数据
     */
    public static byte[] CBCEncrypt(byte[] data, byte[] key, byte[] iv) {

        try {
            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            // Cipher对象实际完成加密操作
            //Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            // 若采用NoPadding模式，data长度必须是8的倍数
             Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");

            // 用密匙初始化Cipher对象
            IvParameterSpec param = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, param);

            // 执行加密操作
            byte encryptedData[] = cipher.doFinal(data);

            return encryptedData;
        } catch (Exception e) {
            System.err.println("DES算法，加密数据出错!");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解密函数
     *
     * @param data
     *            解密数据
     * @param key
     *            密钥
     * @return 返回解密后的数据
     */
    public static byte[] CBCDecrypt(byte[] data, byte[] key, byte[] iv) {
        try {
            // 从原始密匙数据创建一个DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            // using DES in CBC mode
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            // 若采用NoPadding模式，data长度必须是8的倍数
            // Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");

            // 用密匙初始化Cipher对象
            IvParameterSpec param = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, param);

            // 正式执行解密操作
            byte decryptedData[] = cipher.doFinal(data);

            return decryptedData;
        } catch (Exception e) {
            System.err.println("DES算法，解密出错。");
            e.printStackTrace();
        }

        return null;
    }

    private static String ivInfo = "0000000000000000";

    private static char[] CHARARRAY= {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private static String funByteToHexString(byte[] data){
        int len = data.length;
        char[] outChar = new char[len<<1];
        for(int i=0, j=0; j<len; j++){
            outChar[i++] = CHARARRAY[(0xF0 & data[j]) >>> 4];
            outChar[i++] = CHARARRAY[data[j] & 0x0F];
        }
        String outString = new String(outChar);

        return outString;
    }

    private static int parse(char c) {
        if (c >= 'a') return (c - 'a' + 10) & 0x0f;
        if (c >= 'A') return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    public static byte[] funHexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    public static void main(String[] args) {
        try {
//            byte[] key = funHexString2Bytes("D53C05F3F3A74750");
            byte[] key = funHexString2Bytes("D53C05F3F3A74750");
            byte[] iv = "00000000".getBytes();
//            byte[] data2 = DES.encrypt("12345678".getBytes(), key);
//            byte[] data2 = DES.CBCEncrypt("12000000".getBytes(), key, iv);
            byte[] data2 = DES.CBCEncrypt("00000000".getBytes(), key, iv);


            for(int i=0; i<data2.length; i++){
                System.out.print(" " + data2[i]);
            }
            System.out.println();
            System.out.println(funByteToHexString(data2));


            System.out.println();
            System.out.println(funByteToHexString("00000000".getBytes()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
