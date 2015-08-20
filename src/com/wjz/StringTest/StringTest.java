package com.wjz.StringTest;

import java.math.BigDecimal;

/**
 * Created by wujiazhi on 15/7/21.
 */
public class StringTest {

    public static void main(String [] args){

//        System.out.println(getMacValue("中    a ! b  。.b  b  国"));
//        test(1);
        testMoney("12");
    }

    public static void testMoney(String money){
        BigDecimal bigmoney = new BigDecimal(money);
        BigDecimal mul = new BigDecimal(100);
        int intmoney = bigmoney.multiply(mul).intValue();
        System.out.println(String.valueOf(intmoney));
    }

    public static void test(int len){
        if(len >= 10000){
            System.out.println("Fuck!");
            return;
        }

        char [] msgLen= {'0', '0', '0', '0'};
        int i = 3;
        while((len/10) != 0){
            msgLen[i--] = (char)(len % 10 + '0');
            len /= 10;
        }
        msgLen[i] = (char)(len % 10 + '0');
        System.out.println(msgLen);
    }

    public static void testTrim(String str){
        System.out.println(str.trim());
    }

    private static String getMacValue(String value){
        StringBuffer res = new StringBuffer();
        if(value == null || value.length() < 1){
            return "";
        }

        char[] arrs = value.toUpperCase().trim().toCharArray();
        boolean hasBlank = false;
        for(int i=0; i<arrs.length; i++){
            if((arrs[i]>='A' && arrs[i]<='Z') || (arrs[i]>='0' && arrs[i]<='9') || (arrs[i] == '.') || (arrs[i] == ',')){
                res.append(arrs[i]);
                hasBlank = false;
            }else if((arrs[i] == ' ') && !hasBlank){
                res.append(' ');
                hasBlank = true;
            }
        }

        return res.toString().trim();
    }
}
