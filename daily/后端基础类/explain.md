你这个 `/home/wly/ClickGood/src/main/java/com/wly` 目录下面，现在是一套比较典型的 Spring Boot 后端基础骨架。

整体关系可以这样看：

```text
ClickGoodApplication
        |
        v
Spring Boot 启动整个项目
        |
        v
Controller 接收请求
        |
        v
返回 BaseResponse 统一响应
        |
        v
如果出错，抛 BusinessException
        |
        v
GlobalExceptionHandler 统一捕获异常
        |
        v
ResultUtils 生成统一错误响应
```

## 1. 启动入口

文件：

```text
ClickGoodApplication.java
```

核心代码：

```java
@SpringBootApplication
public class ClickGoodApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClickGoodApplication.class, args);
    }
}
```

这是项目启动入口。

`@SpringBootApplication` 会告诉 Spring Boot：从这里开始扫描组件，比如 `controller`、`config`、`exception` 等包里的类。

为什么这样写？

因为 Spring Boot 项目需要一个启动类。运行这个 `main` 方法后，整个 Web 服务才会启动。

## 2. controller：接收前端请求

文件：

```text
controller/Health.java
```

代码：

```java
@RestController
@RequestMapping("/")
public class Health {

    @GetMapping("/health")
    public BaseResponse<String> health() {
        return ResultUtils.success("ok");
    }
}
```

这个类负责提供接口。

访问：

```text
GET /health
```

会返回：

```json
{
  "code": 0,
  "data": "ok",
  "message": "ok"
}
```

为什么不直接返回 `"ok"`？

因为项目想统一返回格式。前端不用猜这个接口返回字符串、那个接口返回对象、另一个接口返回错误结构。全部都长这样：

```json
{
  "code": 状态码,
  "data": 数据,
  "message": 提示信息
}
```

## 3. common：通用对象和工具

### BaseResponse

文件：

```text
common/BaseResponse.java
```

它是统一响应包装类：

```java
public class BaseResponse<T> implements Serializable {
    private int code;
    private String message;
    private T data;
}
```

`T` 是泛型，表示 data 可以是任意类型。

比如：

```java
BaseResponse<String>
BaseResponse<User>
BaseResponse<List<User>>
```

为什么这样写？

因为不同接口返回的数据类型不一样，但外层结构一样。

比如健康检查返回字符串：

```java
BaseResponse<String>
```

查用户可能返回用户对象：

```java
BaseResponse<User>
```

查列表可能返回列表：

```java
BaseResponse<List<User>>
```

### ResultUtils

文件：

```text
common/ResultUtils.java
```

它是响应生成工具类。

成功时：

```java
ResultUtils.success("ok")
```

生成：

```java
new BaseResponse<>(0, data, "ok")
```

失败时：

```java
ResultUtils.error(ErrorCode.PARAMS_ERROR)
```

生成对应错误响应。

为什么这样写？

为了避免每个接口都手写：

```java
return new BaseResponse<>(0, data, "ok");
```

统一用：

```java
return ResultUtils.success(data);
```

代码更短，也更统一。

### DeleteRequest

文件：

```text
common/DeleteRequest.java
```

```java
private Long id;
```

用于删除请求。

比如前端传：

```json
{
  "id": 123
}
```

后端可以用它接收。

### PageRequest

文件：

```text
common/PageRequest.java
```

用于分页查询：

```java
private int current = 1;
private int pageSize = 10;
private String sortField;
private String sortOrder = "descend";
```

为什么这样写？

很多查询接口都需要分页，所以抽一个公共请求类，避免每个查询接口重复写这些字段。

## 4. exception：统一异常体系

这是这套代码里比较重要的一组。

### ErrorCode

文件：

```text
exception/ErrorCode.java
```

它是错误码枚举：

```java
PARAMS_ERROR(40000, "请求参数错误"),
NOT_LOGIN_ERROR(40100, "未登录"),
NO_AUTH_ERROR(40101, "无权限"),
SYSTEM_ERROR(50000, "系统内部异常")
```

为什么这样写？

为了把常见错误统一管理。

否则代码里可能到处写：

```java
40000
"请求参数错误"
50000
"系统错误"
```

