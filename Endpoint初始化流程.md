# Endpoint初始化流程

## 引入依赖
``` 
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

## EndpointDiscoverer 用来发现并注册Endpoint

## 自动配置
> WebMvcEndpointManagementContextConfiguration

> WebEndpointAutoConfiguration

## 配置类
> WebEndpointProperties

## 配置

``` 
management.endpoints.web.exposure.include=*
management.endpoints.web.exposure.exclude=*
```
## Endpoint注解

|注解||
|---|---|
|Endpoint||
|ServletEndpoint||
|ControllerEndpoint||
|RestControllerEndpoint||
|||
|||





``` 
@ReadOperation
@WriteOperation
@DeleteOperation
```






































































































































































































































