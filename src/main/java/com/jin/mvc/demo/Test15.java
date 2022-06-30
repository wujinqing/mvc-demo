package com.jin.mvc.demo;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executors;

/*

CompletionStage
各个阶段(stage)的表现形式可能是：Function(有入参，有返回值)、Consumer(有入参，没有返回值)、Runnable(没有入参，没有返回值)，
对应的相应的方法是以: apply、accept、run 开头

The computation performed by a stage may be expressed as a Function, Consumer, or Runnable
(using methods with names including apply, accept, or run, respectively)

 */
public class Test15 {
    public static void main(String[] args) throws Exception{
        for(int i = 0; i< 10; i++) {
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(Test15::getName);
            CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(Test15::getName2);


            CompletableFuture.anyOf(completableFuture, completableFuture2).thenAccept(s -> {
                System.out.println(s + "aa");
            });
        }

        Thread.sleep(5000);

    }

    public static String getName() {
        Random random = new Random();
        try {
            Thread.sleep(1000 + random.nextInt(1000));
        } catch (InterruptedException e) {
            throw new CompletionException(e);
        }
        return "zhang san1";
    }

    public static String getName2() {

        Random random = new Random();
        try {
            Thread.sleep(1000 + random.nextInt(1000));
        } catch (InterruptedException e) {
            throw new CompletionException(e);
        }
        return "zhang san2";
    }
}

