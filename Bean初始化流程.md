# Bean初始化流程

> 1.调用org.springframework.boot.SpringApplication.refreshContext()方法。

> 2.调用AbstractApplicationContext.refresh()方法。

> 3.调用invokeBeanFactoryPostProcessors(beanFactory)方法。

> 4.找到ConfigurationClassPostProcessor类，执行postProcessBeanDefinitionRegistry()

> 5.找到被@Configuration注解修饰的com.jin.mvc.demo.Bootstrap启动类。

> 6.创建@Configuration注解解析对象ConfigurationClassParser。

> 7.调用ConfigurationClassParser.parse()方法解析。

> 8.调用ConfigurationClassParser.doProcessConfigurationClass()方法解析。

> 9.调用ComponentScanAnnotationParser.parse()方法解析。

> 10.创建ClassPathBeanDefinitionScanner扫描器类，它会扫描包含@Component(包含它的子注解: @Repository, @Service, @Controller)注解修饰的类。

> 11.对第10步筛选出来的类，递归执行ConfigurationClassParser.processConfigurationClass()进一步解析。

> 12.将扫描后的类放到ConfigurationClassParser.configurationClasses中。

> 13.将所有的bean定义都解析到registry中。

> 14.执行BeanFactoryPostProcessor().






### 扫描解析bean定义：ConfigurationClassPostProcessor.processConfigBeanDefinitions()
``` 
public void processConfigBeanDefinitions(BeanDefinitionRegistry registry) {
		List<BeanDefinitionHolder> configCandidates = new ArrayList<>();
		String[] candidateNames = registry.getBeanDefinitionNames();

		for (String beanName : candidateNames) {
			BeanDefinition beanDef = registry.getBeanDefinition(beanName);
			
			if (ConfigurationClassUtils.isFullConfigurationClass(beanDef) ||
					ConfigurationClassUtils.isLiteConfigurationClass(beanDef)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Bean definition has already been processed as a configuration class: " + beanDef);
				}
			}
			// checkConfigurationClassCandidate: 是否被@Configuration注解修饰，或者是否被@Component, @ComponentScan, @Import, @ImportResource注解修饰
			else if (ConfigurationClassUtils.checkConfigurationClassCandidate(beanDef, this.metadataReaderFactory)) {
				configCandidates.add(new BeanDefinitionHolder(beanDef, beanName));
			}
		}

		// Return immediately if no @Configuration classes were found
		if (configCandidates.isEmpty()) {
			return;
		}

		// Sort by previously determined @Order value, if applicable
		configCandidates.sort((bd1, bd2) -> {
			int i1 = ConfigurationClassUtils.getOrder(bd1.getBeanDefinition());
			int i2 = ConfigurationClassUtils.getOrder(bd2.getBeanDefinition());
			return Integer.compare(i1, i2);
		});

		// Detect any custom bean name generation strategy supplied through the enclosing application context
		SingletonBeanRegistry sbr = null;
		if (registry instanceof SingletonBeanRegistry) {
			sbr = (SingletonBeanRegistry) registry;
			if (!this.localBeanNameGeneratorSet) {
				BeanNameGenerator generator = (BeanNameGenerator) sbr.getSingleton(CONFIGURATION_BEAN_NAME_GENERATOR);
				if (generator != null) {
					this.componentScanBeanNameGenerator = generator;
					this.importBeanNameGenerator = generator;
				}
			}
		}

		if (this.environment == null) {
			this.environment = new StandardEnvironment();
		}

		// Parse each @Configuration class
		// 解析@Configuration类
		ConfigurationClassParser parser = new ConfigurationClassParser(
				this.metadataReaderFactory, this.problemReporter, this.environment,
				this.resourceLoader, this.componentScanBeanNameGenerator, registry);

		Set<BeanDefinitionHolder> candidates = new LinkedHashSet<>(configCandidates);
		Set<ConfigurationClass> alreadyParsed = new HashSet<>(configCandidates.size());
		do {
			parser.parse(candidates);
			parser.validate();

			Set<ConfigurationClass> configClasses = new LinkedHashSet<>(parser.getConfigurationClasses());
			configClasses.removeAll(alreadyParsed);

			// Read the model and create bean definitions based on its content
			if (this.reader == null) {
				this.reader = new ConfigurationClassBeanDefinitionReader(
						registry, this.sourceExtractor, this.resourceLoader, this.environment,
						this.importBeanNameGenerator, parser.getImportRegistry());
			}
			this.reader.loadBeanDefinitions(configClasses);
			alreadyParsed.addAll(configClasses);

			candidates.clear();
			if (registry.getBeanDefinitionCount() > candidateNames.length) {
				String[] newCandidateNames = registry.getBeanDefinitionNames();
				Set<String> oldCandidateNames = new HashSet<>(Arrays.asList(candidateNames));
				Set<String> alreadyParsedClasses = new HashSet<>();
				for (ConfigurationClass configurationClass : alreadyParsed) {
					alreadyParsedClasses.add(configurationClass.getMetadata().getClassName());
				}
				for (String candidateName : newCandidateNames) {
					if (!oldCandidateNames.contains(candidateName)) {
						BeanDefinition bd = registry.getBeanDefinition(candidateName);
						if (ConfigurationClassUtils.checkConfigurationClassCandidate(bd, this.metadataReaderFactory) &&
								!alreadyParsedClasses.contains(bd.getBeanClassName())) {
							candidates.add(new BeanDefinitionHolder(bd, candidateName));
						}
					}
				}
				candidateNames = newCandidateNames;
			}
		}
		while (!candidates.isEmpty());

		// Register the ImportRegistry as a bean in order to support ImportAware @Configuration classes
		if (sbr != null && !sbr.containsSingleton(IMPORT_REGISTRY_BEAN_NAME)) {
			sbr.registerSingleton(IMPORT_REGISTRY_BEAN_NAME, parser.getImportRegistry());
		}

		if (this.metadataReaderFactory instanceof CachingMetadataReaderFactory) {
			// Clear cache in externally provided MetadataReaderFactory; this is a no-op
			// for a shared cache since it'll be cleared by the ApplicationContext.
			((CachingMetadataReaderFactory) this.metadataReaderFactory).clearCache();
		}
	}
```

