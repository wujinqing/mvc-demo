package com.jin.mvc.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author wu.jinqing
 * @date 2021年08月11日
 */
@Component
public class MyEnvironmentAware implements EnvironmentAware {
    @Autowired
    private Environment config;
    public static Environment environment;
    @Override
    public void setEnvironment(Environment environment) {
        MyEnvironmentAware.environment = environment;
    }
}
