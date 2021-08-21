# SpringApplication初始化流程

## spring.factories
> SpringFactoriesLoader

|接口名称|备注|
|---|---|
|org.springframework.boot.diagnostics.FailureAnalyzer||
|org.springframework.boot.env.EnvironmentPostProcessor||
|org.springframework.boot.SpringApplicationRunListener|SpringApplication的run方法执行过程各生命周期监听器|
|org.springframework.context.ApplicationContextInitializer||
|org.springframework.boot.env.PropertySourceLoader||
|org.springframework.context.ApplicationListener|事件监听器用于接收和处理应用发出的各种事件|
|org.springframework.boot.diagnostics.FailureAnalysisReporter||
|org.springframework.boot.SpringBootExceptionReporter||
|org.springframework.boot.autoconfigure.AutoConfigurationImportFilter||
|org.springframework.boot.autoconfigure.AutoConfigurationImportListener||
|org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider||
|org.springframework.boot.autoconfigure.EnableAutoConfiguration||
|org.springframework.beans.BeanInfoFactory||
|org.springframework.cloud.bootstrap.BootstrapConfiguration||


## 重要接口初始化执行顺序

|接口名称|备注|
|---|---|
|EnvironmentPostProcessor|允许自定义Environment在应用上下文刷新之前，在ConfigFileApplicationListener收到ApplicationEnvironmentPreparedEvent事件后执行|
|||
|||
|||
|||
|||

## SpringApplication初始化
> 1.从spring.factories获取并设置ApplicationContextInitializer。

> 2.从spring.factories获取并设置ApplicationListener。

> 3.从spring.factories获取并设置SpringApplicationRunListener, 并将第2步中的ApplicationListener添加进来。

> 4.SpringApplicationRunListener执行生命周期的starting方法，发布ApplicationStartingEvent事件，相关的ApplicationListener会被执行。

> 5.创建ConfigurableEnvironment对象，在其构造方法中添加各种PropertySource配置文件(如:servletConfigInitParams,servletContextInitParams,systemProperties,systemEnvironment)。

> 6.在prepareEnvironment方法中读取spring.profiles.active配置，将ConfigurableEnvironment中所有的PropertySource封装成configurationProperties添加回ConfigurableEnvironment中。

> 7.调用environmentPrepared方法发布ApplicationEnvironmentPreparedEvent事件。

> 8.ConfigFileApplicationListener收到第7步发布的ApplicationEnvironmentPreparedEvent事件，从spring.factories获取到所有的EnvironmentPostProcessor实例(它自己也是EnvironmentPostProcessor的实例)，执行对应的postProcessEnvironment方法。

> 9.在ConfigFileApplicationListener的postProcessEnvironment方法中会从spring.factories获取到所有的PropertySourceLoader，这个是用来从各个地方加载PropertySource的。

> 10.调用ConfigFileApplicationListener.Loader.addLoadedPropertySources()方法将加载到的PropertySource添加到ConfigurableEnvironment对象的propertySources中。

> 11.将 environment中的spring.main开头的属性 绑定到SpringApplication 中的属性值上

> 12.创建ApplicationContext=AnnotationConfigServletWebServerApplicationContext, 构造方法里面会实例化AnnotatedBeanDefinitionReader和ClassPathBeanDefinitionScanner两个对象。

> 13.执行prepareContext方法，将environment设置到ApplicationContext上，执行ApplicationContextInitializer，发布ApplicationContextInitializedEvent事件。
> 将启动类Bootstrap注册到BeanDefinitionRegistry中，通过contextLoaded将所有实现了ApplicationContextAware接口的ApplicationListener监听器执行setApplicationContext方法关联上ApplicationContext并且将监听器添加到ApplicationContext中，然后发布ApplicationPreparedEvent事件。

> 14.执行refreshContext()方法刷新上下文。执行BeanFactoryPostProcessor

> 15.在onRefresh()方法中会创建WebServer比如Tomcat。

> 16.在finishBeanFactoryInitialization(beanFactory)中实例化所有非lazy的单例bean。

> 17.实例化完所有示例后，执行SmartInitializingSingleton.afterSingletonsInstantiated()方法

> 18.调用finishRefresh()方法完成刷新，发布ContextRefreshedEvent事件，启动WebServer，发布ServletWebServerInitializedEvent事件。

> 19.清空缓存，完成refreshContext()操作。

> 20.回到SpringApplication，发布ApplicationStartedEvent事件。

