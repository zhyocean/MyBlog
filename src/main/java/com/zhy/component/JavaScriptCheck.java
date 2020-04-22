package com.zhy.component;

/**
 * @author: zhangocean
 * @Date: 2018/12/28 12:37
 * Describe:
 */
public class JavaScriptCheck {

    public static String javaScriptCheck(String comment){

        comment = textCheck(comment, "script");
        comment = textCheck(comment, "iframe");

        comment = textCheck(comment, "button");
        comment = textCheck(comment, "a");

        comment = textCheck(comment, "img");

        return comment;
    }

    private static String textCheck(String comment, String sign){
        String signFir = "<" + sign;
        String signSec = "</" + sign + ">";
        int begin,end,theEnd;
        String newStr = "";
        begin = comment.indexOf(signFir);
        end = comment.indexOf(signSec);
        if (begin == -1){
            return comment;
        }
        while (begin != -1){
            theEnd = comment.indexOf(">");
            newStr += comment.substring(0, begin);
            newStr += "[removed]" + comment.substring(theEnd+1,end) + "[removed]";

            comment = comment.substring(end+3+sign.length());

            begin = comment.indexOf(signFir);
            end = comment.indexOf(signSec);
        }
        return newStr+comment;
    }
}
