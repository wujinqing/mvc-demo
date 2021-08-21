# 启动过程涉及的接口


## 执行顺序

|接口|执行时机|
|---|---|
|EnvironmentPostProcessor|在ConfigFileApplicationListener收到ApplicationEnvironmentPreparedEvent执行，加载各种配置|
|日志系统初始化|在LoggingApplicationListener接收到ApplicationEnvironmentPreparedEvent事件后执行(在EnvironmentPostProcessor执行完之后)|
|ApplicationContextInitializer|在ApplicationContext创建完后的prepareContext方法中直接执行，执行完后会发布一个ConfigurableApplicationContext事件|
||执行完所有ApplicationContextInitializer初始化器之后，发布ApplicationContextInitializedEvent事件|
|将被@SpringBootApplication注解修饰的启动类注册到ApplicationContext的bean工厂中|注册完发布ApplicationPreparedEvent事件|
|ApplicationContextAware|在EventPublishingRunListener发布ApplicationPreparedEvent事件前执行(只针对实现了此接口的ApplicationListener实例)|
|将SpringApplication里面所有的ApplicationListener添加到ApplicationContext应用上下文中|执行完每一个ApplicationContextAware后|
||执行完所有ApplicationContextAware后发布ApplicationPreparedEvent事件|
|刷新上下文|执行refreshContext方法|
|||
|||
|||
|||


## 1.ApplicationContextInitializer：应用上下文初始化器, 是在prepareContext准备环境方法中执行的。

## 2.ApplicationListener：应用事件监听器
> 最重要的一个接口是ConfigFileApplicationListener，在收到ApplicationEnvironmentPreparedEvent事件后读取各种配置文件。

## 3.SpringApplicationRunListener：SpringApplication的run方法执行流程监听器。



## 重要事件

### 1.ApplicationStartingEvent
> LoggingApplicationListener在收到ApplicationStartingEvent事件后实例化LoggingSystem。

### 2.ApplicationEnvironmentPreparedEvent

## 创建环境StandardServletEnvironment
> 默认会将servletContextInitParams、servletContextInitParams、systemProperties、systemEnvironment添加到propertySources里面去。

### 配置环境configureEnvironment
> 1.设置各种类型转换器Converter.
> 2.配置自定义PropertySources。
> 3.配置activeProfiles。

### ConfigurationPropertySources.attach(environment)
> 将Environment中的所有propertySources，封装成SpringConfigurationPropertySources对象 以configurationProperties的名字添加回propertySources。


### 发布ApplicationEnvironmentPreparedEvent事件


### EnvironmentPostProcessor是配置在META-INF/spring.factories文件里面的



## ConfigFileApplicationListener

## 创建AnnotationConfigServletWebServerApplicationContext
> 它的构造方法中会创建两个对象：AnnotatedBeanDefinitionReader和ClassPathBeanDefinitionScanner

### AnnotatedBeanDefinitionReader的构造方法中会做如下事情：
> 在AnnotatedBeanDefinitionReader中会实例化一个ConditionEvaluator对象用来计算@Conditional注解。

> 在AnnotatedBeanDefinitionReader中会注册一个ConfigurationClassPostProcessor实例的BeanFactoryPostProcessor用来处理@Configuration注解。

> 在AnnotatedBeanDefinitionReader中会注册一个AutowiredAnnotationBeanPostProcessor实例的BeanPostProcessor用来处理@Autowired、@Value以及@Inject注解。

> 在AnnotatedBeanDefinitionReader中会注册一个CommonAnnotationBeanPostProcessor实例的BeanPostProcessor用来处理@PostConstruct、@PreDestroy以及@Resource注解。

> 在AnnotatedBeanDefinitionReader中会注册一个EventListenerMethodProcessor实例的BeanFactoryPostProcessor用来处理@EventListener注解。


