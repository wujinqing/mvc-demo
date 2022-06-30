package com.jin.mvc.demo;

import org.apache.catalina.connector.ResponseFacade;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author wu.jinqing
 * @date 2021年03月02日
 */
@WebFilter(asyncSupported = true)
public class SessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println(request.getClass());
        System.out.println(response.getClass());

        ResponseFacade responseFacade = (ResponseFacade)response;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;

        HttpServletRequest httpRequest = (HttpServletRequest)request;

        System.out.println(httpRequest.getRequestedSessionId());

//        httpServletResponse.addHeader("Set-Cookie", "JSESSIONID2=FEEEBED62E4934B47F21D717DE2F57FE; Max-Age=5000; Expires=Tue, 24-Aug-2021 13:00:44 GMT; Path=/; HttpOnly");
        // 获取或者创建session
        HttpSession session = httpRequest.getSession();
//        response.
        // 设置session失效时间，单位秒
//        session.setMaxInactiveInterval(3);
        chain.doFilter(request, response);
    }


    /**
     * JSESSIONID2=FEEEBED62E4934B47F21D717DE2F57FE; Max-Age=5000; Expires=Tue, 24-Aug-2021 13:00:44 GMT; Path=/; HttpOnly
     * Set-Cookie
     */

    /**
     *
     * @param name JSESSIONID2
     * @param sessionId FEEEBED62E4934B47F21D717DE2F57FE
     * @param maxAge 5000
     * @return JSESSIONID2=FEEEBED62E4934B47F21D717DE2F57FE; Max-Age=5000; Expires=Tue, 24-Aug-2021 13:00:44 GMT; Path=/; HttpOnly
     */
    public static String setCookie(String name, String sessionId, int maxAge)
    {

        String COOKIE_DATE_PATTERN = "EEE, dd-MMM-yyyy HH:mm:ss z";



        DateFormat df =
                new SimpleDateFormat(COOKIE_DATE_PATTERN, Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));

        String exp = df.format(new Date(System.currentTimeMillis() + 5000 * 1000L));

        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("=");
        sb.append(sessionId);
        sb.append("; Max-Age=");
        sb.append(maxAge);
        sb.append("; Expires=");
        sb.append(exp);
        sb.append("; Path=/; HttpOnly");

        return sb.toString();
    }
    /**
     * JSESSIONID2=FEEEBED62E4934B47F21D717DE2F57FE; Max-Age=5000; Expires=Tue, 24-Aug-2021 13:00:44 GMT; Path=/; HttpOnly
     * Set-Cookie
     * @param args
     */
    public static void main(String[] args) {

//        LocalDateTime.now()

        System.out.println(setCookie("JSESSIONID2", "FEEEBED62E4934B47F21D717DE2F57FE", 5000));
    }
}
