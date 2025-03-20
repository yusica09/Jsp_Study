<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[실습]회원가입</title>
</head>
<body>
이름 : <%=request.getParameter("name") %><br>
아이디 : <%=request.getParameter("id") %><br>
비밀번호 : <%=request.getParameter("password") %><br>
전화번호 : <%=request.getParameter("phone") %><br>
<%
	String [] hobby = request.getParameterValues("hobby");
	if(hobby != null){
%>			
취미 : 
<%-- 내가 작성한 아래 코드도 틀린 건 아닌데 굳이 저렇게하지말고 
		out.println()을 이용해서 더 가시성 좋게 적기... --%> 
<%  for(int i=0;i<hobby.length;i++){ 
		if(i != hobby.length-1){
%>
		<%= hobby[i] %>,
<% }else{%>
		<%= hobby[i] %><br>
<%		}
		}	
	}
%>
자기소개 : <%=request.getParameter("content") %>
</body>
</html>