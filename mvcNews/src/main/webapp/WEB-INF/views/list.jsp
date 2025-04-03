<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>뉴스 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css" type="text/css">
</head>
<body>
<div class="page-main">
	<h2>뉴스 목록</h2>
	<div class="align-right">
		<input type="button" value="뉴스 작성"
		             onclick="location.href='writeForm.do'">
	</div>
	<c:if test="${count == 0}">
	<div class="result-display">
		표시할 뉴스가 없습니다.
	</div>	
	</c:if>
	<c:if test="${count > 0}">
	<table>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
		</tr>
		<c:forEach var="news" items="${list}">
		<tr>
			<td>${news.num}</td>
			<td><a href="detail.do?num=${news.num}">${news.title}</a></td>
			<td>${news.writer}</td>
			<td>${news.reg_date}</td>
		</tr>	
		</c:forEach>
	</table>
	<div class="align-center">
		${page}
	</div>
	</c:if>
</div>
</body>
</html>








