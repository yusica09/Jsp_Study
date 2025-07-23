<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
<%
	//전송된 데이터 반환
	String id = request.getParameter("id");
	String password = request.getParameter("password");
	
	//테스트용으로 id 와 비밀번호가 같으면 로그인 처리
	if(id.equals(password)){//로그인 성공
		session.setAttribute("user_id", id);
%>
	<%= id %>님이 로그인했습니다.<br>
	<input type="button" value="로그인 체크"
	       onclick="location.href='sessionLoginCheck.jsp'">
	<input type="button" value="로그아웃"
	       onclick="location.href='sessionLogout.jsp'">       
<%	
	}else{//로그인 실패
%>
	<script type="text/javascript">
		alert('로그인에 실패했습니다.');
		history.go(-1);
	</script>
<%		
	}
%>
</body>
</html>

