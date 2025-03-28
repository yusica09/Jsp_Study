<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.employee.dao.EmployeeDAO" %>
<%@ page import="kr.employee.vo.EmployeeVO" %>
<%
	Long user_num = (Long)session.getAttribute("user_num");
	if(user_num == null){//로그인이 되지 않은 경우
		 response.sendRedirect("loginForm.jsp");
	}else{//로그인된 경우
%>
<jsp:useBean id="employee" class="kr.employee.vo.EmployeeVO"/>
<jsp:setProperty name="employee" property="*"/>
<%
	employee.setSnum(user_num);

	EmployeeDAO dao = EmployeeDAO.getInstance();
	dao.updateEmployee(employee);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원정보 수정 완료</title>
<link rel="stylesheet" href="../css/style.css"  type="text/css">
</head>
<body>
<div class="page-main">
	<h1>사원정보수정 완료</h1>
	<div class="result-display">
		<div class="align-center">
			사원정보수정 완료!<p>
			<button onclick="location.href='myPage.jsp'">MyPage</button>
		</div>
	</div>
</div>
</body>
</html>
<%
	}
%>