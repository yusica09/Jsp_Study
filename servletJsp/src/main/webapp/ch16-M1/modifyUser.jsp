<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.member.dao.MemberDAO" %>
<%@ page import="kr.member.vo.MemberVO" %>
<%
	Long user_num = (Long)session.getAttribute("user_num");
	if(user_num == null){//로그인이 되지 않은 경우
		 response.sendRedirect("loginForm.jsp");
	}else{//로그인된 경우
%>
<jsp:useBean id="member" class="kr.member.vo.MemberVO"/>
<jsp:setProperty name="member" property="*"/>
<%
	member.setNum(user_num);

	MemberDAO dao = MemberDAO.getInstance();
	dao.updateMember(member);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보수정 완료</title>
<link rel="stylesheet" href="../css/style.css"  type="text/css">
</head>
<body>
<div class="page-main">
	<h1>회원정보수정 완료</h1>
	<div class="result-display">
		<div class="align-center">
			회원정보수정 완료!<p>
			<button onclick="location.href='myPage.jsp'">MyPage</button>
		</div>
	</div>
</div>
</body>
</html>

<%
	}
%>