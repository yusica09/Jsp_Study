<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.story.dao.StoryDAO" %>
<%@ page import="kr.story.vo.StoryVO" %>

<%
	Long user_num = (Long)session.getAttribute("user_num");
	if(user_num==null){//로그인이 되지 않은 경우
		response.sendRedirect("loginForm.jsp");
	}else{
%>
<jsp:useBean id="storyVO" class="kr.story.vo.StoryVO"/>
<jsp:setProperty property="*" name="storyVO"/>

<%
		StoryDAO dao = StoryDAO.getInstance();
		StoryVO story = dao.getStory(storyVO.getStory_num());
		//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
		if(user_num == story.getSnum()){
			storyVO.setIp(request.getRemoteAddr());
			dao.update(storyVO);
%>			
		<script>
			alert('글 수정을 완료했습니다.');
			location.href='detail.jsp?story_num=<%= storyVO.getStory_num()%>';
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