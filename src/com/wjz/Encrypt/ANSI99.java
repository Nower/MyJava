package com.wjz.Encrypt;

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

    public static byte[] funStringToBcd(char[] data){
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

    public static String funByteToHexString(byte[] data){

        int len = data.length;
        char[] outChar = new char[len<<1];
        for(int i=0, j=0; j<len; j++){
            outChar[i++] = CHARARRAY[(0xF0 & data[j]) >>> 4];
            outChar[i++] = CHARARRAY[data[j] & 0x0F];
        }
        String outString = new String(outChar);

        return outString;
    }

    public static byte[] ecbEncryptString(String input, byte[] key, byte[] iv) {
        //转换成字节数组
        byte[] inputBytes = input.getBytes();
        return ecbEncryptByte(inputBytes, key, iv);
    }

    public static byte[] ecbEncryptByte(byte[] inputBytes, byte[] key, byte[] iv) {
        byte[] xor = new byte[8];
        try {
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
                //异或后再进行每一组数据的DES加密,1和3的效果是一样的

                //默认的DES密钥只能为8个字节，如果超过8个字节就被会截断；所以招行信用卡的密钥肯定是16进制形式的字符，16个这样的字符，其实是8字节的密钥；
                //但经过加密网站和代码验证，都和招行加密机的结果不相同；
//                xor = desEncrypt(xor, key);//1
                xor = DES.CBCEncrypt(xor, key, iv);//2
//                xor = DES.encrypt(xor, key);//3
                byte[] nXor = new byte[8];
                System.arraycopy(xor, xor.length-8, nXor, 0, 8);
                xor = nXor;
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
        //
        String mac = "0200 166225768738823168 00X000 000000001980 0824143824 7399 00 0897132900 0897132900 SH407100 308999873990426";
//        String mac1 = "30313130a23a40818ac1843000000000020000013331303030303033303831353236303834363630313031353236303830333038303132333532313139323038303031363234303030383534313132303733313532363038343636303130303030303030303030323330383239303035343131323031333036384b4646455a534a3230303031323320202020202020202020202020202020202020202020202020202020202031303030303030303030303030303030347465737474742331353630343030303136313536443132333435363738393032323030313631353644313233343536373839303232303233424932303039303930303030787878787878787878782330313430303030303030303636303030303038353431313230373300000000000000";
        String mac1 = "30313130a23a40818ac184300000000002000001333130303030000000000000";
//        String mac = "MDIwMCAxNjYyMjU3Njg3Mzg4MjMxNjggMDBYMDAwIDAwMDAwMDAwMTk4MCAwODI0MTQzODI0IDczOTkgMDAgMDg5NzEzMjkwMCAwODk3MTMyOTAwIFNINDA3MTAwIDMwODk5OTg3Mzk5MDQyNg==";
        byte[] key = DES.funHexString2Bytes("D53C05F3F3A74750");
        byte[] key1 = DES.funHexString2Bytes("159D7CA749D5CE97");
        byte[] iv = DES.funHexString2Bytes("0000000000000000");
        System.out.println(mac1.length());
        System.out.println("Final :" + funByteToHexString(ecbEncryptString(mac, key, iv)));
        System.out.println("Final :" + funByteToHexString(ecbEncryptByte(DES.funHexString2Bytes(mac1), key1, iv)));
//        System.out.println("Final :" + funByteToHexString(ecbEncrypt("testingSimulateEncryptMachineTxt", "D53C05F3F3A74750")));

    }

}
