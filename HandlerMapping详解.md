## HandlerMapping 请求和处理对象之间的映射

### Interface to be implemented by objects that define a mapping between requests and handler objects.


> 一个handler总是会包装成一个HandlerExecutionChain。并且可能会有一些 HandlerInterceptor拦截器，只有当拦截器中所有的preHandle方法都返回true，handler才会执行。

> 可以实现Ordered来实现排序。



### SimpleUrlHandlerMapping 

> 用来映射 "**/favicon.ico".

### RequestMappingHandlerMapping 用来处理我们自定义的@RequestMapping.






















































































































































































































































































































































































































































































































































































































