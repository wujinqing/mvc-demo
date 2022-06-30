package com.jin.mvc.demo;

import org.checkerframework.checker.units.qual.A;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wu.jinqing
 * @date 2021年12月29日
 */
public class Test20 {
    public static void main(String[] args) throws InterruptedException {
        int max = 789;
        final String key = "1";
        ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<>();
        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(max);

        for(int i = 0; i < max; i++)
        {
            new Thread(() -> {
                try {
                    latch1.await();
                    AtomicInteger count = new AtomicInteger(0);

                    AtomicInteger count1 = map.putIfAbsent(key, count);

                    AtomicInteger cnt = count1 != null ? count1 : count;

                    cnt.getAndIncrement();

                    latch2.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }).start();
        }

        latch1.countDown();
        latch2.await();
        System.out.println(map.get(key).get());
    }
}
