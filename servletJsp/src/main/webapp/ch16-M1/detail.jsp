<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.board.dao.BoardDAO" %>
<%@ page import="kr.board.vo.BoardVO" %>

<%
	Long board_num = Long.parseLong(request.getParameter("board_num"));
	BoardDAO dao = BoardDAO.getInstance();
	BoardVO boardVO = dao.getBoard(board_num);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글 상세</title>
<link rel="stylesheet" href="../css/style.css"  type="text/css">
</head>
<body>
<div class="page-main">
	<h1>게시판 글 상세</h1>
	<ul>
		<li>번호 : <%= boardVO.getBoard_num() %></li>
		<li>제목 : <%= boardVO.getTitle()%></li>
		<li>작성자 : <%= boardVO.getId()%></li>
	</ul>
	<hr size="1" noshade="noshade" width="100%">
	<p>
		<%= boardVO.getContent()%>
	</p>
	<div class="align-right">
		작성일 : <%=boardVO.getReg_date() %>
<%
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num != null && user_num == boardVO.getNum()){
			//로그인한 회원번호와 작성자 회원번호가 일치한 경우
%>	
		<input type="button" value="수정"
			onclick="location.href='updateForm.jsp?board_num=<%=board_num %>'">
		<input type="button" value="삭제" id="delete_btn">
		<script type="text/javascript">
			const delete_btn = document.getElementById('delete_btn');
			//이벤트 연결
			delete_btn.onclick=function(){
				let choice = confirm('삭제하시겠습니까?');
				if(choice){
					location.replace('delete.jsp?board_num=<%=board_num%>');
				}
			};
		</script>
<%
		}
 %>
 		<input type="button" value="목록" onclick="location.href='list.jsp'">
	</div>
</div>
</body>
</html>