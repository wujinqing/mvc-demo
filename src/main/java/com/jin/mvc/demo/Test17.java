package com.jin.mvc.demo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wu.jinqing
 * @date 2021年12月29日
 */
public class Test17 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(2);
        executorService.execute(() -> {
            System.out.println("a");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
        });

        executorService.execute(() -> {
            System.out.println("B");
            latch.countDown();

        });

        System.out.println("c");
        latch.await();

    }
}
