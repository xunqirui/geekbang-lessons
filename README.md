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