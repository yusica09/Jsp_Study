<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page buffer="1kb" autoFlush="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>autoFlush 속성값 true 예제</title>
</head>
<body>
<%
	//autoFlush로 인해 전부 출력가능
	for(int i=0;i<1000;i++){
%>
		1234
<% 
	}
%>
</body>
</html>