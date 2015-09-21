package com.wjz.CharsetAndEncoding;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by wujiazhi on 15/9/5.
 */
public class EncodingTest {

    private final static char[] HEX = "0123456789abcdef".toCharArray();

    public static void main(String[] args) throws Exception{

        testBytes("a中国b");
        //아니超过GBK表示范围
        testBytes("아니");
        //いいえ
        testBytes("いいえ");

        //设置JVM默认字符编码只能在JVM启动时，以启动参数的形式进行设置
        System.out.println(System.getProperty("file.encoding"));
        //UTF-16 is how text is represented internally in the JVM.
        //The default encoding determines how the JVM interprets bytes read from files (using FileReader, for example).
        System.out.println(Charset.defaultCharset());
    }


    /**
     * 字节流转成16进制输出
     * @param bys
     * @return
     */
    public static String bytes2HexString(byte[] bys) {
        char[] chs = new char[bys.length * 2 + bys.length - 1];
        for (int i = 0, offset = 0; i < bys.length; i++) {
            if (i > 0) {
                chs[offset++] = ' ';
            }
            chs[offset++] = HEX[bys[i] >> 4 & 0xf];
            chs[offset++] = HEX[bys[i] & 0xf];
//            System.out.print(bys[i]);
        }
//        System.out.println();
        return new String(chs);
    }

    /**
     * 16进制字符转成字节流
     * @param cardno
     * @return
     */
    public static byte[] hex2Byte(String cardno){
        int cardlength = cardno.length();
        if (cardlength % 2 ==1) {
            cardlength = cardlength+1;
            cardno = "0" + cardno;   // ??0
        }
        byte[] card = cardno.getBytes();
        for (int i=0; i<card.length;i++) {
            switch(card[i])
            {
                case 0x41: card[i] = 10;break;
                case 0x42: card[i] = 11;break;
                case 0x43: card[i] = 12;break;
                case 0x44: card[i] = 13;break;
                case 0x45: card[i] = 14;break;
                case 0x46: card[i] = 15;break;
                case 0x61: card[i] = 10;break;
                case 0x62: card[i] = 11;break;
                case 0x63: card[i] = 12;break;
                case 0x64: card[i] = 13;break;
                case 0x65: card[i] = 14;break;
                case 0x66: card[i] = 15;break;
                default:break;
            }
        }
        byte[] cardbyte = new byte[cardlength/2];
        for (int i = 0; i < cardlength; i = i + 2) {
            cardbyte[i/2] = (byte)(card[i]<<4 | (card[i+1] & 0x0f));
        }
        return cardbyte;
    }

    /**
     * 字节流转换成二进制输出
     * @param cardno
     * @return
     */
    public static String byte2String(byte[] cardno)
    {
        String cardnostring = "";
        for (int i = 0; i < cardno.length; i++) {
            String charcode = Integer.toHexString(cardno[i]);
            // byte????10???
            if (charcode.length() == 1) {
                charcode = "0"+charcode;
                // ????0x80??????????ffffff**
            } else if (charcode.length() == 8) {
                charcode = charcode.substring(6);
            }
            cardnostring = cardnostring + charcode;
        }

        return cardnostring;
    }


    /**
     * 同一个字符串在不同编码下输出的二进制
     * @param str
     * @throws UnsupportedEncodingException
     */
    public static void testBytes(String str) throws UnsupportedEncodingException {
        String[] encoding = {
//                "Unicode",//JVM默认是UTF-16
                "ASCII",
                "ISO-8859-1",
                "GBK",//中文占两个字节，兼容ASCII码
//                "UnicodeBig",
//                "UnicodeLittle",
//                "UnicodeBigUnmarked",
//                "UnicodeLittleUnmarked",
                "UTF-8",//中文占三个字节，兼容ASCII码
                "UTF-16",
//                "UTF-16BE",
//                "UTF-16LE",
                "UTF-32",//???????????
//                "UTF-32BE",
//                "UTF-32LE",
//                "UTF-32BE-BOM",
//                "UTF-32LE-BOM"
        };

        System.out.println("测试字符串:" + str);
        System.out.print("JVM中字符编号(UTF-16编码)：");
        for(int i=0; i<str.length(); i++){
            System.out.print(getCharValue(str.substring(i, i+1).getBytes("UTF-16BE")) + " ");
        }
        System.out.println();
        for (int i = 0; i < encoding.length; i++) {
            byte[] bytes = str.getBytes(encoding[i]);
            System.out.printf("%-22s %s%n", encoding[i], bytes2HexString(bytes));
//            System.out.printf("%-22s %s%n", encoding[i], byte2String(str.getBytes(encoding[i])));
        }
    }

    public static int getCharValue(byte[] bytes){
        int res = 0;
        for(int i=0; i<bytes.length; i++){
            res = (res<<8) + (int)(bytes[i]);
        }
        return res;
    }
}
