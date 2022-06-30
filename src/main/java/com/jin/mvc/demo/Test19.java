package com.jin.mvc.demo;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wu.jinqing
 * @date 2021年12月29日
 */
public class Test19 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        list.add(null);

        System.out.println(JSON.toJSONString(list));
    }
}
