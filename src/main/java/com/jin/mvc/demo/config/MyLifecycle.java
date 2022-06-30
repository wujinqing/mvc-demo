package com.jin.mvc.demo.config;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author wu.jinqing
 * @date 2021年10月27日
 */
@Component
public class MyLifecycle implements SmartLifecycle {
    private AtomicBoolean isRunning = new AtomicBoolean(false);

    @Override
    public void start() {
        if(isRunning.compareAndSet(false, true))
        {
            System.out.println("start");
        }


    }

    @Override
    public void stop() {
        isRunning.compareAndSet(true, false);
        System.out.println("stop");

    }

    @Override
    public boolean isRunning() {
        System.out.println("isRunning");
        return isRunning.get();
    }
}
