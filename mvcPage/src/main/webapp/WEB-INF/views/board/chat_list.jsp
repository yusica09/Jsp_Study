<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#search_form').submit(function(){
			if($('#keyword').val().trim() == ''){
				alert('검색어를 입력하세요!');
				$('#keyword').val('').focus();
				return false;
			}
			
		});
		
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>[${board.title}]의 채팅 목록</h2>
		<form id="search_form" action="chatRoomList.do" method="get">
			<input type="hidden" name="board_num" value="${param.board_num}">
			<ul>
				<li>
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>아이디</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>메세지</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
			</ul>
		</form>
			<div class="list-space align-right">
				<input type="button" value="목록"
						onclick="location.href='chatRoomList.do?board_num=${param.board_num}'">
				<input type="button" value="홈으로"
						onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			</div>
			<c:if test="${count == 0}">
			<div class="result-display">
					표시할 채팅이 없습니다.
			</div>
			</c:if>
				
			<c:if test="${count > 0}">
				<table>
					<tr>
						<th>아이디</th>
						<th>메시지</th>
						<th>등록일</th>
					</tr>
					<c:forEach var="chat" items="${list}">
						<tr>
							<td>${chat.id}</td>
							<td><a href="chatDetail.do?chatroom_num=${chat.chatroom_num}">${fn:substring(chat.message,0,40)}(${chat.unread_cnt})</a></td>
							<td>${chat.chat_date}</td>
						</tr>
					</c:forEach>
				</table>
				<div class="align-center">${page}</div>
			</c:if>
	</div>
</div>
</body>
</html>