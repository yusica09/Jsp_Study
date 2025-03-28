<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.employee.dao.EmployeeDAO" %>
<%@ page import="kr.employee.vo.EmployeeVO" %>

<%
	//전송된 데이터
	String id = request.getParameter("id");
	String passwd = request.getParameter("passwd");
	
	EmployeeDAO dao = EmployeeDAO.getInstance();
	EmployeeVO employee = dao.checkEmployee(id);
	
	boolean check = false;
	
	if(employee != null){ //id 존재
		check = employee.isCheckedPassword(passwd);
	}
	
	if(check){
		session.setAttribute("user_num", employee.getSnum());
		session.setAttribute("user_id", id);
		
		response.sendRedirect("main.jsp");
	}else{
%>		
		<script>
			alert('아이디 또는 비밀번호가 불일치합니다.');
			history.go(-1);
		</script>
<%		
	}
%>