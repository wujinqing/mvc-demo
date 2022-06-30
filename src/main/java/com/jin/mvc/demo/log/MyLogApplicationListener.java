package com.jin.mvc.demo.log;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;

/**
 * @author wu.jinqing
 * @date 2021年10月28日
 */
@Order(LoggingApplicationListener.DEFAULT_ORDER -1)
public class MyLogApplicationListener implements ApplicationListener<ApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
//        System.setProperty(LoggingApplicationListener.CONFIG_PROPERTY, "self-config");
//        System.setProperty(LoggingSystem.SYSTEM_PROPERTY, "com.jin.mvc.demo.log.MyLogbackLoggingSystem");
    }
}
