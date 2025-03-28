<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.board.dao.BoardDAO" %>
<%@ page import="kr.board.vo.BoardVO" %>

<%
	Long user_num = (Long)session.getAttribute("user_num");
	if(user_num==null){
		response.sendRedirect("loginForm.jsp");
	}else{
		long board_num = Long.parseLong(request.getParameter("board_num"));
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO board = dao.getBoard(board_num);
		//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
		if(user_num == board.getNum()){
			//게시글 삭제
			dao.deleteBoard(board_num);
%>
		<script>
			alert('글 삭제를 완료했습니다.');
			location.replace('list.jsp');
		</script>
<%
		}else{
%>
		<script>
			alert('잘못된 접속입니다.');
			location.replace('list.jsp');
		</script>
<%
		}
	}
%>