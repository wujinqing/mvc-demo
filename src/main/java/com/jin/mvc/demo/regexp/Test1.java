package com.jin.mvc.demo.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * https://docs.oracle.com/javase/tutorial/essential/regex/intro.html
 * 元字符：
 * <([{\^-=$!|]})?*+.>
 *
 * @author wu.jinqing
 * @date 2022年06月29日
 */
public class Test1 {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("\\d");
        Matcher m = p.matcher("2");

        System.out.println(m.matches());

    }
}
