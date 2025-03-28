<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.employee.dao.EmployeeDAO" %>
<%@ page import="kr.employee.vo.EmployeeVO" %>
<%
	//session.getAttribute는 Object 타입을 반환하므로 다운캐스팅 (Long)
	Long user_num = (Long)session.getAttribute("user_num");
	if(user_num == null){ //로그인이 되지 않은 경우
		response.sendRedirect("loginForm.jsp");
	}else{ //로그인 된 경우
%>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 상세정보</title>
<link rel="stylesheet" href="../css/style.css"  type="text/css">
</head>
<body>
<%
	EmployeeDAO dao = EmployeeDAO.getInstance();
	EmployeeVO employee = dao.getEmployee(user_num);
%>
<div class="page-main">
	<h1>사원정보</h1>
	<ul>
		<li>아이디 : <%=employee.getId() %></li>
		<li>이름 : <%=employee.getName() %></li>
		<li>급여 : <%=String.format("%,d 원", employee.getSalary())  %></li>
		<li>업무 : <%=employee.getJob()%></li>
		<li>입사일 : <%=employee.getReg_date()%></li>
	</ul>
	<hr size="1" width="100%" noshade="noshade">
	<div class="align-right">
		<input type="button" value="사원정보수정" onclick="location.href='modifyUserForm.jsp'">
		<input type="button" value="퇴사" onclick="location.href='deleteUserForm.jsp'">
		<input type="button" value="홈으로" onclick="location.href='main.jsp'">
	</div>
</div>
</body>
</html>
<%
	}
%>