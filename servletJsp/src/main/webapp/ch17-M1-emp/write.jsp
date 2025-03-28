<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.story.dao.StoryDAO" %>
<%
	Long user_num = (Long)session.getAttribute("user_num");
	if(user_num == null){ //로그인이 되지 않은 경우
		response.sendRedirect("loginForm.jsp");
	}else{
%>
<jsp:useBean id="story" class="kr.story.vo.StoryVO"/>
<jsp:setProperty name="story" property="*"/>
<%
	//작성자의 회원번호
	story.setSnum(user_num);
	//클라이언트의 ip주소 저장
	story.setIp(request.getRemoteAddr());
	
	StoryDAO dao = StoryDAO.getInstance();
	dao.insertStory(story);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글작성 완료</title>
</head>
<body>
<div class="page-main">
	<h1>글작성 완료</h1>
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