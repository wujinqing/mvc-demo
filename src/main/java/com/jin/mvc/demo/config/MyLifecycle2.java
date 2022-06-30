package com.jin.mvc.demo.config;

import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

/**
 * @author wu.jinqing
 * @date 2021年10月27日
 */
@Component
public class MyLifecycle2 implements Lifecycle {
    @Override
    public void start() {
        System.out.println("MyLifecycle2 start");
    }

    @Override
    public void stop() {
        System.out.println("MyLifecycle2 stop");
    }

    @Override
    public boolean isRunning() {
        System.out.println("MyLifecycle2 isRunning");
        return true;
    }
}