> 21.执行ApplicationRunner和CommandLineRunner的run方法。

> 22.发布ApplicationReadyEvent事件。

> 23.返回ApplicationContext，完成启动。



### getBean方法执行流程
``` 
生成代理bean：InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation()// 生成代理实例
InstantiationAwareBeanPostProcessor.postProcessAfterInitialization()// 生成完代理实例后，进行初始化
或者生成bean：SmartInstantiationAwareBeanPostProcessor.determineCandidateConstructors()// 决定使用哪个构造方法

MergedBeanDefinitionPostProcessor.postProcessMergedBeanDefinition()
InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation()
BeanNameAware.setBeanName()
BeanClassLoaderAware.setBeanClassLoader()
BeanFactoryAware.setBeanFactory()
设置bean的属性值
BeanPostProcessor.postProcessBeforeInitialization()：执行的是AbstractBeanFactory的List<BeanPostProcessor> beanPostProcessors。
org.springframework.beans.factory.InitializingBean.afterPropertiesSet()
org.springframework.beans.factory.config.BeanPostProcessor.postProcessAfterInitialization()：执行的是AbstractBeanFactory的List<BeanPostProcessor> beanPostProcessors。
```


### BeanFactoryPostProcessor是在refresh()刷新上下文的时候调用invokeBeanFactoryPostProcessors()执行的
> 1.先执行实现了PriorityOrdered的BeanFactoryPostProcessor。

> 2.再执行实现了Ordered的BeanFactoryPostProcessor。

> 3.最后执行没有Orderd的BeanFactoryPostProcessor。

### BeanDefinitionRegistryPostProcessor和BeanFactoryPostProcessor执行流程(在refresh()刷新上下文的时候调用invokeBeanFactoryPostProcessors()执行)
> 1.先执行方法参数里面传进来的实现了BeanDefinitionRegistryPostProcessor。

> 2.执行从beanFactory获取的实现了PriorityOrdered的BeanDefinitionRegistryPostProcessor。

> 3.执行从beanFactory获取的实现了Ordered的BeanDefinitionRegistryPostProcessor。

> 4.执行从beanFactory获取的没有Orderd的BeanDefinitionRegistryPostProcessor。

> 5.执行从beanFactory获取的和方法参数里面传进来的实现了BeanDefinitionRegistryPostProcessor的BeanFactoryPostProcessor。

> 6.执行方法参数里面传进来的没有实现BeanDefinitionRegistryPostProcessor只实现了BeanFactoryPostProcessor的BeanFactoryPostProcessor。

> 7.执行从beanFactory获取的实现了PriorityOrdered的BeanFactoryPostProcessor(没有实现BeanDefinitionRegistryPostProcessor)。

> 8.执行从beanFactory获取的实现了Ordered的BeanFactoryPostProcessor(没有实现BeanDefinitionRegistryPostProcessor)。

> 9.执行从beanFactory获取的没有Orderd的BeanFactoryPostProcessor(没有实现BeanDefinitionRegistryPostProcessor)。


