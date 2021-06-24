package com.jin.mvc.demo.jcifs;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author wu.jinqing
 * @date 2021年04月19日
 */
public class Test2 {
    public static void main(String[] args) {
        long s = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        System.out.println(s);
        long s2 = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).toEpochSecond(ZoneOffset.of("+8"));
        System.out.println(s2);
    }
}
