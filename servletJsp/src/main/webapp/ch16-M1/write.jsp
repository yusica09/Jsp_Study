<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.board.dao.BoardDAO" %>

<%
	Long user_num = (Long)session.getAttribute("user_num");
	if(user_num == null){ //로그인이 되지 않은 경우
		response.sendRedirect("loginForm.jsp");
	}else{
%>
<jsp:useBean id="board" class="kr.board.vo.BoardVO"/>
<jsp:setProperty name="board" property="*"/>
<%
	//작성자의 회원번호
	board.setNum(user_num);
	//클라이언트의 ip주소 저장
	board.setIp(request.getRemoteAddr());
	
	BoardDAO dao = BoardDAO.getInstance();
	dao.insertBoard(board);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기 완료</title>
<link rel="stylesheet" href="../css/style.css"  type="text/css">
</head>
<body>
<div class="page-main">
	<h1>글쓰기 완료</h1>
	<div class="result-display">
		<div class="align-center">
			게시판에 글을 등록했습니다.<p>
			<button onclick="location.href='list.jsp'">목록</button>
		</div>
	</div>
</div>
</body>
</html>
<%
	}
%>