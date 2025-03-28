<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.story.dao.StoryDAO" %>
<%@ page import="kr.story.vo.StoryVO" %>

<%
	Long user_num = (Long)session.getAttribute("user_num");
	if(user_num==null){//로그인이 되지 않은 경우
		response.sendRedirect("loginForm.jsp");
	}else{//로그인 된 경우
		Long story_num = Long.parseLong(request.getParameter("story_num"));
		StoryDAO dao = StoryDAO.getInstance();
		StoryVO story = dao.getStory(story_num);
		if(user_num == story.getSnum()){
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 수정</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script type="text/javascript">
	window.onload=function(){
		const form = document.getElementById('update_form');
		form.onsubmit = function(){
			const items = document.querySelectorAll('.input-check');
			for(let i=0;i<items.length;i++){
				if(items[i].value.trim()==''){
					const label = document.querySelector(
							'label[for="'+items[i].id+'"]');
					alert(label.textContent + ' 입력 필수');
					items[i].value = '';
					items[i].focus();
					return false;
				}		
			}
		};
	};
</script>
</head>
<body>
<div class="page-main">
	<h1>글 수정</h1>
	<form id="update_form" action="update.jsp" method="post">
		<input type="hidden" name="story_num" value="<%= story_num %>">
		<ul>
			<li>
				<label for="title">제목</label>
				<input type="text" name="title" id="title" value="<%=story.getTitle() %>"
				   size="30" maxlength="50" class="input-check">
			</li>
			<li>
				<label for="content">내용</label>
				<textarea rows="5" cols="40" name="content" id="content"
				  class="input-check"><%=story.getContent()%></textarea>
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="수정">
			<input type="button" value="목록"
			         onclick="location.href='list.jsp'">
		</div>
	</form>
</div>
</body>
</html>
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