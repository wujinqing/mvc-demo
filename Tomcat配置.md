# Tomcat配置
> 配置类：org.springframework.boot.autoconfigure.web.ServerProperties

> https://tomcat.apache.org/tomcat-8.5-doc/config/http.html

> 默认的http协议：org.apache.coyote.http11.Http11NioProtocol

## Tomcat配置项
|配置项|说明|
|---|---|
|server.tomcat.min-spare-threads|最小工作线程数，默认10|
|server.tomcat.max-threads|最大工作线程数，默认200|
|server.tomcat.max-swallow-size|允许的最大请求体的大小， 默认2M|
|server.tomcat.max-connections|允许接收并处理的最大请求数，默认8192|
|server.tomcat.accept-count|当请求数达到max-connections之后，继续存放在队列里面等待处理的请求数，默认100个|

|||
|||




























