<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" href="../css/style.css"  type="text/css">
<script type="text/javascript" src="../js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		//아이디 중복체크 결과 0:아이디 중복체크 미실시, 아이디 중복 / 1:아이디 미중복
		let count = 0;
	
		$('#confirm_id').click(function(){
			if($('#id').val().trim()==''){
				alert('아이디를 입력하세요');
				$('#id').val('').focus();
				return;
			}
			//서버와 통신
			$.ajax({
				url : 'confirmId.jsp',
				type : 'post',
				data : {id : $('#id').val()},
				dataType : 'json',
				success : function(param){
					if(param.result == 'idDuplicated'){
						count = 0;
						$('#id_signed').text('아이디 중복').css('color','red');
						$('#id').val('').focus();
					}else if(param.result == 'idNotFound'){
						count = 1;
						$('#id_signed').text('사용가능 아이디').css('color','black');
					}else{
						count = 0;
						alert('아이디 중복 체크 오류');
					}
				},
				error : function(){
					count = 0;
					alert('네트워크 오류 발생');
				}
			});
		});//end of click
		
		//아이디 입력창에 데이터를 입력하면 중복체크 관련 정보 초기화
		$('#id').keydown(function(){
			count = 0;
			$('#id_signed').text('');
		});		
		
		$('#register_form').submit(function(){
			const items = document.querySelectorAll('.input-check');
			for(let i=0;i<items.length;i++){
				if(items[i].value.trim()==''){
					const label = document.querySelector('label[for="'+items[i].id+'"]');
					alert(label.textContent+' 입력 필수');
					items[i].value='';
					items[i].focus();
					return false;
				}
				if(items[i].id == 'id' && count == 0){
					alert('아이디 중복체크 필수');
					return false;
				}
			}
		});
	});
</script>                                          
</head>
<body>
<div class="page-main">
	<h1>회원가입</h1>
	<form action="registerUser.jsp" method="post" id="register_form">
		<ul>
			<li>
				<label for="id">아이디</label>
				<input type="text" name="id" id="id" size="7" maxlength="12" autocomplete="off"
								class = "input-check">
				<input type="button" id="confirm_id" value="ID중복확인">
				<span id="id_signed"></span>
			</li>
			<li>
				<label for="name">이름</label>
				<input type="text" name="name" id="name" maxlength="10" class = "input-check">
			</li>
			<li>
				<label for="passwd">비밀번호</label>
				<input type="password" name="passwd" id="passwd" maxlength="12" class = "input-check">
			</li>
			<li>
				<label for="email">이메일</label>
				<input type="email" name="email" id="email" maxlength="50" class = "input-check">
			</li>
			<li>
				<label for="phone">전화번호</label>
				<input type="text" name="phone" id="phone" maxlength="15">
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="등록">
			<input type="button" value="홈으로" onclick="location.href='main.jsp'">
		</div>
	</form>
</div>
</body>
</html>