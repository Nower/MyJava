package com.wjz.CharsetAndEncoding;


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.BitSet;

/**
 * Created by wujiazhi on 15/6/17.
 */
public class Encoding {
    public static void main(String[] args) throws Exception{
//        charsetTest();
//        charTest();
//        testBytes();
//        testStringFromBytes();
//        testUTF8ToGBK();

        String str = "涓枃";
        String str1 = "𤭢";//UTF-16                 fe ff d8 52 df 62
//        stringTest(str1, "UTF-16");

        String str2 = "中文";
        stringTest(str2, "UTF-8");//e4 b8 ad e6 96 87
        stringTest(str2, "GBK");//d6 d0 ce c4
    }

    /**
     * JAVA 使用的是 UNICODE，JVM内部可以根据不同的编码字符集进行转换。
     */
    public static void testUTF8ToGBK(){
        String str="字符串编码转换";//在内存中是Unicode字符
        try {
            byte[] temp=str.getBytes("gbk");//这里写原编码方式Unicode使用utf-8编码
            byte[] newtemp=new String(temp,"gbk").getBytes("utf-8");//字节流进行转码，装成GBK编码
            String newStr=new String(newtemp,"utf-8");//这里写转换后的编码方式
            System.out.println(newStr);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * ??String
     * getBytes();
     * -Dfile.encoding
     */
    public static void stringTest(String str, String encoding) throws Exception{
        System.out.printf("%-22s %s%n", encoding, bytes2HexString(str.getBytes(encoding)));
    }

    public static void printEncoding(String charset) throws Exception{
        System.out.println("a(" + charset + ")    ?" + "a".getBytes(charset).length);
        System.out.println("aa(" + charset + ")    ?" + "aa".getBytes(charset).length);
        System.out.println("?(" + charset + ")   ?" + "?".getBytes(charset).length);
        System.out.println("??(" + charset + ") ?" + "??".getBytes(charset).length);
        System.out.println("");
    }

    /**
     * Unicode?UTF-16?1????2????????????,??????Unicode?>=0x10000?UTF-16????4???
     * UTF-8?1??????1???????????????????3???,??????Unicode?>=0x10000?UTF-8????4???
     * Java??char????Unicode?????Java?char?2???(????????????Unicode??????0x10000)
     * GBK???????????????
     * @throws Exception
     */
    public static void charTest() throws Exception{
        /**
         * a(Unicode)    ?4(?????BOM??????fe ff??????????????2????????)
         * aa(Unicode)    ?6??????BOM??fe ff?
         * ?(Unicode)   ?4??????BOM??fe ff?
         * ??(Unicode) ?6??????BOM??fe ff?
         */
        printEncoding("Unicode");
        /**
         * Unicode????????BOM?fe ff
         * a(UnicodeBig)    ?4
         * aa(UnicodeBig)    ?6
         * ?(UnicodeBig)   ?4
         * ??(UnicodeBig) ?6
         */
        printEncoding("UnicodeBig");
        /**
         * Unicode????????BOM?ff fe
         * a(UnicodeLittle)    ?4
         * aa(UnicodeLittle)    ?6
         * ?(UnicodeLittle)   ?4
         * ??(UnicodeLittle) ?6
         */
        printEncoding("UnicodeLittle");
        /**
         * ????????BOM?
         * a(UnicodeBigUnmarked)    ?2
         * aa(UnicodeBigUnmarked)    ?4
         * ?(UnicodeBigUnmarked)   ?2
         * ??(UnicodeBigUnmarked) ?4
         */
        printEncoding("UnicodeBigUnmarked");
        /**
         * ????????BOM?
         * a(UnicodeLittleUnmarked)    ?2
         * aa(UnicodeLittleUnmarked)    ?4
         * ?(UnicodeLittleUnmarked)   ?2
         * ??(UnicodeLittleUnmarked) ?4
         */
        printEncoding("UnicodeLittleUnmarked");
        /**
         * UTF-8????????????????BOM?
         * a(UTF-8)    ?1
         * aa(UTF-8)   ?2
         * ?(UTF-8)   ?3
         * ??(UTF-8) ?6
         */
        printEncoding("UTF-8");
        /**
         * UTF-16??????????????fe ff
         * a(UTF-16)    ?4??Unicode????????BOM????
         * aa(UTF-16)   ?6
         * ?(UTF-16)   ?4
         * ??(UTF-16) ?6
         */
        printEncoding("UTF-16");
        /**
         * ??????????BOM
         * a(UTF-16BE)    ?2
         * aa(UTF-16BE)   ?4
         * ?(UTF-16BE)   ?2
         * ??(UTF-16BE) ?4
         */
        printEncoding("UTF-16BE");
        /**
         * ??????????BOM
         * a(UTF-16LE)    ?2
         * aa(UTF-16LE)   ?4
         * ?(UTF-16LE)   ?2
         * ??(UTF-16LE) ?4
         */
        printEncoding("UTF-16LE");
        /**
         * a(GBK)    ?1
         * aa(GBK)   ?2
         * ?(GBK)   ?2
         * ??(GBK) ?4
         */
        printEncoding("GBK");
        /**
         * UTF-32????BOM??????????????BOM?
         * a(UTF-32)    ?4
         * aa(UTF-32)   ?8
         * ?(UTF-32)   ?4
         * ??(UTF-32) ?8
         */
        printEncoding("UTF-32");
        /**
         * a(UTF-32BE)    ?4
         * aa(UTF-32BE)   ?8
         * ?(UTF-32BE)   ?4
         * ??(UTF-32BE) ?8
         */
        printEncoding("UTF-32BE");
        /**
         * a(UTF-32LE)    ?4
         * aa(UTF-32LE)   ?8
         * ?(UTF-32LE)   ?4
         * ??(UTF-32LE) ?8
         */
        printEncoding("32LE");
    }

    public static void testStringFromBytes() throws Exception{
        /**
         * a??b
         *
         * Unicode                fe ff 00 61 4e 2d 56 fd 00 62
         UnicodeBig             fe ff 00 61 4e 2d 56 fd 00 62
         UnicodeLittle          ff fe 61 00 2d 4e fd 56 62 00
         UnicodeBigUnmarked     00 61 4e 2d 56 fd 00 62
         UnicodeLittleUnmarked  61 00 2d 4e fd 56 62 00
         UTF-8                  61 e4 b8 ad e5 9b bd 62
         UTF-16                 fe ff 00 61 4e 2d 56 fd 00 62
         UTF-16BE               00 61 4e 2d 56 fd 00 62
         UTF-16LE               61 00 2d 4e fd 56 62 00
         GBK                    61 d6 d0 b9 fa 62
         UTF-32                 00 00 00 61 00 00 4e 2d 00 00 56 fd 00 00 00 62
         UTF-32BE               00 00 00 61 00 00 4e 2d 00 00 56 fd 00 00 00 62
         UTF-32LE               61 00 00 00 2d 4e 00 00 fd 56 00 00 62 00 00 00
         UTF-32BE-BOM           00 00 fe ff 00 00 00 61 00 00 4e 2d 00 00 56 fd 00 00 00 62
         UTF-32LE-BOM           ff fe 00 00 61 00 00 00 2d 4e 00 00 fd 56 00 00 62 00 00 00
         */
        byte[] b1 = {127,97,98,65,127};//一个byte是从-128到127,ASCII码中0到31用于记录特殊字符，127表示删除；
        byte[] b2 = {127,6,'d',0};//
        byte b = -128;
        String str = new String(b1, "GBK");
        System.out.println(str);
    }

    public static void testBytes() throws UnsupportedEncodingException {
        String str1 = "??";
        String str = "a??b";
        String[] encoding = {
                "Unicode",//JVM默认是UTF-16
                "UnicodeBig",
                "UnicodeLittle",
                "UnicodeBigUnmarked",
                "UnicodeLittleUnmarked",
                "UTF-8",//中文占三个字节，兼容ASCII码
                "UTF-16",
                "UTF-16BE",
                "UTF-16LE",
                "GBK",//中文占两个字节，兼容ASCII码
                "UTF-32",//???????????
                "UTF-32BE",
                "UTF-32LE",
                "UTF-32BE-BOM",
                "UTF-32LE-BOM"};

        /**
         * Unicode                fe ff 4e 2d 56 fd
         * UnicodeBig             fe ff 4e 2d 56 fd
         * UnicodeLittle          ff fe 2d 4e fd 56
         * UnicodeBigUnmarked     4e 2d 56 fd
         * UnicodeLittleUnmarked  2d 4e fd 56
         * UTF-8                  e4 b8 ad e5 9b bd
         * UTF-16                 fe ff 4e 2d 56 fd
         * UTF-16BE               4e 2d 56 fd
         * UTF-16LE               2d 4e fd 56
         * GBK                    d6 d0 b9 fa
         * UTF-32                 00 00 4e 2d 00 00 56 fd
         * UTF-32BE               00 00 4e 2d 00 00 56 fd
         * UTF-32LE               2d 4e 00 00 fd 56 00 00
         * UTF-32BE-BOM           00 00 fe ff 00 00 4e 2d 00 00 56 fd
         * UTF-32LE-BOM           ff fe 00 00 2d 4e 00 00 fd 56 00 00
         *
         * ????UTF-16????????????BOM???UTF-32??????????????BOM?
         *
         * ??????????
         * 1???????????????0x1234?????0x3412???????????????????
         * 2?????????????????????????UTF-16LE???
         * ?????????????4e 2d(?) 56 fd???
         * ????????????UTF-16??????????????????????? 2d 4e(?)fd 56(?)?
         * ??????UTF-32??????4????????????4???????
         *
         */
        for (int i = 0; i < encoding.length; i++) {
            System.out.printf("%-22s %s%n", encoding[i], bytes2HexString(str.getBytes(encoding[i])));
//            System.out.printf("%-22s %s%n", encoding[i], byte2String(str.getBytes(encoding[i])));
        }
    }

    private final static char[] HEX = "0123456789abcdef".toCharArray();

    public static String bytes2HexString(byte[] bys) {
        char[] chs = new char[bys.length * 2 + bys.length - 1];
        for (int i = 0, offset = 0; i < bys.length; i++) {
            if (i > 0) {
                chs[offset++] = ' ';
            }
            chs[offset++] = HEX[bys[i] >> 4 & 0xf];
            chs[offset++] = HEX[bys[i] & 0xf];
            System.out.print(bys[i]);
        }
        System.out.println();
        return new String(chs);
    }

    /**
     * ??ByteBuffer
     */
    public static void bytebufferTest(){

    }

    /**
     * ??File
     */
    public static void fileTest(){

    }

    /**
     * ??Charset
     * Charset.defaultCharset()
     */
    public static void charsetTest() throws Exception{
        String s = Charset.defaultCharset().displayName();
        String s1 = "????,My Love";

        ByteBuffer bb1 = ByteBuffer.wrap(s1.getBytes("UTF-8"));

        for(byte bt:bb1.array()){
            System.out.printf("%x",bt);
        }
        //char[]??
        char[] chArray={'I','L','o','v','e','?'};

        //CharBuffer??
        CharBuffer cb = CharBuffer.wrap(chArray);
        //??????
        cb.flip();

        String s2= new String(chArray);

        //ByteBuffer??
        ByteBuffer bb2 = Charset.forName("utf-8").encode(cb);

        // ??Charset????????

        ByteBuffer bb3 = Charset.forName("utf-8").encode(s1);

        byte [] b   = bb3.array() ;

        // ??Charset????????????
        ByteBuffer bb4= ByteBuffer.wrap(b);

        String s4 = Charset.forName("utf-8").decode(bb4).toString();
    }

    /**
     * 16?????byte?ISO8583????
     */
    public static byte[] hex2Byte(String cardno)
    {
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
     * byte?string?ISO8583????
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
     * ISO8583 ASCII?string???GBK?UTF-8???
     */
    public String ASC2String(byte[] msg)
    {
        String gbk_msg = "";
        String utf_msg = "";
        try {
            gbk_msg = new String(msg,"GBK");
            utf_msg = new String(gbk_msg.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return gbk_msg;
    }

    /**
     * ISO8583 string?ASCII???UTF-8?GBK??
     */
    public  byte[] string2ASC(String msg)
    {
        byte[] gbk_msg = new byte[msg.length()];
        try {
            gbk_msg = msg.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return gbk_msg;
    }

    /**
     * ISO8583 byte???map??bitset
     */
    public BitSet byteArray2BitSet(byte[] bytes) {
        BitSet bitSet = new BitSet(bytes.length * 8);
        int index = 0;
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 7; j >= 0; j--) {
                bitSet.set(index++, (bytes[i] & (1 << j)) >> j == 1 ? true : false);
            }
        }
        return bitSet;
    }
    /**
     * ISO8583 bitset???map??byte
     */
    public byte[] bitSet2ByteArray(BitSet bitSet) {
        byte[] bytes = new byte[bitSet.size() / 8];
        for (int i = 0; i < bitSet.size(); i++) {
            int index = i / 8;
            int offset = 7 - i % 8;
            bytes[index] |= (bitSet.get(i) ? 1 : 0) << offset;
        }
        return bytes;
    }

}
