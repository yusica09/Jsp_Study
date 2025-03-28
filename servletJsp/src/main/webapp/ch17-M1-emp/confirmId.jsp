<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page import="kr.employee.dao.EmployeeDAO" %>
<%@ page import="kr.employee.vo.EmployeeVO" %>

<%
	//전송된 데이터
	String id = request.getParameter("id");

	EmployeeDAO dao = EmployeeDAO.getInstance();
	EmployeeVO employee = dao.checkEmployee(id);
	if(employee != null){ //아이디 중복
%>
	{"result" : "idDuplicated"}
<%			
	}else{ //미중복
%>		
	{"result" : "idNotFound"}
<%		
	}
%>