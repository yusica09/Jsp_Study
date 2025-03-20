<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>요청 파라미터 출력</title>
</head>
<body>
<h3>request.getParameter() 메서드 사용</h3>
name 파라미터 = <%= request.getParameter("name") %><br>
address 파라미터 = <%= request.getParameter("address") %><br>

<h3>request.getParameterValues() 메서드 사용</h3>
<%
	String[] values = request.getParameterValues("pet");
	if(values != null){
		for(int i=0;i<values.length;i++){
%>			
		<%= values[i] %>
<%	
		}	
	}
%>
</body>
</html>