package com.wjz.CharsetAndEncoding;


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Created by wujiazhi on 15/6/17.
 */
public class Encoding {
    public static void main(String[] args) throws Exception{
//        charsetTest();
//        charTest();
        testBytes();
    }

    /**
     * 关于String
     * getBytes();
     * -Dfile.encoding
     */
    public static void stringTest() throws Exception{

        String str = new String("stringTest");
        str.getBytes();
        str.getBytes("GBK");
    }

    public static void printEncoding(String charset) throws Exception{
        System.out.println("a(" + charset + ")    ：" + "a".getBytes(charset).length);
        System.out.println("aa(" + charset + ")    ：" + "aa".getBytes(charset).length);
        System.out.println("啊(" + charset + ")   ：" + "啊".getBytes(charset).length);
        System.out.println("啊啊(" + charset + ") ：" + "啊啊".getBytes(charset).length);
        System.out.println("");
    }

    /**
     * Unicode和UTF-16：1个字符占2个字节（不管是哪国语言）,有些扩展字符Unicode码>=0x10000，UTF-16就会使用4个字节
     * UTF-8：1个英文字符占1个字节，一个汉字（包括日文和韩文等）占3个字节,有些扩展字符Unicode码>=0x10000，UTF-8就会使用4个字节
     * Java中的char默认采用Unicode编码，所以Java中char占2个字节(只能说大多情况下吧，字符Unicode码一般不超过0x10000)
     * GBK下，英文一个字节，中文两个字节
     * @throws Exception
     */
    public static void charTest() throws Exception{
        /**
         * a(Unicode)    ：4(默认加上了BOM头大端方式，fe ff，占了两个字节，所以其实还是2个字节用在字符上)
         * aa(Unicode)    ：6（默认加上了BOM头，fe ff）
         * 啊(Unicode)   ：4（默认加上了BOM头，fe ff）
         * 啊啊(Unicode) ：6（默认加上了BOM头，fe ff）
         */
        printEncoding("Unicode");
        /**
         * Unicode大端，输出前加上BOM头fe ff
         * a(UnicodeBig)    ：4
         * aa(UnicodeBig)    ：6
         * 啊(UnicodeBig)   ：4
         * 啊啊(UnicodeBig) ：6
         */
        printEncoding("UnicodeBig");
        /**
         * Unicode小端，输出前加上BOM头ff fe
         * a(UnicodeLittle)    ：4
         * aa(UnicodeLittle)    ：6
         * 啊(UnicodeLittle)   ：4
         * 啊啊(UnicodeLittle) ：6
         */
        printEncoding("UnicodeLittle");
        /**
         * 大端方式制定未加BOM头
         * a(UnicodeBigUnmarked)    ：2
         * aa(UnicodeBigUnmarked)    ：4
         * 啊(UnicodeBigUnmarked)   ：2
         * 啊啊(UnicodeBigUnmarked) ：4
         */
        printEncoding("UnicodeBigUnmarked");
        /**
         * 小端方式指定未加BOM头
         * a(UnicodeLittleUnmarked)    ：2
         * aa(UnicodeLittleUnmarked)    ：4
         * 啊(UnicodeLittleUnmarked)   ：2
         * 啊啊(UnicodeLittleUnmarked) ：4
         */
        printEncoding("UnicodeLittleUnmarked");
        /**
         * UTF-8没有大端小端之分，所以不需要加上BOM头
         * a(UTF-8)    ：1
         * aa(UTF-8)   ：2
         * 啊(UTF-8)   ：3
         * 啊啊(UTF-8) ：6
         */
        printEncoding("UTF-8");
        /**
         * UTF-16有大端小端之分，默认是大端，fe ff
         * a(UTF-16)    ：4（和Unicode一样，默认加上了BOM头输出）
         * aa(UTF-16)   ：6
         * 啊(UTF-16)   ：4
         * 啊啊(UTF-16) ：6
         */
        printEncoding("UTF-16");
        /**
         * 指定了大端，但是未加BOM
         * a(UTF-16BE)    ：2
         * aa(UTF-16BE)   ：4
         * 啊(UTF-16BE)   ：2
         * 啊啊(UTF-16BE) ：4
         */
        printEncoding("UTF-16BE");
        /**
         * 指定了小端，但是未加BOM
         * a(UTF-16LE)    ：2
         * aa(UTF-16LE)   ：4
         * 啊(UTF-16LE)   ：2
         * 啊啊(UTF-16LE) ：4
         */
        printEncoding("UTF-16LE");
        /**
         * a(GBK)    ：1
         * aa(GBK)   ：2
         * 啊(GBK)   ：2
         * 啊啊(GBK) ：4
         */
        printEncoding("GBK");
        /**
         * UTF-32其实也由BOM，但是效果显示好像默认是不带BOM头
         * a(UTF-32)    ：4
         * aa(UTF-32)   ：8
         * 啊(UTF-32)   ：4
         * 啊啊(UTF-32) ：8
         */
        printEncoding("UTF-32");
        /**
         * a(UTF-32BE)    ：4
         * aa(UTF-32BE)   ：8
         * 啊(UTF-32BE)   ：4
         * 啊啊(UTF-32BE) ：8
         */
        printEncoding("UTF-32BE");
        /**
         * a(UTF-32LE)    ：4
         * aa(UTF-32LE)   ：8
         * 啊(UTF-32LE)   ：4
         * 啊啊(UTF-32LE) ：8
         */
        printEncoding("32LE");
    }

