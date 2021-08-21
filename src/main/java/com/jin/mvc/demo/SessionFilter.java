package com.jin.mvc.demo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author wu.jinqing
 * @date 2021年03月02日
 */
@WebFilter
public class SessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;

        // 获取或者创建session
        HttpSession session = httpRequest.getSession();

        // 设置session失效时间，单位秒
//        session.setMaxInactiveInterval(3);
        chain.doFilter(request, response);
    }
}
