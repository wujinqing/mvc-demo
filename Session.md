## Session
> 配置类：org.springframework.boot.web.servlet.server.Session, org.springframework.boot.web.servlet.server.Session.Cookie

> session实际上是通过cookie来实现的.

> 请求头: Cookie: JSESSIONID=E19143133B4F982E89E773BE4654F29D

> 响应头: Set-Cookie: JSESSIONID=BAC29CF998DC66C9AC230B47DBA2654F; Max-Age=5; Expires=Wed, 03-Mar-2021 05:24:47 GMT; Path=/; HttpOnly

### 配置项
|配置项|说明|示例|
|---|---|---|
|server.servlet.session.timeout|session在服务器端保存的时间|单位：秒，默认值是30分钟|
|server.servlet.session.cookie.max-age|session在客户端(浏览器)保存的时间(session实际上是通过cookie来实现的)|单位：秒，没有默认值，默认永久保存|

### 设置session超时时间的方式
> session超时时间默认是30秒
```
org.apache.catalina.core.StandardContext.sessionTimeout

    /**
     * The session timeout (in minutes) for this web application.
     */
    private int sessionTimeout = 30;
    
```
#### 1.通过server.servlet.session.timeout=秒, 设置tomcat的Context的setSessionTimeout，不能小于1分钟
> 最终通过这个方法设置org.apache.catalina.Context.setSessionTimeout()

> server.servlet.session.timeout的默认值是30分钟，在源码里面写死了。
```
org.apache.catalina.Context.setSessionTimeout

org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory.configureSession

private void configureSession(Context context) {
		long sessionTimeout = getSessionTimeoutInMinutes();
		context.setSessionTimeout((int) sessionTimeout);
		Boolean httpOnly = getSession().getCookie().getHttpOnly();
		if (httpOnly != null) {
			context.setUseHttpOnly(httpOnly);
		}
		if (getSession().isPersistent()) {
			Manager manager = context.getManager();
			if (manager == null) {
				manager = new StandardManager();
				context.setManager(manager);
			}
			configurePersistSession(manager);
		}
		else {
			context.addLifecycleListener(new DisablePersistSessionListener());
		}
	}
	
org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory.getSessionTimeoutInMinutes

private long getSessionTimeoutInMinutes() {
		Duration sessionTimeout = getSession().getTimeout();
		if (isZeroOrLess(sessionTimeout)) {
			return 0;
		}
		// 如果设置的超时时间小于1分钟，则忽略，用1分钟代替
		return Math.max(sessionTimeout.toMinutes(), 1);
	}

```
#### 2.通过代码方式设置(优先级比方式1高)
```
public class SessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;

        // 获取或者创建session
        HttpSession session = httpRequest.getSession();
        // 设置session失效时间，单位秒
        session.setMaxInactiveInterval(3);
        chain.doFilter(request, response);
    }
}
```

### 设置session的cookie超时时间

#### 1.通过server.servlet.session.cookie.max-age=秒，设置。



### 获取session或者创建session

```
com.jin.mvc.demo.SessionFilter.doFilter

@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;

        // 获取或者创建session
        HttpSession session = httpRequest.getSession();
        // 设置session失效时间，单位秒
        session.setMaxInactiveInterval(3);
        chain.doFilter(request, response);
    }


```

### 获取session、校验session是否有效
```
org.apache.catalina.authenticator.AuthenticatorBase.invoke

public void invoke(Request request, Response response) throws IOException, ServletException {

        if (log.isDebugEnabled()) {
            log.debug("Security checking request " + request.getMethod() + " " +
                    request.getRequestURI());
        }

        // Have we got a cached authenticated Principal to record?
        if (cache) {
            Principal principal = request.getUserPrincipal();
            if (principal == null) {
                // 获取session、校验session是否有效
                Session session = request.getSessionInternal(false);
                if (session != null) {
                    principal = session.getPrincipal();
                    if (principal != null) {
                        if (log.isDebugEnabled()) {
                            log.debug("We have cached auth type " + session.getAuthType() +
                                    " for principal " + principal);
                        }
                        request.setAuthType(session.getAuthType());
                        request.setUserPrincipal(principal);
                    }
                }
            }
        }

       
    }
```