    public static void testBytes() throws UnsupportedEncodingException {
        String str = "中国";
        String[] encoding = {
                "Unicode",
                "UnicodeBig",
                "UnicodeLittle",
                "UnicodeBigUnmarked",
                "UnicodeLittleUnmarked",
                "UTF-8",
                "UTF-16",
                "UTF-16BE",
                "UTF-16LE",
//                "UTF-16BE-BOM",//没有这种类型
//                "UTF-16LEBOM",//没有这种类型
                "GBK",
                "UTF-32",
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
         * 可以看出UTF-16默认是大端，但是需要加上BOM头；而UTF-32默认也是大端，但是不需要加上BOM头
         *
         * 关于大端小端的方式：
         * 1、正常人看的顺序就是大端，比如0x1234表示大端，0x3412就是小端表示，网络字节顺序就是大端的；
         * 2、小端就是以相反的顺序，但是有一个单位进行反序，以UTF-16LE为例：
         * ——》”中国“用大端方式是4e 2d(中) 56 fd（国）
         * ——》反序就是单个字符（UTF-16字符占两个字节）内以单个字节为单位反序，对应了 2d 4e(中)fd 56(国)；
         * ——》所以，UTF-32（一个字符占4个字节）的小端形式，就是4个字节的反序。
         *
         */
        for (int i = 0; i < encoding.length; i++) {
            System.out.printf("%-22s %s%n", encoding[i], bytes2HexString(str.getBytes(encoding[i])));
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
        }
        return new String(chs);
    }

    /**
     * 关于ByteBuffer
     */
    public static void bytebufferTest(){

    }

    /**
     * 关于File
     */
    public static void fileTest(){

    }

    /**
     * 关于Charset
     * Charset.defaultCharset()
     */
    public static void charsetTest() throws Exception{
        String s = Charset.defaultCharset().displayName();
        String s1 = "我喜欢你,My Love";

        ByteBuffer bb1 = ByteBuffer.wrap(s1.getBytes("UTF-8"));

        for(byte bt:bb1.array()){
            System.out.printf("%x",bt);
        }
        //char[]用法
        char[] chArray={'I','L','o','v','e','你'};

        //CharBuffer用法
        CharBuffer cb = CharBuffer.wrap(chArray);
        //重新定位指针
        cb.flip();

        String s2= new String(chArray);

        //ByteBuffer用法
        ByteBuffer bb2 = Charset.forName("utf-8").encode(cb);

        // 利用Charset编码为指定字符集

        ByteBuffer bb3 = Charset.forName("utf-8").encode(s1);

        byte [] b   = bb3.array() ;

        // 利用Charset按指定字符集解码为字符串
        ByteBuffer bb4= ByteBuffer.wrap(b);

        String s4 = Charset.forName("utf-8").decode(bb4).toString();
    }

}
