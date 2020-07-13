package com.superzmz.www.tool;


public class TextUtils {
    public static boolean isEmpty(String text){
        if ("".equals(text) || null == text) {
            return true;
        }
        return false;
    }
}