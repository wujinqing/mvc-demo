# refreshContext刷新上下文

> 1.在prepareBeanFactory方法中将ApplicationContextAwareProcessor添加到bean工厂的BeanPostProcessor列表中，用来装配各种实现了Aware接口的bean。

> 2.在prepareBeanFactory方法中将ApplicationListenerDetector添加到bean工厂的BeanPostProcessor列表中，用来检测实现了ApplicationListener接口的bean。

> 3.在prepareBeanFactory方法中将environment、systemProperties、systemEnvironment注册到bean工厂中。

> 4.在postProcessBeanFactory方法中将WebApplicationContextServletContextAwareProcessor添加到bean工厂的BeanPostProcessor列表中.

> 5.在postProcessBeanFactory方法中注册各种对象RequestScope，SessionScope，ServletContextScope，RequestObjectFactory，ResponseObjectFactory，SessionObjectFactory，WebRequestObjectFactory.

> 6.在invokeBeanFactoryPostProcessors方法中执行所有的BeanFactoryPostProcessor 和 BeanDefinitionRegistryPostProcessor。


## ConfigurationClassPostProcessor是一个BeanDefinitionRegistryPostProcessor用来扫描@Configuration、@Component、@ComponentScan、@Import、@ImportResource、@Bean修饰的各种bean。

## invokeBeanFactoryPostProcessors执行BeanFactoryPostProcessor



### ApplicationContextAwareProcessor用来给实现了下列接口的类传递ApplicationContext

### ServletContextAwareProcessor 或者WebApplicationContextServletContextAwareProcessor

### 各种Aware
|Aware|设置类|
|---|---|
|EnvironmentAware|ApplicationContextAwareProcessor|
|EmbeddedValueResolverAware|ApplicationContextAwareProcessor|
|ResourceLoaderAware|ApplicationContextAwareProcessor|
|ApplicationEventPublisherAware|ApplicationContextAwareProcessor|
|MessageSourceAware|ApplicationContextAwareProcessor|
|ApplicationContextAware|ApplicationContextAwareProcessor|
|ServletContextAware|ServletContextAwareProcessor|
|ServletConfigAware|ServletContextAwareProcessor|
|||
|||


## ConfigurationClassPostProcessor 用来@Configuration的BeanFactoryPostProcessor
> SpringApplication.createApplicationContext -> AnnotatedBeanDefinitionReader构造方法 -> 调用AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);方法设置。


### BeanFactoryPostProcessor 和 BeanDefinitionRegistryPostProcessor



### 被@Configuration注解修饰的类是FullConfigurationCandidate, 被@Component、@ComponentScan、@Import、@ImportResource、@Bean注解修饰的类是LiteConfigurationCandidate




### ConfigurationClassParser 用来解析@Configuration注解
### ComponentScanAnnotationParser 用来解析@ComponentScan注解
### ConditionEvaluator 用来计算@Conditional注解

### ClassPathBeanDefinitionScanner 用来扫描被@Component(包含它的子注解: @Repository, @Service, @Controller), @ManagedBean, @Named注解修饰的类。

``` 实例化ConfigurationClassParser，同时会实例化ComponentScanAnnotationParser和ConditionEvaluator

	public ConfigurationClassParser(MetadataReaderFactory metadataReaderFactory,
			ProblemReporter problemReporter, Environment environment, ResourceLoader resourceLoader,
			BeanNameGenerator componentScanBeanNameGenerator, BeanDefinitionRegistry registry) {

		this.metadataReaderFactory = metadataReaderFactory;
		this.problemReporter = problemReporter;
		this.environment = environment;
		this.resourceLoader = resourceLoader;
		this.registry = registry;
		this.componentScanParser = new ComponentScanAnnotationParser(
				environment, resourceLoader, componentScanBeanNameGenerator, registry);
		this.conditionEvaluator = new ConditionEvaluator(registry, environment, resourceLoader);
	}
```


