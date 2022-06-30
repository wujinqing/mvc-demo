package com.jin.mvc.demo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author wu.jinqing
 * @date 2021年12月29日
 */
public class Test18 {
    public static void main(String[] args) {

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime.toInstant().toString());
        System.out.println(zonedDateTime.toString());
        System.out.println(zonedDateTime.toLocalDateTime().toString());
//        DateTimeFormatter.ofPattern("").format()
    }
}
