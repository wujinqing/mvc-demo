## @WebFilter、@WebListener、@WebServlet详解

## @ServletComponentScan
> SpringBoot启动类中加入@ServletComponentScan注解，用于初始化扫描@WebFilter、@WebListener、@WebServlet注解；它只能在内嵌服务器中使用。

```
@SpringBootApplication
@ServletComponentScan
public class Bootstrap {
    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }
}
```

## @WebServlet
> 被@WebServlet注解修饰的Servlet类必须继承HttpServlet。

```
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("MyServlet");
    }
}
```

## @WebListener
> 被@WebListener注解修饰的Listener必须实现一个或者多个以下接口:

```
 javax.servlet.http.HttpSessionAttributeListener,
 javax.servlet.http.HttpSessionListener,
 javax.servlet.ServletContextAttributeListener,
 javax.servlet.ServletContextListener,
 javax.servlet.ServletRequestAttributeListener,
 javax.servlet.ServletRequestListener,
 javax.servlet.http.HttpSessionIdListener
```

```

```

## @WebFilter
> 被@WebFilter注解修饰的Filter类必须实现Filter接口。

```
@WebFilter("/*")
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Before doFilter.");

        chain.doFilter(request, response);

        System.out.println("After doFilter.");
    }
}

```

### Filter执行源码

> 1.隐式递归，遍历filters数组中的每一个Filter。直到所有Filter都执行完，再执行Servlet.

```java

public final class ApplicationFilterChain implements FilterChain {
    public void doFilter(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
    
            if( Globals.IS_SECURITY_ENABLED ) {
                final ServletRequest req = request;
                final ServletResponse res = response;
                try {
                    java.security.AccessController.doPrivileged(
                        new java.security.PrivilegedExceptionAction<Void>() {
                            @Override
                            public Void run()
                                throws ServletException, IOException {
                                internalDoFilter(req,res);
                                return null;
                            }
                        }
                    );
                } catch( PrivilegedActionException pe) {
                    Exception e = pe.getException();
                    if (e instanceof ServletException)
                        throw (ServletException) e;
                    else if (e instanceof IOException)
                        throw (IOException) e;
                    else if (e instanceof RuntimeException)
                        throw (RuntimeException) e;
                    else
                        throw new ServletException(e.getMessage(), e);
                }
            } else {
                internalDoFilter(request,response);
            }
        }
        
        
    private void internalDoFilter(ServletRequest request,
                                  ServletResponse response)
        throws IOException, ServletException {

        // Call the next filter if there is one
        if (pos < n) {// 隐式递归，变量filters数组中的每一个Filter。直到所有Filter都执行完，再执行Servlet
        
            ApplicationFilterConfig filterConfig = filters[pos++];
            try {
                Filter filter = filterConfig.getFilter();

                if (request.isAsyncSupported() && "false".equalsIgnoreCase(
                        filterConfig.getFilterDef().getAsyncSupported())) {
                    request.setAttribute(Globals.ASYNC_SUPPORTED_ATTR, Boolean.FALSE);
                }
                if( Globals.IS_SECURITY_ENABLED ) {
                    final ServletRequest req = request;
                    final ServletResponse res = response;
                    Principal principal =
                        ((HttpServletRequest) req).getUserPrincipal();

                    Object[] args = new Object[]{req, res, this};
                    SecurityUtil.doAsPrivilege ("doFilter", filter, classType, args, principal);
                } else {
                    // 隐式递归，执行Filter
                    filter.doFilter(request, response, this);
                }
            } catch (IOException | ServletException | RuntimeException e) {
                throw e;
            } catch (Throwable e) {
                e = ExceptionUtils.unwrapInvocationTargetException(e);
                ExceptionUtils.handleThrowable(e);
                throw new ServletException(sm.getString("filterChain.filter"), e);
            }
            return;
        }

        // We fell off the end of the chain -- call the servlet instance
        try {
            if (ApplicationDispatcher.WRAP_SAME_OBJECT) {
                lastServicedRequest.set(request);
                lastServicedResponse.set(response);
            }

            if (request.isAsyncSupported() && !servletSupportsAsync) {
                request.setAttribute(Globals.ASYNC_SUPPORTED_ATTR,
                        Boolean.FALSE);
            }
            // Use potentially wrapped request from this point
            if ((request instanceof HttpServletRequest) &&
                    (response instanceof HttpServletResponse) &&
                    Globals.IS_SECURITY_ENABLED ) {
                final ServletRequest req = request;
                final ServletResponse res = response;
                Principal principal =
                    ((HttpServletRequest) req).getUserPrincipal();
                Object[] args = new Object[]{req, res};
                SecurityUtil.doAsPrivilege("service",
                                           servlet,
                                           classTypeUsedInService,
                                           args,
                                           principal);
            } else {
                // 执行Servlet
                servlet.service(request, response);
            }
        } catch (IOException | ServletException | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            e = ExceptionUtils.unwrapInvocationTargetException(e);
            ExceptionUtils.handleThrowable(e);
            throw new ServletException(sm.getString("filterChain.servlet"), e);
        } finally {
            if (ApplicationDispatcher.WRAP_SAME_OBJECT) {
                lastServicedRequest.set(null);
                lastServicedResponse.set(null);
            }
        }
    }
}
```













































































































































































































































































































