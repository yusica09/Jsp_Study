<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.board.dao.BoardDAO" %>
<%@ page import="kr.board.vo.BoardVO" %>

<%
	Long user_num = (Long)session.getAttribute("user_num");
	if(user_num==null){//로그인이 되지 않은 경우
		response.sendRedirect("loginForm.jsp");
	}else{
%>
<jsp:useBean id="boardVO" class="kr.board.vo.BoardVO"/>
<jsp:setProperty property="*" name="boardVO"/>

<%
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO board = dao.getBoard(boardVO.getBoard_num());
		//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
		if(user_num == board.getNum()){
			boardVO.setIp(request.getRemoteAddr());
			dao.updateBoard(boardVO);
%>			
		<script>
			alert('글 수정을 완료했습니다.');
			location.href='detail.jsp?board_num=<%= boardVO.getBoard_num()%>';
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