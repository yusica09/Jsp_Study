<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.util.DBUtil" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 수정</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body>
<%
	//전송된 데이터 반환
	int num = Integer.parseInt(request.getParameter("num"));
	String name = request.getParameter("name");
	String title = request.getParameter("title");
	String passwd = request.getParameter("passwd");
	String content = request.getParameter("content");
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql = null;
	int cnt = 0;
	try{
		//Connection 객체 반환
		conn = DBUtil.getConnection();
		//SQL문 작성
		sql = "UPDATE tboard SET name=?,title=?,passwd=?,content=? WHERE num=?";
		//JDBC 수행 3단계 : PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		//?에 데이터 바인딩
		pstmt.setString(++cnt, name);
		pstmt.setString(++cnt, title);
		pstmt.setString(++cnt, passwd);
		pstmt.setString(++cnt, content);
		pstmt.setInt(++cnt, num);
		//JDBC 수행 4단계 : SQL문 실행
		pstmt.executeUpdate();
%>
	<div class="result-display">
		<div class="align-center">
			글 수정 완료!<p>
			<input type="button" value="글 상세"
			  onclick="location.href='detailTest.jsp?num=<%= num %>'">
		</div>
	</div>
<%		
	}catch(Exception e){
%>
	<div class="result-display">
		<div class="align-center">
			오류 발생! 글 수정 실패!<p>
			<input type="button" value="글 수정 폼"
			  onclick="location.href='updateTestForm.jsp?num=<%= num %>'">
		</div>
	</div>	
<%		
		e.printStackTrace();
	}finally{
		//자원정리
		DBUtil.executeClose(null, pstmt, conn);
	}
%>
</body>
</html>
