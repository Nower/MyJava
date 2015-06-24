package com.wjz.test;

/**
 * Created by wujiazhi on 15/6/23.
 */
public class SwitchTest {

    public static final int a = 1;
    public static final int b = 2;

    public static void main(String[] args){
        int t = 0;
        switch (t){
            case a:
                System.out.println(a);
                break;
            case b:
                System.out.println(b);
                break;
        }
    }

}
