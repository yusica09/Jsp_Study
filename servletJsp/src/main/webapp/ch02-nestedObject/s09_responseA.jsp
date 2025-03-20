<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>s09_responseA.jsp</title>
</head>
<body>
현재 페이지는 s09_responseA.jsp입니다. 화면에 보여지지 않습니다.
</body>
</html>
<%
	//html 태그 없이도 Redirect사용가능
	response.sendRedirect("s10_responseB.jsp");
%>