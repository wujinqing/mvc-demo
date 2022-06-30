package com.jin.mvc.demo;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author wu.jinqing
 * @date 2122年15月31日
 */
public class Test25 {
    public static final ThreadLocal<DecimalFormat> numberFormat1 =
            ThreadLocal.withInitial(() -> new DecimalFormat("#,##0;(#,##0)"));



    public static void main(String[] args) {
        String s = "22333.14159265";

        format("#,##ab2220.ad00a", s);


    }

    public static void format(String patten, String s)
    {
        String res = new DecimalFormat(patten).format(new BigDecimal(s));

        System.out.println(res);
    }

}