``` 
public static void invokeBeanFactoryPostProcessors(
			ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors) {

		// Invoke BeanDefinitionRegistryPostProcessors first, if any.
		Set<String> processedBeans = new HashSet<>();

		if (beanFactory instanceof BeanDefinitionRegistry) {
			BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
			List<BeanFactoryPostProcessor> regularPostProcessors = new ArrayList<>();
			List<BeanDefinitionRegistryPostProcessor> registryProcessors = new ArrayList<>();

			for (BeanFactoryPostProcessor postProcessor : beanFactoryPostProcessors) {
				if (postProcessor instanceof BeanDefinitionRegistryPostProcessor) {
					BeanDefinitionRegistryPostProcessor registryProcessor =
							(BeanDefinitionRegistryPostProcessor) postProcessor;
					registryProcessor.postProcessBeanDefinitionRegistry(registry);
					registryProcessors.add(registryProcessor);
				}
				else {
					regularPostProcessors.add(postProcessor);
				}
			}

			// Do not initialize FactoryBeans here: We need to leave all regular beans
			// uninitialized to let the bean factory post-processors apply to them!
			// Separate between BeanDefinitionRegistryPostProcessors that implement
			// PriorityOrdered, Ordered, and the rest.
			List<BeanDefinitionRegistryPostProcessor> currentRegistryProcessors = new ArrayList<>();

			// First, invoke the BeanDefinitionRegistryPostProcessors that implement PriorityOrdered.
			String[] postProcessorNames =
					beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
			for (String ppName : postProcessorNames) {
				if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
					currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
					processedBeans.add(ppName);
				}
			}
			sortPostProcessors(currentRegistryProcessors, beanFactory);
			registryProcessors.addAll(currentRegistryProcessors);
			invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
			currentRegistryProcessors.clear();

			// Next, invoke the BeanDefinitionRegistryPostProcessors that implement Ordered.
			postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
			for (String ppName : postProcessorNames) {
				if (!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class)) {
					currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
					processedBeans.add(ppName);
				}
			}
			sortPostProcessors(currentRegistryProcessors, beanFactory);
			registryProcessors.addAll(currentRegistryProcessors);
			invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
			currentRegistryProcessors.clear();

			// Finally, invoke all other BeanDefinitionRegistryPostProcessors until no further ones appear.
			boolean reiterate = true;
			while (reiterate) {
				reiterate = false;
				postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
				for (String ppName : postProcessorNames) {
					if (!processedBeans.contains(ppName)) {
						currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
						processedBeans.add(ppName);
						reiterate = true;
					}
				}
				sortPostProcessors(currentRegistryProcessors, beanFactory);
				registryProcessors.addAll(currentRegistryProcessors);
				invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
				currentRegistryProcessors.clear();
			}

			// Now, invoke the postProcessBeanFactory callback of all processors handled so far.
			invokeBeanFactoryPostProcessors(registryProcessors, beanFactory);
			invokeBeanFactoryPostProcessors(regularPostProcessors, beanFactory);
		}

		else {
			// Invoke factory processors registered with the context instance.
			invokeBeanFactoryPostProcessors(beanFactoryPostProcessors, beanFactory);
		}

		// Do not initialize FactoryBeans here: We need to leave all regular beans
		// uninitialized to let the bean factory post-processors apply to them!
		String[] postProcessorNames =
				beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);

		// Separate between BeanFactoryPostProcessors that implement PriorityOrdered,
		// Ordered, and the rest.
		List<BeanFactoryPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();
		List<String> orderedPostProcessorNames = new ArrayList<>();
		List<String> nonOrderedPostProcessorNames = new ArrayList<>();
		for (String ppName : postProcessorNames) {
			if (processedBeans.contains(ppName)) {
				// skip - already processed in first phase above
			}
			else if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
				priorityOrderedPostProcessors.add(beanFactory.getBean(ppName, BeanFactoryPostProcessor.class));
			}
			else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
				orderedPostProcessorNames.add(ppName);
			}
			else {
				nonOrderedPostProcessorNames.add(ppName);
			}
		}

		// First, invoke the BeanFactoryPostProcessors that implement PriorityOrdered.
		sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
		invokeBeanFactoryPostProcessors(priorityOrderedPostProcessors, beanFactory);

		// Next, invoke the BeanFactoryPostProcessors that implement Ordered.
		List<BeanFactoryPostProcessor> orderedPostProcessors = new ArrayList<>();
		for (String postProcessorName : orderedPostProcessorNames) {
			orderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
		}
		sortPostProcessors(orderedPostProcessors, beanFactory);
		invokeBeanFactoryPostProcessors(orderedPostProcessors, beanFactory);

		// Finally, invoke all other BeanFactoryPostProcessors.
		List<BeanFactoryPostProcessor> nonOrderedPostProcessors = new ArrayList<>();
		for (String postProcessorName : nonOrderedPostProcessorNames) {
			nonOrderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
		}
		invokeBeanFactoryPostProcessors(nonOrderedPostProcessors, beanFactory);

		// Clear cached merged bean definitions since the post-processors might have
		// modified the original metadata, e.g. replacing placeholders in values...
		beanFactory.clearMetadataCache();
	}
```

### ConfigurationClassPostProcessor是用来解析@Configuration类的，是在SpringApplication.createApplicationContext()中实例化AnnotationConfigServletWebServerApplicationContext时自动创建的。

``` 
	public AnnotationConfigServletWebServerApplicationContext() {
		this.reader = new AnnotatedBeanDefinitionReader(this);
		this.scanner = new ClassPathBeanDefinitionScanner(this);
	}


	public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry, Environment environment) {
		Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
		Assert.notNull(environment, "Environment must not be null");
		this.registry = registry;
		this.conditionEvaluator = new ConditionEvaluator(registry, environment, null);
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














































































































































































































































































