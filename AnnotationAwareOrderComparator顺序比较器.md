### AnnotationAwareOrderComparator 顺序

### 1.实现了PriorityOrdered的优先。

### 2.是否实现了Ordered接口，从该接口的getOrder()获取排序整数。

### 3.是否被@Order注解修饰，从该注解中获取排序整数。

### 4.是否被@Priority注解修饰，从该注解中获取排序整数。

### 5.默认Ordered.LOWEST_PRECEDENCE最低顺序。


