### Cookie配置
> org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory.SessionConfiguringInitializer.configureSessionCookie

```

private void configureSessionCookie(SessionCookieConfig config) {
			Session.Cookie cookie = this.session.getCookie();
			if (cookie.getName() != null) {
				config.setName(cookie.getName());
			}
			if (cookie.getDomain() != null) {
				config.setDomain(cookie.getDomain());
			}
			if (cookie.getPath() != null) {
				config.setPath(cookie.getPath());
			}
			if (cookie.getComment() != null) {
				config.setComment(cookie.getComment());
			}
			if (cookie.getHttpOnly() != null) {
				config.setHttpOnly(cookie.getHttpOnly());
			}
			if (cookie.getSecure() != null) {
				config.setSecure(cookie.getSecure());
			}
			if (cookie.getMaxAge() != null) {
				config.setMaxAge((int) cookie.getMaxAge().getSeconds());
			}
		}
```

### 将session转换成cookie，放在响应头里返回给客户端
> Set-Cookie: JSESSIONID=BAC29CF998DC66C9AC230B47DBA2654F; Max-Age=5; Expires=Wed, 03-Mar-2021 05:24:47 GMT; Path=/; HttpOnly
> 
```
org.apache.catalina.connector.Request.doGetSession

protected Session doGetSession(boolean create) {

        // There cannot be a session if no context has been assigned yet
        Context context = getContext();
        if (context == null) {
            return null;
        }

        // Return the current session if it exists and is valid
        if ((session != null) && !session.isValid()) {
            session = null;
        }
        if (session != null) {
            return session;
        }

        // Return the requested session if it exists and is valid
        Manager manager = context.getManager();
        if (manager == null) {
            return null;      // Sessions are not supported
        }
        if (requestedSessionId != null) {
            try {
                session = manager.findSession(requestedSessionId);
            } catch (IOException e) {
                session = null;
            }
            if ((session != null) && !session.isValid()) {
                session = null;
            }
            if (session != null) {
                session.access();
                return session;
            }
        }

        // Create a new session if requested and the response is not committed
        if (!create) {
            return null;
        }
        if (response != null
                && context.getServletContext()
                        .getEffectiveSessionTrackingModes()
                        .contains(SessionTrackingMode.COOKIE)
                && response.getResponse().isCommitted()) {
            throw new IllegalStateException(
                    sm.getString("coyoteRequest.sessionCreateCommitted"));
        }

        // Re-use session IDs provided by the client in very limited
        // circumstances.
        String sessionId = getRequestedSessionId();
        if (requestedSessionSSL) {
            // If the session ID has been obtained from the SSL handshake then
            // use it.
        } else if (("/".equals(context.getSessionCookiePath())
                && isRequestedSessionIdFromCookie())) {
            /* This is the common(ish) use case: using the same session ID with
             * multiple web applications on the same host. Typically this is
             * used by Portlet implementations. It only works if sessions are
             * tracked via cookies. The cookie must have a path of "/" else it
             * won't be provided for requests to all web applications.
             *
             * Any session ID provided by the client should be for a session
             * that already exists somewhere on the host. Check if the context
             * is configured for this to be confirmed.
             */
            if (context.getValidateClientProvidedNewSessionId()) {
                boolean found = false;
                for (Container container : getHost().findChildren()) {
                    Manager m = ((Context) container).getManager();
                    if (m != null) {
                        try {
                            if (m.findSession(sessionId) != null) {
                                found = true;
                                break;
                            }
                        } catch (IOException e) {
                            // Ignore. Problems with this manager will be
                            // handled elsewhere.
                        }
                    }
                }
                if (!found) {
                    sessionId = null;
                }
            }
        } else {
            sessionId = null;
        }
        session = manager.createSession(sessionId);

        // Creating a new session cookie based on that session
        if (session != null
                && context.getServletContext()
                        .getEffectiveSessionTrackingModes()
                        .contains(SessionTrackingMode.COOKIE)) {
            Cookie cookie =
                ApplicationSessionCookieConfig.createSessionCookie(
                        context, session.getIdInternal(), isSecure());

            response.addSessionCookieInternal(cookie);
        }

        if (session == null) {
            return null;
        }

        session.access();
        return session;
    }
    
    
org.apache.catalina.core.ApplicationSessionCookieConfig.createSessionCookie
    
public static Cookie createSessionCookie(Context context,
            String sessionId, boolean secure) {

        SessionCookieConfig scc =
            context.getServletContext().getSessionCookieConfig();

        // NOTE: The priority order for session cookie configuration is:
        //       1. Context level configuration
        //       2. Values from SessionCookieConfig
        //       3. Defaults

        Cookie cookie = new Cookie(
                SessionConfig.getSessionCookieName(context), sessionId);

        // Just apply the defaults.
        cookie.setMaxAge(scc.getMaxAge());
        cookie.setComment(scc.getComment());

        if (context.getSessionCookieDomain() == null) {
            // Avoid possible NPE
            if (scc.getDomain() != null) {
                cookie.setDomain(scc.getDomain());
            }
        } else {
            cookie.setDomain(context.getSessionCookieDomain());
        }

        // Always set secure if the request is secure
        if (scc.isSecure() || secure) {
            cookie.setSecure(true);
        }

        // Always set httpOnly if the context is configured for that
        if (scc.isHttpOnly() || context.getUseHttpOnly()) {
            cookie.setHttpOnly(true);
        }

        cookie.setPath(SessionConfig.getSessionCookiePath(context));

        return cookie;
    }    
```


