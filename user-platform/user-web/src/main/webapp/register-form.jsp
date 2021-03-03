<%--
  Created by IntelliJ IDEA.
  User: xunqirui
  Date: 2021/3/1
  Time: 10:30 下午
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <jsp:directive.include file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
    <title>My Home Page</title>
    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>
</head>
<body>
<div style="margin-left: 45%; margin-top: 10%;">
    <form action="<%request.getContextPath();%>/register" method="post">
        <label>用户名:</label>
        <input type="text" name="name" placeholder="请输入用户名" required/>
        <br/>
        <label>密码：</label>
        <input type="password" name="password" placeholder="请输入密码" required/>
        <br/>
        <label>邮箱地址：</label>
        <input type="text" name="email" placeholder="请输入邮箱地址" required>
        <br/>
        <label>手机号码：</label>
        <input type="text" name="phoneNumber" placeholder="请输入手机号码" required>

        <div>
            <button style="width: 200px;height: 50px;" type="submit" >注册</button>
        </div>
    </form>
</div>


</body>
</html>
