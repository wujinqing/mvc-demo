package com.jin.mvc.demo.ni;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * ISO 7064:1983.MOD11-2校验码
 *
 * 关于身份证的校验算法问题
 * 身份证15位升级到18位，原来年用2位且没有最后一位，从左到右方分别表示
 * ①1-2 升级行政区代码
 * ②3-4 地级行政区划分代码
 * ③5-6 县区行政区分代码
 * ④7-10 11-12 13-14 出生年、月、日
 * ⑤15-17 顺序码，同一地区同年、同月、同日出生人的编号，奇数是男性，偶数是女性
 * ⑥18 校验码，如果是0-9则用0-9表示，如果是10则用X（罗马数字10）表示
 * ISO 7064:1983.MOD 11-2校验码
 *   ∑(a[i]*W[i]) mod 11 ( i = 18，17,16 ... 2 ) 最后一位是校验位 1
 *
 *   "*" 表示乘号
 *   i--------表示身份证号码每一位的序号，从右至左，最左侧为18，最右侧为1。
 *   a[i]-----表示身份证号码第 i 位上的号码
 *   W[i]-----表示第 i 位上的权值 W[i] = 2^(i-1) mod 11  【这里得到的序列是 7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2】
 *   计算公式 (1) 令结果为 R
 *
 *   根据下表找出 R 对应的校验码即为要求身份证号码的校验码C。
 *   R 0 1 2 3 4 5 6 7 8 9 10
 *   C 1 0 X 9 8 7 6 5 4 3 2
 *   由此看出 X 就是 10，罗马数字中的 10 就是 X
 *
 * 1、将前面的身份证号码17位数分别乘以不同的系数。第i位对应的数为[2^(18-i)]mod11。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 ；
 * 2、将这17位数字和系数相乘的结果相加；
 * 3、用加出来和除以11，看余数是多少？；
 * 4、余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3 2；
 * 5、通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2；
 *
 * 0 1 2 3 4 5 6 7 8 9 10
 * 1 0 X 9 8 7 6 5 4 3 2
 *
 * @author wu.jinqing
 * @date 2021年09月16日
 */
public class Test1 {
    private static final int[] factors = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final String[] checkcode = new String[]{"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
    private static final String idno = "360733199404108324";

    public static void main(String[] args) {
        int sum = 0;
        for(int i = 0; i < 17; i++)
        {
            sum += Integer.parseInt(idno.substring(i, i+1)) * factors[i];
        }

        int mod = sum % 11;

        System.out.println(checkcode[mod]);
    }
}