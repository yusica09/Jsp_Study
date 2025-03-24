<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.util.DBUtil" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 삭제</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body>
<%
	//전송된 데이터 반환
	int num = Integer.parseInt(request.getParameter("num"));
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql = null;
	
	try{
		//Connection 객체 반환
		conn = DBUtil.getConnection();
		//SQL문 작성
		sql = "DELETE FROM tboard WHERE num=?";
		//JDBC 수행 3단계 : PreparedSatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		//?에 데이터 바인딩
		pstmt.setInt(1, num);
		//JDBC 수행 4단계 : SQL문 실행
		pstmt.executeUpdate();
%>
	<div class="result-display">
		<div class="align-center">
			글 삭제 완료!<p>
			<input type="button" value="목록"
			     onclick="location.href='selectTest.jsp'">
		</div>
	</div>
<%		
	}catch(Exception e){
%>
	<div class="result-display">
		<div class="align-center">
			오류 발생! 글 삭제 실패!<p>
			<input type="button" value="목록"
			     onclick="location.href='selectTest.jsp'">
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
