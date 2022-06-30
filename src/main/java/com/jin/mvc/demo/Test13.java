package com.jin.mvc.demo;

import org.checkerframework.checker.units.qual.A;

import java.math.BigDecimal;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author wu.jinqing
 * @date 2021年09月06日
 */
public class Test13 {
    private AtomicReference reference = new AtomicReference();

    public static void main(String[] args) {
        System.out.println(formatBigDecimal(new BigDecimal("134422100.0000")));
    }

    public static String formatBigDecimal(BigDecimal price)
    {
        if(price == null)
        {
            return "0";
        }

        String s = String.valueOf(price.doubleValue());

        System.out.println(s);
        if(s.endsWith(".0"))
        {
            return s.replace(".0", "");
        }

        return s;
    }
}
