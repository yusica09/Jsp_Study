<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 4개 영역에 저장된 데이터 읽기</title>
</head>
<body>
<%
                            //속성명 속성값
	pageContext.setAttribute("msg", "봄");
	request.setAttribute("msg2", "여름");
	session.setAttribute("msg3", "가을");
	application.setAttribute("msg4", "겨울");
	
	String str = "여행";
	pageContext.setAttribute("pageStr", str);
%>

page Scope(영역) : ${pageScope.msg}, ${msg}<br>
request scope : ${requestScope.msg2}, ${msg2}<br>
session scope : ${sessionScope.msg3}, ${msg3}<br>
application scope : ${applicationScope.msg4}, ${msg4}<br>
<%-- 
스크립트릿에 정의한 변수의 값은 EL이 읽지 못하기 때문에 JSP 4개 영역에
저장 후 읽는 방식을 선택해야 함
 --%>
str : ${str}<br>
pageStr : ${pageStr}

</body>
</html>










