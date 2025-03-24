<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="kr.util.DBUtil"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학생 점수 등록 처리</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body>
	<%
	//전송된 데이터 반환
	String id = request.getParameter("id");
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

	//DB연동
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql = null;

	try {
		//Connection 객체 반환
		conn = DBUtil.getConnection();

		//SQL문 작성
		sql = "INSERT INTO score(id,name,korean,english,math,sum,avg,grade,reg_date)"
		+ "VALUES(?,?,?,?,?,?,?,?,SYSDATE)";

		//JDBC 수행 3단계 : PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		//?에 데이터 바인딩
		pstmt.setString(1, id);
		pstmt.setString(2, name);
		pstmt.setInt(3, korean);
		pstmt.setInt(4, english);
		pstmt.setInt(5, math);
		pstmt.setInt(6, sum);
		pstmt.setInt(7, avg);
		pstmt.setString(8, grade);

		//JDBC 수행 4단계 : SQL문 실행
		pstmt.executeUpdate();
	%>
	<div class="result-display">
		<div class="align-center">
			글 등록 성공
			<p>
				<input type="button" value="목록보기"
					onclick="location.href='selectTest.jsp'">
		</div>
	</div>
	<%
	} catch (Exception e) {
	%>
	<div class="result-display">
		<div class="align-center">
			오류 발생! 등록 실패
			<p>
				<input type="button" value="글쓰기"
					onclick="location.href='insertTestForm.jsp'">
		</div>
	</div>
	<%
	e.printStackTrace();
	} finally {
	//자원정리
	DBUtil.executeClose(null, pstmt, conn);
	}
	%>
</body>
</html>