### Cookie对象转换成响应头
> JSESSIONID=BAC29CF998DC66C9AC230B47DBA2654F; Max-Age=5; Expires=Wed, 03-Mar-2021 05:24:47 GMT; Path=/; HttpOnly

```
org.apache.tomcat.util.http.Rfc6265CookieProcessor.generateHeader

 public String generateHeader(javax.servlet.http.Cookie cookie) {

        // Can't use StringBuilder due to DateFormat
        StringBuffer header = new StringBuffer();

        // TODO: Name validation takes place in Cookie and cannot be configured
        //       per Context. Moving it to here would allow per Context config
        //       but delay validation until the header is generated. However,
        //       the spec requires an IllegalArgumentException on Cookie
        //       generation.
        header.append(cookie.getName());
        header.append('=');
        String value = cookie.getValue();
        if (value != null && value.length() > 0) {
            validateCookieValue(value);
            header.append(value);
        }

        // RFC 6265 prefers Max-Age to Expires but... (see below)
        int maxAge = cookie.getMaxAge();
        if (maxAge > -1) {
            // Negative Max-Age is equivalent to no Max-Age
            header.append("; Max-Age=");
            header.append(maxAge);

            // Microsoft IE and Microsoft Edge don't understand Max-Age so send
            // expires as well. Without this, persistent cookies fail with those
            // browsers. See http://tomcat.markmail.org/thread/g6sipbofsjossacn

            // Wdy, DD-Mon-YY HH:MM:SS GMT ( Expires Netscape format )
            header.append ("; Expires=");
            // To expire immediately we need to set the time in past
            if (maxAge == 0) {
                header.append(ANCIENT_DATE);
            } else {
                COOKIE_DATE_FORMAT.get().format(
                        new Date(System.currentTimeMillis() + maxAge * 1000L),
                        header,
                        new FieldPosition(0));
            }
        }

        String domain = cookie.getDomain();
        if (domain != null && domain.length() > 0) {
            validateDomain(domain);
            header.append("; Domain=");
            header.append(domain);
        }

        String path = cookie.getPath();
        if (path != null && path.length() > 0) {
            validatePath(path);
            header.append("; Path=");
            header.append(path);
        }

        if (cookie.getSecure()) {
            header.append("; Secure");
        }

        if (cookie.isHttpOnly()) {
            header.append("; HttpOnly");
        }

        return header.toString();
    }
```





