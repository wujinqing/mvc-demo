package com.jin.mvc.demo.config;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 被@WebServlet注解修饰的Servlet类必须继承HttpServlet。
 *
 * @author wu.jinqing
 * @date 2020年10月29日
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("MyServlet");
    }
}
