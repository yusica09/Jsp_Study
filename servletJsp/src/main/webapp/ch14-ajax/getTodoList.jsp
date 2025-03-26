<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page import="kr.util.DBUtil" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Date" %>
[<%
 	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	try{
		//Connection 객체 반환
		conn = DBUtil.getConnection();
		//SQL문 작성
		sql = "SELECT * FROM todo ORDER BY id DESC";
		//JDBC 수행 3단계 : PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		//JDBC 수행 4단계 : SQL문 작성
		rs = pstmt.executeQuery();
		while(rs.next()){
			int id = rs.getInt("id");
			String todo = rs.getString("todo");
			Date created = rs.getDate("created");
			int completed = rs.getInt("completed");
			
			if(rs.getRow()>1) out.print(",");
%>
		{
			"id":<%= id %>,
			"todo":"<%= todo %>",
			"created":"<%= created %>",
			"completed":<%= completed %>
		}
<%
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		DBUtil.executeClose(rs, pstmt, conn);
	}
 
 %>]


