package com.zhy.component;

import org.springframework.stereotype.Component;

/**
 * @author: zhangocean
 * @Date: 2018/6/24 14:59
 * Describe: 字符串与字符串数组之间的转换
 */
@Component
public class StringAndArray {

    /**
     * 字符串转换成字符串数组
     * @param str 字符串
     * @return 转换后的字符串数组
     */
    public static String[] stringToArray(String str){
        String[] array = str.split(",");
        return array;
    }

    /**
     * 字符串数组拼接成字符串
     * @param articleTags 字符串数组
     * @return 拼接后的字符串
     */
    public static String arrayToString(String[] articleTags){
        String buffered = "";
        for(String s : articleTags){
            if("".equals(buffered)){
                buffered +=  s;
            } else {
                buffered += "," + s;
            }
        }
        return buffered;
    }

}
