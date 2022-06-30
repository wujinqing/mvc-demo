package com.jin.mvc.demo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author wu.jinqing
 * @date 2022年02月17日
 */
public class Test21 {
    public static void main(String[] args) {
        String s = "1.0";

        if(s.endsWith(".0"))
        {
            s = s.replace(".0", "");
        }

        System.out.println(s);
    }
}
