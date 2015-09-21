package com.wjz.test;

/**
 * Created by wujiazhi on 15/9/2.
 */
public class JudgeSign {
    public static void main(String[] args){

        int [] arr = {17, 465, 224, 209, 16, 464, 208, 213, 212};
        for(int i=0; i<arr.length; i++){
            test(arr[i]);
        }
    }

    public static void test(int attr){
        boolean res = ((attr&4) == 0);
        System.out.println(attr + ":" + res);
    }
}
