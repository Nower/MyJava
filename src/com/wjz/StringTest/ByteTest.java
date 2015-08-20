package com.wjz.StringTest;

/**
 * Created by wujiazhi on 15/8/7.
 */
public class ByteTest {


    public static void main(String [] args) throws Exception{
//        System.out.println(byte2String(getGBKByte("1421182751256516")));
//        gbkHexStr2String("31343231313832373531323536353136c9c8e32a");
        System.out.println(byte2String(hex2Byte("16")));

    }

    public static void gbkHexStr2String(String str) throws Exception{
        byte[] bytes = hex2Byte(str);
        String gbkStr = new String(bytes, "GBK");
        System.out.println(gbkStr);
    }


    public static byte[] getGBKByte(String str) throws Exception{
        return str.getBytes("GBK");
    }

    /**
     * byte转string，ISO8583数字处理
     */
    public static String byte2String(byte[] cardno)
    {
        String cardnostring = "";
        for (int i = 0; i < cardno.length; i++) {
            String charcode = Integer.toHexString(cardno[i]);
            // byte存储小于10的数组
            if (charcode.length() == 1) {
                charcode = "0"+charcode;
                // 存储大于0x80的数字转换为字符串是ffffff**
            } else if (charcode.length() == 8) {
                charcode = charcode.substring(6);
            }
            cardnostring = cardnostring + charcode;
        }

        return cardnostring;
    }

    public static byte[] hex2Byte(String cardno)
    {
        int cardlength = cardno.length();
        if (cardlength % 2 ==1) {
            cardlength = cardlength+1;
            cardno = "0" + cardno;   // 前补0
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
        for(int i=0; i<cardbyte.length; i++){
            System.out.println(cardbyte[i]);
        }
        return cardbyte;
    }
}
