## HandlerMethodReturnValueHandler返回结果处理器

### 返回值处理器：HandlerMethodReturnValueHandler
> 被@RequestMapping修饰的目标方法(HandlerMethod)返回类型处理器

|处理类|supportsReturnType|
|---|---|
|ModelAndViewMethodReturnValueHandler|ModelAndView.class|
|ModelMethodProcessor|Model.class|
|ViewMethodReturnValueHandler|View.class|
|ResponseBodyEmitterReturnValueHandler|ResponseBodyEmitter.class|
|StreamingResponseBodyReturnValueHandler|StreamingResponseBody.class|
|HttpEntityMethodProcessor|HttpEntity.class|
|HttpHeadersReturnValueHandler|HttpHeaders.class|
|CallableMethodReturnValueHandler|Callable.class|
|DeferredResultMethodReturnValueHandler|DeferredResult或者ListenableFuture.class或者CompletionStage.class|
|AsyncTaskMethodReturnValueHandler|WebAsyncTask.class|
|ModelAttributeMethodProcessor|方法被@ModelAttribute修饰, returnType.hasMethodAnnotation(ModelAttribute.class)|
|RequestResponseBodyMethodProcessor|方法或者方法所在类被ResponseBody.class注解修饰|
|ViewNameMethodReturnValueHandler|返回值是void或者是字符串|
|MapMethodProcessor|Map.class|