## bean扫描排除过滤器(excludeFilter): TypeExcludeFilter、AutoConfigurationExcludeFilter、AbstractTypeHierarchyTraversingFilter
> 1.TypeExcludeFilter从beanFactory获取自定义的TypeExcludeFilter过滤器。

> 2.AutoConfigurationExcludeFilter用来过滤被@Configuration注解修饰的并且定义在在META-INF/spring.factories文件中的key为org.springframework.boot.autoconfigure.EnableAutoConfiguration各种自动配置类。

> 3.AbstractTypeHierarchyTraversingFilter用来过滤掉com.jin.mvc.demo.Bootstrap启动类。

## bean扫描包含过滤器(includeFilters): @Component(含@Repository, @Service, @Controller)、@ManagedBean, @Named 包含被这三个注解修饰的类。

### includeFilters: 
> 1.默认添加@Component(包含它的子注解: @Repository, @Service, @Controller)注解修饰的类的过滤器

> 2.默认添加@ManagedBean注解修饰的类的过滤器

> 3.默认添加@Named注解修饰的类的过滤器

## @PropertySources、@PropertySource、@ComponentScans、@ComponentScan、@Import、@ImportResource、@Bean 都是在ConfigurationClassParser类里面解析的


## AutoConfigurationImportSelector用来处理自动配置@EnableAutoConfiguration。


## ImportSelector、ImportBeanDefinitionRegistrar


## this.reader.loadBeanDefinitions(configClasses);从被注解@Configuration修饰的类里面的@Bean修饰的方法里面解析BeanDefinition。

``` 

org.springframework.context.annotation.ConfigurationClassPostProcessor.processConfigBeanDefinitions

this.reader.loadBeanDefinitions(configClasses);

```

## PropertySourcesPlaceholderConfigurer是一个BeanFactoryPostProcessor用来解析占位符"${}"，从Environment的propertySources里面获取对应的值。

``` 
org.springframework.core.env.PropertySourcesPropertyResolver.getProperty(java.lang.String, java.lang.Class<T>, boolean)

protected <T> T getProperty(String key, Class<T> targetValueType, boolean resolveNestedPlaceholders) {
		if (this.propertySources != null) {
			for (PropertySource<?> propertySource : this.propertySources) {
				if (logger.isTraceEnabled()) {
					logger.trace("Searching for key '" + key + "' in PropertySource '" +
							propertySource.getName() + "'");
				}
				Object value = propertySource.getProperty(key);
				if (value != null) {
					if (resolveNestedPlaceholders && value instanceof String) {
						value = resolveNestedPlaceholders((String) value);
					}
					logKeyFound(key, propertySource, value);
					return convertValueIfNecessary(value, targetValueType);
				}
			}
		}
		if (logger.isTraceEnabled()) {
			logger.trace("Could not find key '" + key + "' in any property source");
		}
		return null;
	}


```

### 可以用占位符"${}"的地方
> 1.ParentName.

> 2.BeanClassName

> 3.FactoryBeanName

> 4.FactoryMethodName

> 5.Scope

> 6.PropertyValue

> 7.ConstructorArgumentValue

> 8.Alias




``` 
public void visitBeanDefinition(BeanDefinition beanDefinition) {
		visitParentName(beanDefinition);
		visitBeanClassName(beanDefinition);
		visitFactoryBeanName(beanDefinition);
		visitFactoryMethodName(beanDefinition);
		visitScope(beanDefinition);
		if (beanDefinition.hasPropertyValues()) {
			visitPropertyValues(beanDefinition.getPropertyValues());
		}
		if (beanDefinition.hasConstructorArgumentValues()) {
			ConstructorArgumentValues cas = beanDefinition.getConstructorArgumentValues();
			visitIndexedArgumentValues(cas.getIndexedArgumentValues());
			visitGenericArgumentValues(cas.getGenericArgumentValues());
		}
	}
	



org.springframework.beans.factory.config.PlaceholderConfigurerSupport.doProcessProperties

	
		// New in Spring 2.5: resolve placeholders in alias target names and aliases as well.
		beanFactoryToProcess.resolveAliases(valueResolver);

		// New in Spring 3.0: resolve placeholders in embedded values such as annotation attributes.
		beanFactoryToProcess.addEmbeddedValueResolver(valueResolver);

	
	
```


