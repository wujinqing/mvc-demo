## RequestMapping注解剖析

### @RequestMapping初始化过程

> 1.RequestMappingHandlerMapping实现了InitializingBean接口，实现了afterPropertiesSet()方法。

> 2.扫描所有被@Controller或者@RequestMapping注解修饰的bean，作为候选bean。

> 3.遍历每个候选bean的所有方法，找到被@RequestMapping修饰的方法，封装成一个RequestMappingInfo对象。

> 4.检查这个方法所在类是否也被@RequestMapping修饰，如果是，则合并两个@RequestMapping的信息。

> 5.将RequestMappingInfo和HandlerMethod信息封装成MappingRegistration对象注册到mappingRegistry维护的registry中。


### @RequestMapping属性

|属性名|说明|示例|
|---|---|---|
|name|当前RequestMapping的名称, 如果设定了会作为nameLookup的key||
|value, path|mapping的路径|"/myPath.do", "/myPath/*.do", "/${connect}"|
|method|允许的请求方法列表|@RequestMapping(path = "/getUser6", method = {RequestMethod.GET, RequestMethod.POST})|
|params|限制参数|@RequestMapping(path = "/getUser7", params = {"id=21", "name", "!age"}) 匹配参数id的值是21，name参数要出现，age参数不能出现|
|headers|请求头限制|@RequestMapping(path = "/getUser8", headers = {"content-type=text/*", "My-Header", "!My-Other-Header"}) 匹配头信息，content-type满足text *，必须出现key为My-Header的头，并且不能出现可以为My-Other-Header的头|
|consumes|限制请求头里面Content-Type的值, 告诉服务器端客户端给的入参类型是什么|@RequestMapping(path = "/getUser9", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}) 请求头里面的Content-Type的值必须是"application/json;charset=UTF-8"|
|produces|限制请求头里面Accept的值, 告诉服务器端客户端需要服务器端返回的类型是什么|@RequestMapping(path = "/getUser10", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}) 请求头里面的Accept的值必须是"application/json;charset=UTF-8"|



### 初始化并找到所有
> RequestMappingHandlerMapping实现了InitializingBean接口，实现afterPropertiesSet()方法。

``` 
@Override
	public void afterPropertiesSet() {
		this.config = new RequestMappingInfo.BuilderConfiguration();
		this.config.setUrlPathHelper(getUrlPathHelper());
		this.config.setPathMatcher(getPathMatcher());
		this.config.setSuffixPatternMatch(this.useSuffixPatternMatch);
		this.config.setTrailingSlashMatch(this.useTrailingSlashMatch);
		this.config.setRegisteredSuffixPatternMatch(this.useRegisteredSuffixPatternMatch);
		this.config.setContentNegotiationManager(getContentNegotiationManager());

		super.afterPropertiesSet();
	}
```

### 从spring容器中找到所有被@Controller或者@RequestMapping注解修饰的候选实例对象

``` 
org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.initHandlerMethods

    protected void initHandlerMethods() {
		for (String beanName : getCandidateBeanNames()) {
			if (!beanName.startsWith(SCOPED_TARGET_NAME_PREFIX)) {
				processCandidateBean(beanName);
			}
		}
		handlerMethodsInitialized(getHandlerMethods());
	}
	
    protected void processCandidateBean(String beanName) {
		Class<?> beanType = null;
		try {
			beanType = obtainApplicationContext().getType(beanName);
		}
		catch (Throwable ex) {
			// An unresolvable bean type, probably from a lazy bean - let's ignore it.
			if (logger.isTraceEnabled()) {
				logger.trace("Could not resolve type for bean '" + beanName + "'", ex);
			}
		}
		if (beanType != null && isHandler(beanType)) {
			detectHandlerMethods(beanName);
		}
	}
```

### 判断当前bean是否是RequestMapping的候选bean
``` 

protected boolean isHandler(Class<?> beanType) {
		return (AnnotatedElementUtils.hasAnnotation(beanType, Controller.class) ||
				AnnotatedElementUtils.hasAnnotation(beanType, RequestMapping.class));
	}

```

### 创建及合并父类RequestMappingInfo
```
protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
		RequestMappingInfo info = createRequestMappingInfo(method);
		if (info != null) {
			RequestMappingInfo typeInfo = createRequestMappingInfo(handlerType);
			if (typeInfo != null) {
				info = typeInfo.combine(info);
			}
			String prefix = getPathPrefix(handlerType);
			if (prefix != null) {
				info = RequestMappingInfo.paths(prefix).build().combine(info);
			}
		}
		return info;
	}
```

### 根据@RequestMapping注解创建RequestMappingInfo对象

