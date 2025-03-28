<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.employee.dao.EmployeeDAO" %>
<%@ page import="kr.employee.vo.EmployeeVO" %>

<%
	Long user_num = (Long)session.getAttribute("user_num");
	if(user_num == null){ //로그인이 되지 않은 경우
		response.sendRedirect("loginForm.jsp");
	}else{
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 정보 수정</title>
<link rel="stylesheet" href="../css/style.css"  type="text/css">
<script type="text/javascript">
	window.onload=function(){
		const form = document.getElementById('modify_form');
		form.onsubmit = function(){
			const items = document.querySelectorAll('.input-check');
			for(let i=0;i<items.length;i++){
				if(items[i].value.trim()==''){
					const label = document.querySelector('label[for="'+items[i].id+'"]');
					alert(label.textContent+' 입력 필수');
					items[i].value='';
					items[i].focus();
					return false;
				}
			}
		};
	};
</script>
</head>
<body>

<%
	EmployeeDAO dao = EmployeeDAO.getInstance();
	EmployeeVO employee = dao.getEmployee(user_num);
%>

<div class="page-main">
	<h1>회원정보수정</h1>
	<form action="modifyUser.jsp" method="post" id="modify_form">
		<ul>
			<li>
				<label for="name">이름</label>
				<input type="text" name="name" id="name" maxlength="30" 
					value="<%= employee.getName()%>" class = "input-check">
			</li>
			<li>
				<label for="passwd">비밀번호</label>
				<input type="password" name="passwd" id="passwd" maxlength="12" class = "input-check">
			</li>
			<li>
				<label for="salary">급여</label>
				<input type="number" name="salary" id="salary" maxlength="8" min="0" max="99999999"
					value="<%= employee.getSalary()%>"class = "input-check">
			</li>
			<li>
				<label for="job">업무</label>
				<input type="text" name="job" id="job" maxlength="30" value="<%= employee.getJob()%>">
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="등록">
			<input type="button" value="홈으로" onclick="location.href='main.jsp'">
		</div>
	</form>
</div>
</body>
</html>
<%
	}
%>