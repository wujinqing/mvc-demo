package com.jin.mvc.demo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author wu.jinqing
 * @date 2022年06月16日
 */
public class Test26 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("M05","M50","M20","M10");

        list.sort(Comparator.naturalOrder());

        list.forEach(System.out::println);
    }
}
