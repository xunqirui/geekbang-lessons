# 第一周作业
- 通过自研 Web MVC 框架实现（可以自己实现）一个用户 注册，forward 到一个成功的页面（JSP 用法）
    - /register
- 通过 Controller -> Service -> Repository 实现（数据库实 现）
- （非必须）JDNI 的方式获取数据库源（DataSource）， 在获取 Connection

## 说明
- 均已完成，
- 注册页面 http://localhost:8080/register/page
- jndi 获取的 dataSource 通过 servletContext 监听器 org.geektimes.projects.user.web.listener.DBConnectionInitializerListener 注入到 DBConnectionManager 中