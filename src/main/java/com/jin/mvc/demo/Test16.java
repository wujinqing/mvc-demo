package com.jin.mvc.demo;

import org.checkerframework.checker.units.qual.A;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wu.jinqing
 * @date 2021年12月08日
 */
public class Test16 {
    public static void main(String[] args) {
        /*
        int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 60, TimeUnit.MINUTES, new LinkedBlockingQueue<>(13));
        AtomicInteger amt = new AtomicInteger(1);
        AtomicInteger amt2 = new AtomicInteger(1);
        for(int i = 0; i < 10; i++)
        {
            System.out.println("提交任务:"+amt2.getAndIncrement());
            try {
                threadPoolExecutor.submit(() -> {

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程名称:" + Thread.currentThread().getName() + ", amt:" + amt.getAndIncrement());
                });
            }catch (Exception e)
            {
                System.out.println("提交失败");
                e.printStackTrace();
            }

        }
    }
}
