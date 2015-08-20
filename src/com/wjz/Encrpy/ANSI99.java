package com.wjz.Encrpy;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * Created by wujiazhi on 15/8/20.
 */
public class ANSI99 {
    private static char[] CHARARRAY= {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private static byte[] funStringToBcd(char[] data){
        int len = data.length;
        if(len % 2 != 0 || len == 0){
            throw new RuntimeException("数据长度错误");
        }
        byte[] outData = new byte[len >> 1];
        for(int i=0, j=0; j<len; i++){
            outData[i] = (byte) (((Character.digit(data[j], 16) & 0x0F) << 4) | (Character.digit(data[j+1], 16) & 0x0F));
            j++;
            j++;
        }

        return outData;
    }

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

    public static void printBytes(byte[] bytes){
        for(int i=0; i<bytes.length; i++){
            System.out.println(bytes[i]);
        }
    }

    public static byte[] ecbEncrypt(String input, String key) {
        byte[] xor = new byte[8];
        try {
            //转换成字节数组
            byte[] inputBytes = input.getBytes();
            int len = inputBytes.length;
            //D1....DN，groups就是N
            int groups = (len + 7) / 8;
            int other = len % 8;
            // 如果密文最后不足8个字节，则以0补足
            if (other != 0) {
                byte[] tempBytes = inputBytes;
                inputBytes = new byte[tempBytes.length + (8 - other)];
                System.arraycopy(tempBytes, 0, inputBytes, 0, len);
            }

            for(int i=0; i<groups; i++){
                //首先和xor进行异或
                int start = i * 8;
                int xorPos = 0;
                for(int j = start; xorPos<8; xorPos++, j++){
                    xor[xorPos] ^= inputBytes[j];
                }
                //异或后再进行每一组数据的DES加密
                xor = desEncrypt(xor, key.getBytes());
            }

            return xor;
        } catch (Exception e) {

            return null;
        }
    }


    public static byte[] desEncrypt(byte[] src, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");

        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        // 执行加密操作
        return cipher.doFinal(src);
    }

    public static void main(String[] args){
//        System.out.println("Final :" + funByteToHexString(ecbEncrypt("12345678", "D53C05F3F3A74750")));
        System.out.println("Final :" + new String(ecbEncrypt("F4F3E7B3566F6622098750B491EA8D5C6", "9BED98891580C3B2")));

    }

}
