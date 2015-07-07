package com.wjz.StreamTest;

import java.io.*;

/**
 * Created by wujiazhi on 15/6/27.
 */
public class FileStream {

    public static void main(String [] args) throws Exception{

    }

    public FileStream() {
    }

    /**
     * FileReader extends InputStreamReader extends Reader
     * FileWriter extends OutputStreamWriter extends Writer
     * FileWriter没有指定字符集，而OutputStreamWriter可以指定字符集
     * public OutputStreamWriter(OutputStream out, String charsetName)
     throws UnsupportedEncodingException
     {
     super(out);
     if (charsetName == null)
     throw new NullPointerException("charsetName");
     se = StreamEncoder.forOutputStreamWriter(out, this, charsetName);
     }
     *
     * /**
     * Writer中的缓冲区；所以需要close()或flush()才能将内存数据真正写入文件中。
     * Temporary buffer used to hold writes of strings and single characters
     * private char[] writeBuffer;
     *
     * @throws Exception
     */
    public static void testFileReaderWriter() throws Exception{
        File file = new File("");
        FileReader fr = new FileReader(file);

//        Reads a single character.
//        * @return The character read, or -1 if the end of the stream has been reached
        //StreamDecoder.read();将字节流以指定字符集格式解码成字符char(UTF-16)读取出来
        /**
         * private int read0() throws IOException {
         116           synchronized (lock) {
         117
         118               // Return the leftover char, if there is one
         119               if (haveLeftoverChar) {
         120                   haveLeftoverChar = false;
         121                   return leftoverChar;
         122               }
         123
         124               // Convert more bytes
         125               char cb[] = new char[2];
         126               int n = read(cb, 0, 2);
         127               switch (n) {
         128               case -1:
         129                   return -1;
         130               case 2:
         131                   leftoverChar = cb[1];
         132                   haveLeftoverChar = true;
         133                   // FALL THROUGH
         134               case 1:
         135                   return cb[0];
         136               default:
         137                   assert false : n;
         138                   return -1;
         139               }
         140           }
         141       }
         */

        int a = fr.read();

        FileWriter fw = new FileWriter(file);
        /**
         * fw.write(int);——》StreamEncoder.write(int);(StreamEncoder extends Writer )
         *
         * public void write(int c) throws IOException {
         122     char cbuf[] = new char[1];
         123     cbuf[0] = (char) c;
         124     write(cbuf, 0, 1);
         125     }
         *
         *
         *
         * fw.write(char []);
         * fw.write(String);——》Writer.write(char []);——》OutputStreamWriter.write(char[]);
         * ——》StreamEncoder.write(char[]);——》OutputStream.write(byte[]);
         * StreamEncoder将字符char(UTF-16)编码成执行字符集的二进制流后，使用OutputStream输出。
         */
        fw.write(a);
        fw.write("d");
    }

    /**
     * SocketOutputStream extends FileOutputStream extends OutputStream
     * @throws Exception
     */
    public static void testFileOutputStream() throws Exception{

        File file = new File("");
        OutputStream os = new FileOutputStream(file);
    }
}
