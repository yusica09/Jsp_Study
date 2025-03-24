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
<title>글상세 정보</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body>
<div class="page-main">
	<h2>글상세 정보</h2>
<%
	int num = Integer.parseInt(request.getParameter("num"));
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	try{
		//Connection 객체 반환
		conn = DBUtil.getConnection();
		//SQL문 작성
		sql = "SELECT * FROM tboard WHERE num=?";
		//JDBC 수행 3단계 : PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		//?에 데이터 바인딩
		pstmt.setInt(1,num);
		
		//JDBC 수행 4단계 : SQL문 실행
		rs = pstmt.executeQuery();
		if(rs.next()){
			String name = rs.getString("name");
			String title = rs.getString("title");
			String content = rs.getString("content");
			Date reg_date = rs.getDate("reg_date");
%>
	<ul>
		<li>글번호 : <%= num %></li>
		<li>제목 : <%= title %></li>
		<li>작성자 : <%= name %></li>
		<li>작성일 : <%= reg_date %></li>
	</ul>
	<hr width="100%" size="1" noshade="noshade">
	<p>
		<%= content %>
	</p>
	<div class="align-right">
		<input type="button" value="수정"
		 onclick="location.href='updateTestForm.jsp?num=<%= num %>'">
		<input type="button" value="삭제" id="delete_btn"> 
		<input type="button" value="목록"
		     onclick="location.href='selectTest.jsp'">
	</div>
	<script type="text/javascript">
		const delete_btn = 
			      document.getElementById('delete_btn');
		//이벤트 연결
		delete_btn.onclick=function(){
			let choice = confirm('삭제하시겠습니까?');
			if(choice){
				location.replace('deleteTest.jsp?num=<%= num %>');
			}
		};
	</script>
<%			
		}else{
%>
	<div class="result-display">
		<div class="align-center">
			글상세 정보가 없습니다.<p>
			<input type="button" value="목록"
			   onclick="location.href='selectTest.jsp'">
		</div>
	</div>
<%			
		}
	}catch(Exception e){
%>
	<div class="result-display">
		<div class="align-center">
			오류 발생! 글상세 정보 호출 실패!<p>
			<input type="button" value="목록"
			   onclick="location.href='selectTest.jsp'">
		</div>
	</div>	
<%		
		e.printStackTrace();
	}finally{
		//자원정리
		DBUtil.executeClose(rs, pstmt, conn);
	}
%>	
</div>
</body>
</html>
