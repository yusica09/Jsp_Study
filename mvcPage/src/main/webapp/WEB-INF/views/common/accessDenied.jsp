<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>안내</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css" type="text/css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>안내</h2>
		<div class="result-display">
			<div class="align-center">
				잘못된 접속입니다.<p>
				<input type="button" value="홈으로"
				  onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			</div>
		</div>
	</div>
</div>
</body>
</html>