## registerBeanPostProcessors(beanFactory);找出所有的BeanPostProcessor，并添加到beanFactory中。

## initMessageSource();初始化messageSource。

## initApplicationEventMulticaster();初始化应用事件广播

## onRefresh(); 初始化主题：ThemeSource及创建TomcatWebServer。
> 从beanFactory中获取WebServerFactoryCustomizer实例，然后从ServerProperties（ServletWebServerFactoryCustomizer）中读取配置设置TomcatWebServer。

## registerListeners();将应用上下文中的监听器添加到上面初始化的广播中，从bean工厂中获取ApplicationListener类型的实例也添加到广播中。


## finishBeanFactoryInitialization(beanFactory); 初始化剩余的所有非延迟加载的单例。
> Instantiate all remaining (non-lazy-init) singletons.


# bean的初始化

|接口名|方法|声明|
|---|---|---|
|InstantiationAwareBeanPostProcessor|postProcessBeforeInstantiation|实例化之前执行, 这一步可以生成代理bean|
|BeanPostProcessor|postProcessAfterInitialization|如果上一步返回了代理bean，这执行这个操作，执行完直接返回不会执行后续操作|
|InstantiationAwareBeanPostProcessor|postProcessAfterInstantiation|实例化之后执行|
|执行autowire|||
|InstantiationAwareBeanPostProcessor|postProcessProperties|处理对象属性，注入@Autowired、@Value等属性值（通过AutowiredAnnotationBeanPostProcessor这个类处理）|
|设置BeanNameAware、BeanClassLoaderAware、BeanFactoryAware|||
|BeanPostProcessor|postProcessBeforeInitialization|在InitializingBean的初始化方法执行之前调用,如通过ApplicationContextAwareProcessor类设置（EnvironmentAware，EmbeddedValueResolverAware，ResourceLoaderAware，ApplicationEventPublisherAware，MessageSourceAware，ApplicationContextAware）；通过ServletContextAwareProcessor类设置（ServletContextAware，ServletConfigAware）；装配被@ConfigurationProperties修饰的类；对WebServerFactory执行自定义WebServerFactoryCustomizer配置|
|InitializingBean|afterPropertiesSet|执行初始化操作|
|BeanPostProcessor|postProcessAfterInitialization|在InitializingBean的初始化方法执行之后调用|
||||
||||
||||
||||





``` 

org.springframework.context.support.ApplicationContextAwareProcessor.invokeAwareInterfaces


private void invokeAwareInterfaces(Object bean) {
		if (bean instanceof Aware) {
			if (bean instanceof EnvironmentAware) {
				((EnvironmentAware) bean).setEnvironment(this.applicationContext.getEnvironment());
			}
			if (bean instanceof EmbeddedValueResolverAware) {
				((EmbeddedValueResolverAware) bean).setEmbeddedValueResolver(this.embeddedValueResolver);
			}
			if (bean instanceof ResourceLoaderAware) {
				((ResourceLoaderAware) bean).setResourceLoader(this.applicationContext);
			}
			if (bean instanceof ApplicationEventPublisherAware) {
				((ApplicationEventPublisherAware) bean).setApplicationEventPublisher(this.applicationContext);
			}
			if (bean instanceof MessageSourceAware) {
				((MessageSourceAware) bean).setMessageSource(this.applicationContext);
			}
			if (bean instanceof ApplicationContextAware) {
				((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
			}
		}
	}

```



## finishRefresh();完成刷新
> 1.执行Lifecycle的start方法。

> 2.发布ContextRefreshedEvent事件。

> 3.webServer.start();启动Tomcat。

> 4.发布ServletWebServerInitializedEvent事件。
















































































































































































