## Filter、HandlerInterceptor拦截器、ControllerAdvice、AOP比较

### 执行顺序
> Filter -> HandlerInterceptor -> ControllerAdvice -> AOP.

|执行顺序|归属|
|---|---|
|Before doFilter|Filter|
|preHandle|HandlerInterceptor|
|beforeBodyRead|RequestBodyAdvice|
|afterBodyRead|RequestBodyAdvice|
|Before around|AOP|
|目标方法执行|自己写的方法|
|After around|AOP|
|beforeBodyWrite|ResponseBodyAdvice|
|postHandle|HandlerInterceptor|
|afterCompletion|HandlerInterceptor|
|After doFilter|Filter|


### Filter、HandlerInterceptor拦截器、ControllerAdvice、AOP比较

|比较项|Filter|HandlerInterceptor|ControllerAdvice|AOP|
|---|---|---|---|---|
|能否获取目标方法的入参对象|不可以|不可以|beforeBodyRead不可以，afterBodyRead方法可以|可以|
|能否获取目标方法的返回对象|不可以|不可以，但是可以获取ModelAndView对象|可以|可以|
|使用范围限制|没有|没有|只能处理被@RequestBody，@ResponseBody注解修饰或者出入参类型是HttpEntity，ResponseEntity的方法|没有|
|是否在spring管辖范围内|否|是|是|是|

### Filter不属于spring，无法使用spring全家桶。