```

public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry, Environment environment) {
		Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
		Assert.notNull(environment, "Environment must not be null");
		this.registry = registry;
		this.conditionEvaluator = new ConditionEvaluator(registry, environment, null);
		// 注册一些注解的解析器
		AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);
	}
	
	
	
	public static Set<BeanDefinitionHolder> registerAnnotationConfigProcessors(
			BeanDefinitionRegistry registry, @Nullable Object source) {

		DefaultListableBeanFactory beanFactory = unwrapDefaultListableBeanFactory(registry);
		if (beanFactory != null) {
			if (!(beanFactory.getDependencyComparator() instanceof AnnotationAwareOrderComparator)) {
				beanFactory.setDependencyComparator(AnnotationAwareOrderComparator.INSTANCE);
			}
			if (!(beanFactory.getAutowireCandidateResolver() instanceof ContextAnnotationAutowireCandidateResolver)) {
				beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
			}
		}

		Set<BeanDefinitionHolder> beanDefs = new LinkedHashSet<>(8);

		if (!registry.containsBeanDefinition(CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME)) {
			RootBeanDefinition def = new RootBeanDefinition(ConfigurationClassPostProcessor.class);
			def.setSource(source);
			beanDefs.add(registerPostProcessor(registry, def, CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME));
		}

		if (!registry.containsBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)) {
			RootBeanDefinition def = new RootBeanDefinition(AutowiredAnnotationBeanPostProcessor.class);
			def.setSource(source);
			beanDefs.add(registerPostProcessor(registry, def, AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
		}

		// Check for JSR-250 support, and if present add the CommonAnnotationBeanPostProcessor.
		if (jsr250Present && !registry.containsBeanDefinition(COMMON_ANNOTATION_PROCESSOR_BEAN_NAME)) {
			RootBeanDefinition def = new RootBeanDefinition(CommonAnnotationBeanPostProcessor.class);
			def.setSource(source);
			beanDefs.add(registerPostProcessor(registry, def, COMMON_ANNOTATION_PROCESSOR_BEAN_NAME));
		}

		// Check for JPA support, and if present add the PersistenceAnnotationBeanPostProcessor.
		if (jpaPresent && !registry.containsBeanDefinition(PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME)) {
			RootBeanDefinition def = new RootBeanDefinition();
			try {
				def.setBeanClass(ClassUtils.forName(PERSISTENCE_ANNOTATION_PROCESSOR_CLASS_NAME,
						AnnotationConfigUtils.class.getClassLoader()));
			}
			catch (ClassNotFoundException ex) {
				throw new IllegalStateException(
						"Cannot load optional framework class: " + PERSISTENCE_ANNOTATION_PROCESSOR_CLASS_NAME, ex);
			}
			def.setSource(source);
			beanDefs.add(registerPostProcessor(registry, def, PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME));
		}

		if (!registry.containsBeanDefinition(EVENT_LISTENER_PROCESSOR_BEAN_NAME)) {
			RootBeanDefinition def = new RootBeanDefinition(EventListenerMethodProcessor.class);
			def.setSource(source);
			beanDefs.add(registerPostProcessor(registry, def, EVENT_LISTENER_PROCESSOR_BEAN_NAME));
		}

		if (!registry.containsBeanDefinition(EVENT_LISTENER_FACTORY_BEAN_NAME)) {
			RootBeanDefinition def = new RootBeanDefinition(DefaultEventListenerFactory.class);
			def.setSource(source);
			beanDefs.add(registerPostProcessor(registry, def, EVENT_LISTENER_FACTORY_BEAN_NAME));
		}

		return beanDefs;
	}

```


### ClassPathBeanDefinitionScanner 的构造方法中会做如下事情：

> 1.将@Component注解添加到includeFilters中。

> 2.将@ManagedBean注解添加到includeFilters中。

> 3.将@Named注解添加到includeFilters中。


```  注册默认过滤器
protected void registerDefaultFilters() {
		this.includeFilters.add(new AnnotationTypeFilter(Component.class));
		ClassLoader cl = ClassPathScanningCandidateComponentProvider.class.getClassLoader();
		try {
			this.includeFilters.add(new AnnotationTypeFilter(
					((Class<? extends Annotation>) ClassUtils.forName("javax.annotation.ManagedBean", cl)), false));
			logger.trace("JSR-250 'javax.annotation.ManagedBean' found and supported for component scanning");
		}
		catch (ClassNotFoundException ex) {
			// JSR-250 1.1 API (as included in Java EE 6) not available - simply skip.
		}
		try {
			this.includeFilters.add(new AnnotationTypeFilter(
					((Class<? extends Annotation>) ClassUtils.forName("javax.inject.Named", cl)), false));
			logger.trace("JSR-330 'javax.inject.Named' annotation found and supported for component scanning");
		}
		catch (ClassNotFoundException ex) {
			// JSR-330 API not available - simply skip.
		}
	}
```

## prepareContext准备环境
> 1.context.setEnvironment(environment);将environment关联到应用上下文中。

> 2.将各种类型转换器关联到应用上下文中。

> 3.applyInitializers(context);执行应用初始化器ApplicationContextInitializer。

> 4.发布ApplicationContextInitializedEvent：应用上下文已经被初始化事件。

> 5.load(context, sources.toArray(new Object\[0]));把com.jin.mvc.demo.Bootstrap注册到bean工厂。



### BeanDefinitionLoader: Bean定义加载器初始化（用来从注解、XML、package里面加载Bean定义）
> 1.初始化一个AnnotatedBeanDefinitionReader对象。

> 2.初始化一个XmlBeanDefinitionReader对象。

> 3.初始化一个ClassPathBeanDefinitionScanner对象。




```
BeanDefinitionLoader(BeanDefinitionRegistry registry, Object... sources) {
		Assert.notNull(registry, "Registry must not be null");
		Assert.notEmpty(sources, "Sources must not be empty");
		this.sources = sources;
		this.annotatedReader = new AnnotatedBeanDefinitionReader(registry);
		this.xmlReader = new XmlBeanDefinitionReader(registry);
		if (isGroovyPresent()) {
			this.groovyReader = new GroovyBeanDefinitionReader(registry);
		}
		this.scanner = new ClassPathBeanDefinitionScanner(registry);
		this.scanner.addExcludeFilter(new ClassExcludeFilter(sources));
	}
```


### BeanDefinitionHolder：Bean定义持有者


## 发布ApplicationPreparedEvent

> 1.将所有实现了ApplicationContextAware的ApplicationListener实例调用它的setApplicationContext方法





``` 执行ApplicationContextAware，添加ApplicationListener
	public void contextLoaded(ConfigurableApplicationContext context) {
		for (ApplicationListener<?> listener : this.application.getListeners()) {
			if (listener instanceof ApplicationContextAware) {
				((ApplicationContextAware) listener).setApplicationContext(context);
			}
			context.addApplicationListener(listener);
		}
		this.initialMulticaster.multicastEvent(new ApplicationPreparedEvent(this.application, this.args, context));
	}
```

## 发布ApplicationStartedEvent事件


## 从bean容器中获取并执行所有的ApplicationRunner和CommandLineRunner对象。

## 发布ApplicationReadyEvent事件。

## 最后返回ApplicationContext完成启动流程。









