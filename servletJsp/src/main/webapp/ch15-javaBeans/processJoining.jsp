<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.web.member.MemberVO" %>

<%
	//자바빈 생성
	//전송된 데이터를 request로부터 반환 받아서 setter를 이용해
	//자바빈의 프로퍼티에 저장
	MemberVO member = new MemberVO();
	member.setId(request.getParameter("id"));
	member.setPassword(request.getParameter("password"));
	member.setName(request.getParameter("name"));
	member.setEmail(request.getParameter("email"));
	member.setAddress(request.getParameter("address"));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>