package com.li.seckill.util;

import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern=Pattern.compile("1\\d{10}");

    public static boolean isMobile(String src){
        if(StringUtils.isEmpty(src)){
            return false;
        }
        Matcher m=mobile_pattern.matcher(src);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(isMobile("1892344321"));
        System.out.println(isMobile("18923443201"));
    }
}
