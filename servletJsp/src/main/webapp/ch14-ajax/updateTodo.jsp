<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page import="kr.util.DBUtil" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>

<%
	int id = Integer.parseInt(request.getParameter("id"));
	int completed = Integer.parseInt(request.getParameter("completed"));
	
	if(completed == 0) completed = 1;
	else completed = 0;
	
	Connection conn = null;
	PreparedStatement pstmt  = null;
	String sql = null;
	int cnt = 0;
	
	try{
		//Connection 객체 반환
		conn = DBUtil.getConnection();
		//SQL문 작성
		sql = "UPDATE todo SET completed=? WHERE id=?";
		//JDBC수행 3단계 : PreaparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(++cnt,completed);
		pstmt.setInt(++cnt,id);
		//JDBC수행 4단계 : SQL문 실행
		pstmt.executeUpdate();
%>
		{"result":"success"}
<%	
	}catch(Exception e){
%>
		{"result":"failure"}
<%
		e.printStackTrace();
	}finally{
		DBUtil.executeClose(null, pstmt, conn);
	}
%>