package com.jin.mvc.demo.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 被@WebListener注解修饰的Listener必须实现一个或者多个以下接口:
 *
 javax.servlet.http.HttpSessionAttributeListener,
 javax.servlet.http.HttpSessionListener,
 javax.servlet.ServletContextAttributeListener,
 javax.servlet.ServletContextListener,
 javax.servlet.ServletRequestAttributeListener,
 javax.servlet.ServletRequestListener,
 javax.servlet.http.HttpSessionIdListener
 *
 * @author wu.jinqing
 * @date 2020年10月29日
 */
@WebListener
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("MyListener");
    }
}
