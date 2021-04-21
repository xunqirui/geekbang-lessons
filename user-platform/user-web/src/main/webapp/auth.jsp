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
	<div class="container">
		<a href="https://gitee.com/oauth/authorize?client_id=80714c350860ef865076c17d7f023a96c787f1ede32b5c19548554fbc489624c&redirect_uri=http://127.0.0.1:8080/callback&response_type=code&scope=user_info">
			码云登录
		</a>
	</div>
</body>