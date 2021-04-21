<head>
<jsp:directive.include
	file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
<title>My Home Page</title>
</head>
<body>
	<div class="container-lg">
		<!-- Content here -->
		Oauth2 认证成功
		当前 accessToken 为
		<%=request.getAttribute("accessToken")%>
	</div>
</body>