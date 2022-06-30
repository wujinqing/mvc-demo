# ConfigFileApplicationListener读取自定义配置application.properties

## 1.收到ApplicationEnvironmentPreparedEvent事件。

## 2.从META-INF/spring.factories文件中读取并实例化所有EnvironmentPostProcessor对象，ConfigFileApplicationListener也是EnvironmentPostProcessor对象。

## 3.执行ConfigFileApplicationListener的postProcessEnvironment方法。

## 4.将RandomValuePropertySource添加到Environment的propertySources中。

## 5.创建Loader对象，构造方法中会创建placeholdersResolver和propertySourceLoaders。
> 默认会从META-INF/spring.factories文件中获取到PropertiesPropertySourceLoader和YamlPropertySourceLoader两个实例，用来加载application.properties和application.yml.

## 6.在Loader的构造方法中会从META-INF/spring.factories文件中读取并实例化所有PropertySourceLoader对象，用于从各个地方加载PropertySource。

## 7.默认配置文件搜索路径：DEFAULT_SEARCH_LOCATIONS = "classpath:/,classpath:/config/,file:./,file:./config/";

## 8.默认配置文件名称：DEFAULT_NAMES = "application";

## 9.用Loader的构造方法中获取的propertySourceLoaders对象，从DEFAULT_SEARCH_LOCATIONS目录中读取名字为DEFAULT_NAMES的配置文件(文件后缀由propertySourceLoaders指定)。

## 10.将所有加载到的PropertySource添加到Environment的propertySources中。

```
Loader(ConfigurableEnvironment environment, ResourceLoader resourceLoader) {
			this.environment = environment;
			this.placeholdersResolver = new PropertySourcesPlaceholdersResolver(this.environment);
			this.resourceLoader = (resourceLoader != null) ? resourceLoader : new DefaultResourceLoader();
			this.propertySourceLoaders = SpringFactoriesLoader.loadFactories(PropertySourceLoader.class,
					getClass().getClassLoader());
		}
```