以后很难维护。

用了 `ErrorCode` 后，可以写：

```java
ErrorCode.PARAMS_ERROR
```

含义更清楚。

### BusinessException

文件：

```text
exception/BusinessException.java
```

它是业务异常：

```java
public class BusinessException extends RuntimeException {
    private final int code;
}
```

业务异常就是：不是 Java 程序崩了，而是业务上不允许继续。

比如：

```java
throw new BusinessException(ErrorCode.PARAMS_ERROR);
```

表示“请求参数错误”。

它里面有两个核心信息：

```java
super(message);
this.code = code;
```

`super(message)` 把错误信息交给父类 `RuntimeException`。

`this.code = code` 保存业务错误码。

为什么继承 `RuntimeException`？

因为业务异常通常不想每层都强制 `try-catch`，而是直接抛出去，交给全局异常处理器统一处理。

### ThrowUtils

文件：

```text
exception/ThrowUtils.java
```

它是抛异常工具类。

比如你原本要写：

```java
if (id == null) {
    throw new BusinessException(ErrorCode.PARAMS_ERROR);
}
```

可以简化成：

```java
ThrowUtils.throwIf(id == null, ErrorCode.PARAMS_ERROR);
```

为什么这样写？

为了让参数校验更简洁。

尤其以后代码多了，经常会出现这种判断：

```java
if (xxx) {
    throw new BusinessException(...);
}
```

抽成工具类后更统一。

### GlobalExceptionHandler

文件：

```text
exception/GlobalExceptionHandler.java
```

这是全局异常处理器：

```java
@RestControllerAdvice
public class GlobalExceptionHandler
```

它负责捕获 Controller 抛出来的异常。

处理业务异常：

```java
@ExceptionHandler(BusinessException.class)
public BaseResponse<?> BusinessExceptionHandler(BusinessException e) {
    return ResultUtils.error(e.getCode(), e.getMessage());
}
```

处理普通运行时异常：

```java
@ExceptionHandler(RuntimeException.class)
public BaseResponse<?> RuntimeExceptionHandler(RuntimeException e) {
    return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统运行异常");
}
```

为什么这样写？

这样 Controller 里不用到处写 `try-catch`。

业务代码可以直接：

```java
throw new BusinessException(ErrorCode.PARAMS_ERROR);
```

最终会被 `GlobalExceptionHandler` 捕获，并返回统一 JSON：

```json
{
  "code": 40000,
  "data": null,
  "message": "请求参数错误"
}
```

## 5. config：项目配置

文件：

```text
config/CorConfig.java
```

这个类配置跨域 CORS：

```java
registry.addMapping("/**")
        .allowCredentials(true)
        .allowedOriginPatterns("*")
        .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
        .allowedHeaders("*")
        .exposedHeaders("*");
```

它的作用是允许前端跨域访问后端接口。

比如前端运行在：

```text
http://localhost:5173
```

后端运行在：

```text
http://localhost:8080
```

这就是跨域。没有 CORS 配置时，浏览器可能会拦截请求。

不过这个类目前少了一个注解：

```java
@Configuration
```

现在写成这样：

```java
public class CorConfig implements WebMvcConfigurer
```

Spring Boot 不一定会把它当成配置类加载。通常应该写成：

```java
@Configuration
public class CorConfig implements WebMvcConfigurer
```

另外文件名是 `CorConfig`，更常见的名字是 `CorsConfig`，少了一个 `s`，不影响运行，但命名上可以更准确。

## 总结

你现在这套代码主要是在搭后端项目的“基础设施”：

```text
ClickGoodApplication    启动项目
controller              接收请求
common                  统一响应、通用请求对象
exception               错误码、业务异常、全局异常处理
config                  跨域配置
```

为什么要这样分层？

因为后端项目以后会越来越大。如果所有东西都写在 Controller 里，会很乱。现在这样写，每一层职责清楚：

```text
Controller 只负责接口
ResultUtils 只负责生成响应
ErrorCode 只负责管理错误码
BusinessException 只表示业务错误
GlobalExceptionHandler 只负责统一处理异常
```

这就是 Spring Boot 项目里很常见、也很实用的一套写法。