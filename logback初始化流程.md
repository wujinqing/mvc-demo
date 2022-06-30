# logback初始化流程

> 1.执行SpringApplication.run()方法，在prepareEnvironment()方法中创建Environment，发布ApplicationEnvironmentPreparedEvent事件。

> 2.事件监听器类LoggingApplicationListener(他的顺序是Ordered.HIGHEST_PRECEDENCE + 20，ConfigFileApplicationListener的顺序是Ordered.HIGHEST_PRECEDENCE + 10)接收到ApplicationEnvironmentPreparedEvent事件，执行initialize方法初始化Logback。

> 3.执行LogbackLoggingSystem.initialize()方法。

> 4.在AbstractLoggingSystem.initializeWithConventions()方法中查找标准配置文件或者spring的配置文件。

> 5.通过SpringBootJoranConfigurator来解析logback-spring.xml文件。


## StandardEncryptableServletEnvironment和StandardEncryptableEnvironment解决logback-spring.xml无法解密问题
> springApplication.setEnvironment();替换掉默认的Environment。


### 查找配置文件
``` 
org.springframework.boot.logging.AbstractLoggingSystem.initializeWithConventions

private void initializeWithConventions(LoggingInitializationContext initializationContext, LogFile logFile) {
		// 查找自带的配置文件
		String config = getSelfInitializationConfig();
		if (config != null && logFile == null) {
			// self initialization has occurred, reinitialize in case of property changes
			reinitialize(initializationContext);
			return;
		}
		if (config == null) {
		    // 查找spring的配置文件
			config = getSpringInitializationConfig();
		}
		if (config != null) {
		// 加载配置文件
			loadConfiguration(initializationContext, config, logFile);
			return;
		}
		loadDefaults(initializationContext, logFile);
	}
	
	protected String getSelfInitializationConfig() {
		return findConfig(getStandardConfigLocations());
	}
	
	自带配置文件
	org.springframework.boot.logging.logback.LogbackLoggingSystem.getStandardConfigLocations
	protected String[] getStandardConfigLocations() {
		return new String[] { "logback-test.groovy", "logback-test.xml", "logback.groovy", "logback.xml" };
	}
```

### 标准配置文件

``` 
protected String[] getStandardConfigLocations() {
		return new String[] { "logback-test.groovy", "logback-test.xml", "logback.groovy", "logback.xml" };
	}
```
### 查找spring配置文件，标准配置文件+“-spring.”
```
protected String[] getSpringConfigLocations() {
		String[] locations = getStandardConfigLocations();
		for (int i = 0; i < locations.length; i++) {
			String extension = StringUtils.getFilenameExtension(locations[i]);
			locations[i] = locations[i].substring(0, locations[i].length() - extension.length() - 1) + "-spring."
					+ extension;
		}
		return locations;
	}
```

### 解析logback-spring.xml配置文件
``` 
org.springframework.boot.logging.logback.LogbackLoggingSystem.configureByResourceUrl

private void configureByResourceUrl(LoggingInitializationContext initializationContext, LoggerContext loggerContext,
			URL url) throws JoranException {
		if (url.toString().endsWith("xml")) {
			JoranConfigurator configurator = new SpringBootJoranConfigurator(initializationContext);
			configurator.setContext(loggerContext);
			configurator.doConfigure(url);// 解析logback-spring.xml配置文件
		}
		else {
			new ContextInitializer(loggerContext).configureByResource(url);
		}
	}
```




























