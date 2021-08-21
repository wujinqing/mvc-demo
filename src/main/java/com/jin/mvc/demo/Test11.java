package com.jin.mvc.demo;

import java.net.URLClassLoader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * @author wu.jinqing
 * @date 2021年08月03日
 */
public class Test11 {
    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader instanceof URLClassLoader) {
            String s =  Arrays.toString(((URLClassLoader) classLoader).getURLs());
            System.out.println(s);
        }

    }
}
