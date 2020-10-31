package com.jin.mvc.demo.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 被@WebFilter注解修饰的Filter类必须实现Filter接口。
 * @author wu.jinqing
 * @date 2020年10月29日
 */
@WebFilter("/*")
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Before doFilter.");

        chain.doFilter(request, response);

        System.out.println("After doFilter.");
    }
}
