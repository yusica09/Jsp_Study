<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학생 점수 등록</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body>
<div class="page-main">
	<h2>글쓰기</h2>
	<form id="myForm" action="insertTest.jsp" method="post">
		<ul>
			<li>
				<label for="id">아이디</label>
				<input type="text" name="id" id="id" size="20" maxlength="10">
			</li>
			<li>
				<label for="name">이름</label>
				<input type="text" name="name" id="name" size="20" maxlength="10">
			</li>
			<li>
				<label for="korean">국어점수</label>
				<input type="number" name="korean" id="korean" min="0" max="100" maxlength="3">
			</li>
			<li>
				<label for="english">영어점수</label>
				<input type="number" name="english" id="english" min="0" max="100" maxlength="3">
			</li>
			<li>
				<label for="math">수학점수</label>
				<input type="number" name="math" id="math" min="0" max="100" maxlength="3">
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="전송">
			<input type="button" value="목록" onclick="location.href='selectTest.jsp'">
		</div>
	</form>
</div>
</body>
</html>