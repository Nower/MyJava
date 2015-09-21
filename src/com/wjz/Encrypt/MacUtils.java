//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.Security;
//import java.security.spec.InvalidKeySpecException;
//
//import javax.crypto.Cipher;
//import javax.crypto.SecretKey;
//import javax.crypto.SecretKeyFactory;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//
//import org.apache.commons.codec.binary.Hex;
//
////bcprov-jdk16-146.jar
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//
///**
// * Created by lihao05 on 15/8/18.
// */
//public class MacUtils {
//
//    static{
//        Security.addProvider(new BouncyCastleProvider());
//    }
//
////    /**
////     * 使用加密机
////     */
////    public static void main(String[] args) {
////        //String srcStr = "testingSimulateEncryptMachineTxt";
////        String srcStr = args[0];
////        System.out.println("srcStr=" + srcStr);
////        byte[] srcByte = srcStr.getBytes();
////        String encStr = Hsm.getMab(srcByte, "D53C05F3F3A74750");
////        System.out.println("encStr=" + encStr);
////    }
//
//    /**
//     * 尝试加密机软实现
//     */
//    public static void main(String[] args) throws Exception {
//
//        //String srcStr = "testingSimulateEncryptMachineTxt";
//        String srcStr = "testingS";
//        String hexKey = "D53C05F3F3A74750";
//
////        //zak(mackey)
////        byte[] MACKEY = new byte[]{31, 31, 31, 31, 31, 31, 31, 31};
////        //待计算数据
////        byte[] data = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
//
//        //byte[] MACKEY = Hex.decodeHex(hexKey.toCharArray());
//        byte[] MACKEY = new byte[]{43, 60, 5, 13, 13, 89, 71, 80};
//        for(byte b : MACKEY) {
//            System.out.print(b);
//            System.out.print(" ");
//        }
//        System.out.println();
//        byte[] data = srcStr.getBytes();
//
//        //进行分组
//        int group = (data.length + (8 - 1)) / 8;
//
//        //偏移量
//        int offset = 0 ;
//        //输入计算数据
//        byte[] edata = null;
//        for(int i = 0 ; i < group; i++) {
//            byte[] temp = new byte[8];
//            if(i != group - 1) {
//                System.arraycopy(data, offset, temp, 0, 8);
//                offset += 8;
//            } else {
//                //只有最后一组数据才进行填充0x00
//                System.arraycopy(data, offset, temp, 0, data.length - offset);
//                for(byte b : temp) {
//                    System.out.print(b);
//                    System.out.print(" ");
//                }
//            }
//            if(i != 0){
//                //只有第一次不做异或
//                temp = XOR(edata,temp);
//            }
//            edata = desedeEn(MACKEY,temp);
//            //edata = desEnc(temp, MACKEY);
//        }
//        //System.out.println(java.util.Arrays.toString(byte2Int(edata)));
//        System.out.println();
//        System.out.println( String.valueOf(Hex.encodeHex(edata)).toUpperCase() );
//    }
//
//    public static void main(String[] args) throws Exception {
//
//        String a = "D53C05F3F3A74750";
//        byte[] b = a.getBytes();
//        for(byte tmp : b) {
//            System.out.print(tmp);
//            System.out.print(" ");
//        }
//
//        System.out.println("\n");
//
//        byte[] b1 = Hex.decodeHex(a.toCharArray());
//        for(byte tmp : b1) {
//            System.out.print(tmp);
//            System.out.print(" ");
//        }
//
//        System.out.println("\n");
//
//        byte[] b2 = BytesUtil.hexStringToBytes(a);
//        for(byte tmp : b2) {
//            System.out.print(tmp);
//            System.out.print(" ");
//        }
//
//    }
//
//    public static byte[] XOR(byte[] edata, byte[] temp) {
//        byte [] result = new byte[8];
//        for (int i = 0 , j = result.length ; i < j; i++) {
//            result [i] = (byte) (edata[i] ^ temp[i]);
//        }
//        return result;
//    }
//
//    public static byte[] desedeEn(byte[] key,byte[] data){
//        byte[] result = null;
//        try {
//            SecretKey secretKey = getSecretKeySpec(key);
//            Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding","BC");
//
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey,new IvParameterSpec(new byte[8]));
//            //初始化向量为0,即异或不改变原始数据
//
//            result = cipher.doFinal(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    private static SecretKey getSecretKeySpec(byte[] keyB) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("Des");
//        SecretKeySpec secretKeySpec = new SecretKeySpec(keyB,"Des");
//        return secretKeyFactory.generateSecret(secretKeySpec);
//    }
//
////    public static int[] byte2Int(byte[] data) {
////        int[] result = new int[data.length];
////
////        for(int i = 0; i < data.length; i++) {
////            if (data[i] < 0) {
////                result[i] = data[i] + 256;
////            } else {
////                result[i] = data[i];
////            }
////        }
////
////        return result;
////    }
//
//    private static byte[] desEnc(byte[] src, byte[] key) throws Exception {
//        SecureRandom random = new SecureRandom();
//        SecretKey secretKey = new SecretKeySpec(key, "DES");
//        Cipher cipher = Cipher.getInstance("DES");
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);
//        return cipher.doFinal(src);
//    }
//
//}