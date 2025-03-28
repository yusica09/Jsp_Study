<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 관리 메인</title>
<link rel="stylesheet" href="../css/style.css"  type="text/css">
</head>
<body>
<%
	String user_id = (String)session.getAttribute("user_id");
%>
<div class="page-main">
	<h1>사원관리 메인</h1>
	<div class="align-right">
		<a href="list.jsp">사원게시판</a>
		<%
			if(user_id == null){
		%>
			<a href="registerUserForm.jsp">회원가입</a>
			<a href="loginForm.jsp">로그인</a>
		<%
			}else{
		%>
			<a href="myPage.jsp">MyPage</a>
			[<b><%= user_id %></b>님 로그인 중]
			<a href="logout.jsp">로그아웃</a>
		<%
			}
		%>
	</div>
</div>

</body>
</html>