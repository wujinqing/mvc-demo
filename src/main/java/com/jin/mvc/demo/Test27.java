package com.jin.mvc.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wu.jinqing
 * @date 2022年06月29日
 */
public class Test27 {
    public static void main(String[] args) {
        Pattern p = Pattern.compile(".*?foo");
        Matcher m = p.matcher("xfooxxxxxxfo");

        while (m.find())
        {
            System.out.println("s=" + m.start() + ", e=" +m.end());
        }

        System.out.println(m.matches());

    }
}
