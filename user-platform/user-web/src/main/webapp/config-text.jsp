<head>
    <jsp:directive.include
            file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
    <title>My Home Page</title>
</head>
<body>
<div class="container-lg">
    <!-- Content here -->
    当前 application.name 为
    <%=request.getAttribute("application.name")%>
</div>
</body>