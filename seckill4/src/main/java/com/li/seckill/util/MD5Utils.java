package com.li.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
public class MD5Utils {

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt ="1b2b3b4b";

    public static String inputPassToFormPass(String inputPass){
        String str=""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);

        return md5(str);
    }

    public static String formPassToDBPass(String formPass,String salt){
        String str=""+salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);

        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass,String dbSalt){
        String formPass=inputPassToFormPass(inputPass);
        String dbPass=formPassToDBPass(formPass, dbSalt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));
        //System.out.println(formPassToDBPass("f3d09231f41dda70ad45f7862ea4fbd1", "a8c9d8234a"));
        System.out.println(inputPassToDBPass("123456", "a8c9d8234a"));
    }
}
