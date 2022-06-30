package com.jin.mvc.demo;

import com.google.common.collect.EvictingQueue;

/**
 * @author wu.jinqing
 * @date 2021年08月25日
 */
public class Test12 {
    public static void main(String[] args) {
        EvictingQueue<String> queue = EvictingQueue.create(2);

        queue.add("a");
        queue.add("b");
        queue.add("c");
        queue.add("d");

        queue.forEach(System.out::println);
    }
}