``` 
protected RequestMappingInfo createRequestMappingInfo(
			RequestMapping requestMapping, @Nullable RequestCondition<?> customCondition) {

		RequestMappingInfo.Builder builder = RequestMappingInfo
				.paths(resolveEmbeddedValuesInPatterns(requestMapping.path()))
				.methods(requestMapping.method())
				.params(requestMapping.params())
				.headers(requestMapping.headers())
				.consumes(requestMapping.consumes())
				.produces(requestMapping.produces())
				.mappingName(requestMapping.name());
		if (customCondition != null) {
			builder.customCondition(customCondition);
		}
		return builder.options(this.config).build();
	}
	
	
public RequestMappingInfo build() {
			ContentNegotiationManager manager = this.options.getContentNegotiationManager();

			PatternsRequestCondition patternsCondition = new PatternsRequestCondition(
					this.paths, this.options.getUrlPathHelper(), this.options.getPathMatcher(),
					this.options.useSuffixPatternMatch(), this.options.useTrailingSlashMatch(),
					this.options.getFileExtensions());

			return new RequestMappingInfo(this.mappingName, patternsCondition,
					new RequestMethodsRequestCondition(this.methods),
					new ParamsRequestCondition(this.params),
					new HeadersRequestCondition(this.headers),
					new ConsumesRequestCondition(this.consumes, this.headers),
					new ProducesRequestCondition(this.produces, this.headers, manager),
					this.customCondition);
		}
```


### RequestMapping路径合并逻辑

``` 
public String combine(String pattern1, String pattern2) {
		if (!StringUtils.hasText(pattern1) && !StringUtils.hasText(pattern2)) {
			return "";
		}
		if (!StringUtils.hasText(pattern1)) {
			return pattern2;
		}
		if (!StringUtils.hasText(pattern2)) {
			return pattern1;
		}

		boolean pattern1ContainsUriVar = (pattern1.indexOf('{') != -1);
		if (!pattern1.equals(pattern2) && !pattern1ContainsUriVar && match(pattern1, pattern2)) {
			// /* + /hotel -> /hotel ; "/*.*" + "/*.html" -> /*.html
			// However /user + /user -> /usr/user ; /{foo} + /bar -> /{foo}/bar
			return pattern2;
		}

		// /hotels/* + /booking -> /hotels/booking
		// /hotels/* + booking -> /hotels/booking
		if (pattern1.endsWith(this.pathSeparatorPatternCache.getEndsOnWildCard())) {
			return concat(pattern1.substring(0, pattern1.length() - 2), pattern2);
		}

		// /hotels/** + /booking -> /hotels/**/booking
		// /hotels/** + booking -> /hotels/**/booking
		if (pattern1.endsWith(this.pathSeparatorPatternCache.getEndsOnDoubleWildCard())) {
			return concat(pattern1, pattern2);
		}

		int starDotPos1 = pattern1.indexOf("*.");
		if (pattern1ContainsUriVar || starDotPos1 == -1 || this.pathSeparator.equals(".")) {
			// simply concatenate the two patterns
			return concat(pattern1, pattern2);
		}

		String ext1 = pattern1.substring(starDotPos1 + 1);
		int dotPos2 = pattern2.indexOf('.');
		String file2 = (dotPos2 == -1 ? pattern2 : pattern2.substring(0, dotPos2));
		String ext2 = (dotPos2 == -1 ? "" : pattern2.substring(dotPos2));
		boolean ext1All = (ext1.equals(".*") || ext1.isEmpty());
		boolean ext2All = (ext2.equals(".*") || ext2.isEmpty());
		if (!ext1All && !ext2All) {
			throw new IllegalArgumentException("Cannot combine patterns: " + pattern1 + " vs " + pattern2);
		}
		String ext = (ext1All ? ext2 : ext1);
		return file2 + ext;
	}
```


### 将RequestMappingInfo信息注册到mappingRegistry中
> 分别会在mappingLookup，urlLookup，nameLookup中保存信息，分别是根据mapping，url，name来查找。

> @RequestMapping("${server.error.path:${error.path:/error}}")  默认值嵌套默认值

``` 
org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.MappingRegistry.register

public void register(T mapping, Object handler, Method method) {
			this.readWriteLock.writeLock().lock();
			try {
				HandlerMethod handlerMethod = createHandlerMethod(handler, method);
				assertUniqueMethodMapping(handlerMethod, mapping);
				this.mappingLookup.put(mapping, handlerMethod);

				List<String> directUrls = getDirectUrls(mapping);
				for (String url : directUrls) {
					this.urlLookup.add(url, mapping);
				}

				String name = null;
				if (getNamingStrategy() != null) {
					name = getNamingStrategy().getName(handlerMethod, mapping);
					addMappingName(name, handlerMethod);
				}

				CorsConfiguration corsConfig = initCorsConfiguration(handler, method, mapping);
				if (corsConfig != null) {
					this.corsLookup.put(handlerMethod, corsConfig);
				}

				this.registry.put(mapping, new MappingRegistration<>(mapping, handlerMethod, directUrls, name));
			}
			finally {
				this.readWriteLock.writeLock().unlock();
			}
		}

```

### BasicErrorController 默认的错误处理类，被@Controller @RequestMapping两个注解修饰了。
> org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController




























