## excludeFilters： 默认有三个

### 1.@SpringBootApplication注解自带的两个excludeFilter。
``` 
TypeExcludeFilter：从beanFactory中获取所有TypeExcludeFilter实例委托。
AutoConfigurationExcludeFilter: 获取同时被@Configuration和@EnableAutoConfiguration注解修饰的类。

@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
```
### 2.过滤掉启动类
``` 
org.springframework.context.annotation.ComponentScanAnnotationParser.parse: 126行

scanner.addExcludeFilter(new AbstractTypeHierarchyTraversingFilter(false, false) {
			@Override
			protected boolean matchClassName(String className) {
				return declaringClass.equals(className);
			}
		});
```

## includeFilters: 默认添加@Component(包含它的子注解: @Repository, @Service, @Controller)注解修饰的类的过滤器

### 1.创建ClassPathBeanDefinitionScanner扫描器类的时候默认会注册三个: @Component, @ManagedBean, @Named
``` 
public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters,
			Environment environment, @Nullable ResourceLoader resourceLoader) {

		Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
		this.registry = registry;

		if (useDefaultFilters) {
			registerDefaultFilters();
		}
		setEnvironment(environment);
		setResourceLoader(resourceLoader);
	}
	
	
	/**
	 * Register the default filter for {@link Component @Component}.
	 * <p>This will implicitly register all annotations that have the
	 * {@link Component @Component} meta-annotation including the
	 * {@link Repository @Repository}, {@link Service @Service}, and
	 * {@link Controller @Controller} stereotype annotations.
	 * <p>Also supports Java EE 6's {@link javax.annotation.ManagedBean} and
	 * JSR-330's {@link javax.inject.Named} annotations, if available.
	 *
	 */
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



























































































