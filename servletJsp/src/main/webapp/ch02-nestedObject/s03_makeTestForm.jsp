<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>폼</title>
</head>
<body>
<h1>폼에 데이터 입력</h1>
<form action="s04_viewParameter.jsp" method="get">
<%-- ?name=&address= 라고 써져있는 주소창을 보고 다 입력 안하고 전송했을 경우
	name과 address는 전송됨을 알수있음, pet은 전달 안됨 --%>
	이름 : <input type="text" name="name" size="10"><br>
	주소 : <input type="text" name="address" size="30"><br>
	좋아하는 동물 : 
	<input type="checkbox" name="pet" value="dog">강아지
	<input type="checkbox" name="pet" value="cat">고양이
	<input type="checkbox" name="pet" value="pig">돼지
	<br>
	<input type="submit" value="전송">
	
</form>
</body>
</html>