<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	window.onload=function(){
		const myForm = document.getElementById('modify_form');
		//이벤트 연결
		myForm.onsubmit=function(){
			const radio = document.querySelectorAll('input[type=radio]:checked');
			
			const items = document.querySelectorAll('.input-check');
			for(let i=0; i<items.length;i++){
				if(items[i].value.trim()==''){
					let label = document.querySelector('label[for="'+items[i].id+'"]');
					alert(label.textContent + ' 입력 필수');
					items[i].value ='';
					items[i].focus();
					return false;
				}
			}
			
		};
		
	};
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>상품 수정</h2>
		<form action="adminModify.do" method="post" id="modify_form" 
					enctype="multipart/form-data">
					<input type="hidden" name="item_num" value="${item.item_num}">
			<ul>
				<li>
					<label>상품표시여부</label>
					<input type="radio" name="status" value="1"
								id="status1"<c:if test="${item.status == 1}">checked</c:if>>미표시
					<input type="radio" name="status" value="2"
								id="status2"<c:if test="${item.status == 2}">checked</c:if>>표시
				</li>
				<li>
					<label for="name">상품명</label>
					<input type="text" name="name" id="name"
								maxlength="10" class="input-check" value="${item.name}">
				</li>
				<li>
					<label for="price">가격</label>
					<input type="number" name="price" id="price"
								min="1" max="999999999" class="input-check" value="${item.price}">
				</li>
				<li>
					<label for="quantity">수량</label>
					<input type="number" name="quantity" id="quantity"
								min="0" max="9999999" value="${item.quantity}" class="input-check">
				</li>
				<li>
					<label for="photo1">상품사진1</label>
					<img src="${pageContext.request.contextPath}/upload/${item.photo1}"
							data-img="${item.photo1}" width="50" height="50" class="my-photo">
					<input type="file" name="photo1" id="photo1"
								accept="image/gif,image/png,image/jpeg" class="form-notice">
				</li>
				<li>
					<label for="photo2">상품사진2</label>
					<img src="${pageContext.request.contextPath}/upload/${item.photo2}"
							data-img="${item.photo2}" width="50" height="50" class="my-photo">
					<input type="file" name="photo2" id="photo2"
								accept="image/gif,image/png,image/jpeg" class="form-notice">
				</li>
				<li>
					<label for="detail">상품설명</label>
					<textarea rows="5" cols="30" id="detail"
								name="detail" class="input-check">${item.detail}</textarea>
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="수정">
				<input type="button" value="삭제" onclick="location.href='adminDelete.do?item_num=${item.item_num}'">
				<input type="button" value="목록" onclick="location.href='adminList.do'">
			</div>
		</form>
	</div>
</div>
</body>
</html>