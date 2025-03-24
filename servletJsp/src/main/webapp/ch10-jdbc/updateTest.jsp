<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.util.DBUtil" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학생 성적 수정</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body>
<%
	//전송된 데이터 반환
	String id =request.getParameter("id");
	String name = request.getParameter("name");
	int korean = Integer.parseInt(request.getParameter("korean"));
	int english = Integer.parseInt(request.getParameter("english"));
	int math = Integer.parseInt(request.getParameter("math"));
	
	int sum = korean + english + math;
	int avg = sum / 3;
	String grade = null;

	switch (avg / 10) {
	case 10:
	case 9:
		grade = "A";
		break;
	case 8:
		grade = "B";
		break;
	case 7:
		grade = "C";
		break;
	case 6:
		grade = "D";
		break;
	default :
		grade = "F";
		break;
	}
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql = null;
	int cnt = 0;
	try{
		//Connection 객체 반환
		conn = DBUtil.getConnection();
		//SQL문 작성
		sql = "UPDATE score SET name=?,korean=?,english=?,math=?,sum=?,avg=?, grade=? WHERE id=?";
		//JDBC 수행 3단계 : PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		//?에 데이터 바인딩
		pstmt.setString(++cnt, name);
		pstmt.setInt(++cnt, korean);
		pstmt.setInt(++cnt, english);
		pstmt.setInt(++cnt, math);
		pstmt.setInt(++cnt, sum);
		pstmt.setInt(++cnt, avg);
		pstmt.setString(++cnt, grade);
		pstmt.setString(++cnt, id);
		//JDBC 수행 4단계 : SQL문 실행
		pstmt.executeUpdate();
%>
	<div class="result-display">
		<div class="align-center">
			수정 완료!<p>
			<input type="button" value="학생 상세"
			  onclick="location.href='detailTest.jsp?id=<%= id %>'">
		</div>
	</div>
<%		
	}catch(Exception e){
%>
	<div class="result-display">
		<div class="align-center">
			오류 발생! 수정 실패!<p>
			<input type="button" value="수정 폼"
			  onclick="location.href='updateTestForm.jsp?id=<%= id %>">
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
