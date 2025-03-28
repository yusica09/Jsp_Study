<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Long user_num = (Long)session.getAttribute("user_num");
	if(user_num==null){//로그인이 되지 않은 경우
		response.sendRedirect("loginForm.jsp");
	}else{//로그인 된 경우
%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글작성</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script type="text/javascript">
	window.onload=function(){
		const form = document.getElementById('write_form');
		form.onsubmit = function(){
			const items = 
				document.querySelectorAll('.input-check');
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
	<h1>글쓰기</h1>
	<form id="write_form" action="write.jsp" method="post">
		<ul>
			<li>
				<label for="title">제목</label>
				<input type="text" name="title" id="title"
				   size="30" maxlength="150" class="input-check">
			</li>
			<li>
				<label for="content">내용</label>
				<textarea rows="5" cols="40"
				  name="content" id="content"
				  class="input-check"></textarea>
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="글쓰기">
			<input type="button" value="목록"
			         onclick="location.href='list.jsp'">
		</div>
	</form>
</div>
</body>
</html>
<%
	}
%>