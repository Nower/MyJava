package com.wjz.test;

/**
 * Created by wujiazhi on 15/6/15.
 */
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

class Test {
    public static void main(String[] args) {
//        System.out.println("Default Charset=" + Charset.defaultCharset());
//        System.out.println("file.encoding=" + System.getProperty("file.encoding"));
//        System.out.println("Default Charset=" + Charset.defaultCharset());
//        System.out.println("Default Charset in Use=" + getDefaultCharSet());

        testException();
    }

    private static String getDefaultCharSet() {
        OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());
        String enc = writer.getEncoding();
        return enc;
    }


    public static void testException(){
        Integer a = null;
        try {
            int b = (int) a;
        }catch (Exception e){

        }
        for(int i=0; i<10; i++){
            try {
                System.out.println(i + ":" + (int) a);
            }catch (Exception e){
                System.out.println("X");
            }
        }
    }
}