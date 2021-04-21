# 第一周作业（2021-03-03）
- 通过自研 Web MVC 框架实现（可以自己实现）一个用户 注册，forward 到一个成功的页面（JSP 用法）
    - /register
- 通过 Controller -> Service -> Repository 实现（数据库实 现）
- （非必须）JDNI 的方式获取数据库源（DataSource）， 在获取 Connection

## 说明
- 均已完成，
- 注册页面 http://localhost:8080/register/page
- jndi 获取的 dataSource 通过 servletContext 监听器 org.geektimes.projects.user.web.listener.ComponentContextInitializerListener 注入到 DBConnectionManager 中


# 第二周作业（2021-03-10）
通过课堂上的简易版依赖注入和依赖查找，实现用户注册功能
- 通过 UserService 实现用户注册
- 注册用户需要校验
  - Id：必须大于 0 的整数
  - 密码：6-32 位
  - 电话号码：采用中国大陆方式（11 位校验）
  
## 完成情况说明
- 所有要求均已完成
- 实现说明
  - 将 ComponentContext 放入了 my-web-mvc 模块，通过在 context.xml 里注入的模式，实现了 Controller、Service、Repository 依赖注入和依赖查找的功能。
  - 由于 ServletContextListener 的执行顺序是在调用自定义 servlet init 方法之前，所以修改了 FrontControllerServlet 类的初始化方法，之前是通过 spi 的方式来获取 controller 再做相关的映射，现改为通过 ComponentContext 来获取 Controller 实现类
  - 校验的时候，如果数据不符合，会返回一个错误页面，并将错误的消息文案打印在游览器页面上
  - 用户注册方法之前通过 jdbc 调用，现改为通过 jpa 来进行存储，并通过是否返回了 id 来判断注册是否成功
- 注册页面地址：http://localhost:8080/register/page

# 第三周作业（2021-03-17）
- 需求一
  - 整合 https://jolokia.org/
    - 实现一个自定义 JMX MBean，通过 Jolokia 做 Servlet 代理

- 需求二
  - 继续完成 Microprofile config API 中的实现
    - 扩展 org.eclipse.microprofile.config.spi.ConfigSource 实现，包括 OS 环境变量，以及本地配置文件
    - 扩展 org.eclipse.microprofile.config.spi.Converter 实现，提供 String 类型到简单类型
  - 通过 org.eclipse.microprofile.config.Config 读取当前 应用名称
    - 应用名称 property name = "application.name"

## 完成情况说明
上述需求均已完成，对于需求一，我的理解是引入 jolokia 之后，能够通过 http 请求可以访问并管理自己注入的 bean

1. 需求一 相关类
   
   org.geektimes.projects.user.management.UserInfo
   org.geektimes.projects.user.management.UserInfoManageMBean
   org.geektimes.projects.user.management.UserInfoManage
   
2. 需求二 相关类
    
    均在 org.geektimes.configuration.microprofile 下

3. 实现方式
  
    通过 TestListener 注入 MBean，访问地址：http://localhost:8080/jolokia/read/org.geektimes.projects.user.management:type=UserInfo
    
    通过 TestListener 读取 application_name
   
# 第四周作业（2021-03-24）
1. 完善 my dependency-injection 模块
    - 脱离 web.xml 配置实现 ComponentContext 自动初始化
    - 使用独立模块并且能够在 user-web 中运行成功
    
2. 完善 my-configuration 模块
    - Config 对象如何能被 my-web-mvc 使用
        - 可能在 ServletContext 获取
        - 如何通过 ThreadLocal 获取

3. 去提前阅读 Servlet 规范中 Security 章节（Servlet 容器安 全）

## 完成情况说明
作业一已经完成，作业二的通过 ThreadLocal 获取不太清楚，没有实现，获取 config 方式，通过增加了一个 ConfigController 方法返回当前获取到的 application.name，作业三已完成

# 第五周作业（2021-03-31）
1. 复本程序 org.geektimes.reactive.streams 包下的打印逻辑问题
   
2. 继续完善 my-rest-client POST 方法
    
3. （可选）读一下 Servlet 3.0 关于 Servlet 异步
    - AsyncContext
## 完成情况说明
1. 第一个作业
   
   - org.geektimes.reactive.streams.BusinessSubscriber#onNext 将打印方法上移至方法第一行，即可打印出所有的循环的值

2. 第二个作业

    - my-rest-client 模块中增加一个类 org.geektimes.rest.client.HttpPostInvocation，里面调用 post 方法，支持 json 和 form 表单两种模式
    - my-rest-client 的 test 中 org.geektimes.rest.demo.RestClientDemo 类写了对应的 post 请求测试方法，需要启动 my-web-mvc 模块对应的 jar 包之后才能进行测试
    - 修改了 my-web-mvc 模块中的 org.geektimes.web.mvc.FrontControllerServlet#service() 方法，提供了 RestController 的支持，为 post 方法调用时返回对应实体类而不返回页面提供接口支持
    - 在 user-web 模块中增加了一个 org.geektimes.projects.user.web.controller.UserRestController 类，来提供 Rest 请求的接口
3. 第三个作业

    - 还未开始阅读，争取在老师上课前读一遍
    
# 第六周作业（2021-04-14）
1. my-cache 模块

    - 提供一套抽象 API 实现对象的序列化和反序列化 
    - 通过 Lettuce 实现一套 Redis CacheManager 以及 Cache

## 完成情况说明
- 抽象序列化 API 见my-serialize 模块
- Lettuce 实现见 my-cache 中的 org.geektimes.cache.redis.LettuceCache 和 org.geektimes.cache.redis.LettuceCacheManager
    
# 第七周作业（2021-04-21）
1. 使用 Spring Boot 或 servlet 来实现一个整合Gitee/或者Github OAuth2 认证

## 作业完成情况
使用 servlet 进行了实现，认证 controller 在 org.geektimes.projects.user.web.controller.OauthController，由于最近工作繁忙，没有做用户与 token 之间的绑定策略，只是在前端页面显示了 accessToken

访问地址为 http://127.0.0.1/auth