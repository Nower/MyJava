package com.wjz.Encrypt;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class DESUtil2 {

    /**
     * 构造器.
     */
    protected DESUtil2() {
        throw new UnsupportedOperationException();
    }

    /**
     * @param args
     *            args
     * @throws Exception
     *             Exception
     */
    public static void main(String[] args) throws Exception {
		/* 生成des随机密钥 */
        byte[] key = getKey();
		/* 将byte数组转为16进制字符串 */
        String hex = getHexString(key);
        // /* 将16进制的字符串转为字节数组；一个16进制数对应4位二进制。长度自己可以任意创建，单实际加密解密过程中只会用到前56位 */
//        hex = "0fa61e1b77b51b8a";
        hex = "D53C05F3F3A74750";
        key = getByteArray(hex);
        //Assert.assertEquals(hex.length(), key.length * 2);
        System.out.println("key Length:" + key.length + "字节");
        for (int i = 0; i < key.length; i++) {
            System.out.print(key[i] + " ");
        }
        System.out.println("");
		/* 等加密明文 */
//        String s = "1111aaaa1111aaaa1111aaaa";
        String s = "12345678";
        System.out.println("明文:" + s);
		/* 加密结果字节数组 */
        byte[] encryptedData = encrypt(s.getBytes(), key);
        for (int i = 0; i < encryptedData.length; i++) {
            System.out.print(encryptedData[i] + " ");
        }
        System.out.println("");
        System.out.println("encryptedData Length:" + encryptedData.length + "字节");
		/* 将byte数组转为16进制字符串 */
        System.out.println("encryptedData hex:" + getHexString(encryptedData));
        System.out.println("");

		/* 解密结果字节数组 */
//        byte[] decryptedData = decrypt(encryptedData, key);
//        System.out.println("解密：" + new String(decryptedData));
    }

    /**
     * decrypt.
     *
     * @param encryptedData
     *            encryptedData
     * @param key
     *            key
     * @return String
     */
    public static String decrypt(String encryptedData, String key) {
        String ret = null;
        byte[] keyByte = DESUtil2.getByteArray(key);
        byte[] byteData = DESUtil2.getByteArray(encryptedData.toLowerCase());
        try {
            ret = new String(DESUtil2.decrypt(byteData, keyByte));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * DES加密.
     *
     * @param data
     *            .
     * @param keyBytes
     *            .
     * @return byte[]
     * @throws Exception .
     */
    public static byte[] encrypt(byte[] data, byte[] keyBytes) throws Exception {
		/* 实例化DES密钥材料 */
        DESKeySpec dks = new DESKeySpec(keyBytes);
		/* 实例化秘密密钥工厂 */
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		/* 生成秘密密钥 */
        SecretKey key = keyFactory.generateSecret(dks);
        SecureRandom sr = new SecureRandom();
		/* jce框架核心，为加密和解密提供密码功能 */
//        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
		/* 设置加密模式 */
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        byte[] encryptedData = cipher.doFinal(data);
        return encryptedData;
    }

    /**
     * DES解密.
     *
     * @param data
     *            .
     * @param keyBytes
     *            .
     * @return byte[]
     * @throws Exception .
     */
    public static byte[] decrypt(byte[] data, byte[] keyBytes) throws Exception {
		/* 实例化DES密钥材料 */
        DESKeySpec dks = new DESKeySpec(keyBytes);
		/* 实例化秘密密钥工厂 */
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		/* 生成秘密密钥 */
        SecretKey key = keyFactory.generateSecret(dks);
        SecureRandom sr = new SecureRandom();
//        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, sr);
        byte[] decryptedData = cipher.doFinal(data);
        return decryptedData;
    }

    /**
     * 随机生成密钥.
     *
     * @return byte[]
     * @throws Exception .
     */
    public static byte[] getKey() throws Exception {
		/* 随机数生成器 */
        SecureRandom sr = new SecureRandom();
		/* 实例化对称密钥生成器 */
        KeyGenerator kg = KeyGenerator.getInstance("DES");
		/* 初始化；以下两种方式都将创建一个56位长度的密钥 */
        kg.init(56);
        kg.init(sr);
		/*
		 * 生成秘密密钥;java仅支持56位密钥长度，作为补充，Bouncy
		 * Castle提供64位密钥的支持；【使用的密钥为64位,有效密钥长度为56位(有8位用于奇偶校验)】
		 */
        SecretKey key = kg.generateKey();
		/* 获得密钥的二进制编码形式，8个字节 */
        byte[] b = key.getEncoded();
        return b;
    }

    /**
     * byte数组转换成16进制字符串.
     *
     * @param b
     *            .
     * @return String
     */
    public static String getHexString(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            result.append(hex.toLowerCase());
        }
        return result.toString();
    }

    /**
     * 16进制字符串转换成byte数组.
     *
     * @param hex
     *            .
     * @return byte[]
     */
    public static byte[] getByteArray(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    /**
     * toByte.
     *
     * @param c
     *            c
     * @return int
     */
    private static int toByte(char c) {
        byte b = (byte) "0123456789abcdef".indexOf(c);
        return b;
    }
}
