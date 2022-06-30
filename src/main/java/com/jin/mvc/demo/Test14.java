package com.jin.mvc.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jin.mvc.demo.beans.Student;

/**
 * @author wu.jinqing
 * @date 2021年10月28日
 */
public class Test14 {
    public static void main(String[] args) {

        String s = "BACK SPACING:180°";

        String[] arr = s.split(":", 2);


        System.out.println(arr.length);
    }
}
