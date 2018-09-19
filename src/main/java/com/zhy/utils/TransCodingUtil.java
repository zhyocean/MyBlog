package com.zhy.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author: zhangocean
 * @Date: 2018/6/22 21:17
 * Describe: 转码工具
 */
public class TransCodingUtil {

    /**
     * 中文转unicode编码
     * @param gbString 汉字
     * @return unicode编码
     */
    public static String stringToUnicode(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    /**
     * unicode编码转中文
     * @param dataStr unicode编码
     * @return 中文
     */
    public static String unicodeToString(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            // 16进制parse整形字符串。
            char letter = (char) Integer.parseInt(charStr, 16);
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }

    /**
     * 将utf-8展开的16进制数转换成utf-8汉字
     * @param strUtf16
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String utf16ToUtf8(String strUtf16) throws UnsupportedEncodingException {
        String strUtf8 = URLDecoder.decode(strUtf16, "UTF-8");
        return strUtf8;
    }
    /**
     * 判断是否为汉字
     * @param str
     * @return
     */
    public static boolean isChinese(String str){
        for(int i=0;i<str.length();i++){
            int char1 = str.charAt(i);
            //汉字范围
            if(char1>=19968 && char1<=171941){
                return true;
            }
        }
        return false;
    }

//    /**
//     * 汉字转16进制
//     * @param s 汉字
//     * @return 汉字的16进制
//     */
//    public static String stringToHex16(String s){
//        System.out.println("汉字转16进制-------汉字：" + s);
//        if (s == null || s.equals("")) {
//            return null;
//        }
//        StringBuffer sb = new StringBuffer();
//        try {
//            char c;
//            for (int i = 0; i < s.length(); i++) {
//                c = s.charAt(i);
//                if (c >= 0 && c <= 255) {
//                    sb.append(c);
//                } else {
//                    byte[] b;
//                    b = Character.toString(c).getBytes("utf-8");
//                    for (byte aB : b) {
//                        int k = aB;
//                        if (k < 0) {
//                            k += 256;
//                        }
//                        sb.append(Integer.toHexString(k).toUpperCase());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("汉字转16进制-------16进制：" + sb.toString());
//        return sb.toString();
//    }
//
//    /**
//     * 16进制转汉字
//     * @param s 汉字的16进制
//     * @return 汉字
//     */
//    public static String hex16ToString(String s){
//        System.out.println("16进制转汉字-------16进制：" + s);
//        if (s == null || s.equals("")) {
//            return null;
//        }
//
//        try {
//            s = s.toUpperCase();
//            int total = s.length() / 2;
//            int pos = 0;
//            byte[] buffer = new byte[total];
//            for (int i = 0; i < total; i++) {
//                int start = i * 2;
//                buffer[i] = (byte) Integer.parseInt(
//                        s.substring(start, start + 2), 16);
//                pos++;
//            }
//            System.out.println("16进制转汉字-------汉字：" + new String(buffer, 0, pos, "UTF-8"));
//            return new String(buffer, 0, pos, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        System.out.println("16进制转汉字-------汉字：" + s);
//        return s;
//    }

}
