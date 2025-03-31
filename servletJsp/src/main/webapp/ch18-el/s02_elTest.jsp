<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL</title>
</head>
<body>
<form action="s02_elTest.jsp" method="post">
	이름 : <input type="text" name="name" placeholder="이름 입력"><br>
	<input type="submit" value="확인">
</form>
<br>
이름은 <%= request.getParameter("name") %><br>
<%-- EL의 경우 null이 아닌 빈문자열로 대체함. --%>
이름은 ${param.name}<br> 
이름은 ${param["name"]}<br> 
</body>
</html>