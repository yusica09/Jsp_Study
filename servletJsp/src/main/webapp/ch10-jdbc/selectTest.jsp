<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.util.DBUtil" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Date" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학생 정보 목록</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body>
<div class="page-main">
	<h2>학생 성적 목록</h2>
	<div class="align-right">
		<input type="button" value="글쓰기"
		   onclick="location.href='insertTestForm.jsp'">
	</div>
	<table>
		<tr>
			<th>아이디</th>
			<th>이름</th>
			<th>국어</th>
			<th>영어</th>
			<th>수학</th>
			<th>총점</th>
			<th>평균</th>
			<th>등급</th>
		</tr>
<%
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	try{
		//Connection 객체 반환
		conn = DBUtil.getConnection();
		//SQL문 작성
		sql = "SELECT * FROM score ORDER BY id DESC";
		//JDBC 수행 3단계 : PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		//JDBC 수행 4단계 : SQL문 실행
		rs = pstmt.executeQuery();
		while(rs.next()){
			String id = rs.getString("id");
			String name = rs.getString("name");
			int korean = rs.getInt("korean");
			int english = rs.getInt("english");
			int math = rs.getInt("math");
			int sum = rs.getInt("sum");
			int avg = rs.getInt("avg");
			String grade = rs.getString("grade");
%>
			<tr>
				<td><a href="detailTest.jsp?id=<%= id %>"><%= id %></a></td>
				<td><%= name %></td>
				<td><%= korean %></td>
				<td><%= english %></td>
				<td><%= math %></td>
				<td><%= sum %></td>
				<td><%= avg %></td>
				<td><%= grade %></td>
			</tr>			
<%				
			}		
	}catch(Exception e){
%>
		<tr>
			<td colspan="8" class="align-center">오류 발생!</td>
		</tr>
<%		
		e.printStackTrace();
	}finally{
		//자원정리
		DBUtil.executeClose(rs, pstmt, conn);
	}
%>	
	</table>
</div>
</body>
</html>