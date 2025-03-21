<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문서</title>
<style type="text/css">
table{
	border-collapse:collapse;
	border: 1px solid gray;
	width: 500px;
	margin: 0 auto;
}
td{
	height: 30px;
	border:1px solid gray;
}
td.title{
	width:100px;
	text-align:center;
	background-color:ivory;
	font-weight:bold;
	color:#ff6600;
	padding:0 10px;
}
input[type="number"]{
	text-align:right;
	width: 50px;
	height:19px;
}
ul{
	list-style:none;
	padding: 0 10px;
	margin: 5px;
}
ul li{
	display:inline;
}
</style>
<script type="text/javascript">
	window.onload=function(){
		const myForm = document.getElementById("myForm");
		myForm.onsubmit=function(){
			/*
			let check = false;
			for(let i=0;i<=2;i++){
				let food = document.getElementsByName("food_c"+i)[0];
				if(food != null && food.value > 0 && food.value != null) check = true;
			}
			
			if(!check) {
				alert('최소 1가지 이상 주문해야 합니다.')
				return false;
			}
			*/
			const items = document.querySelectorAll('input[type="number"]');
			for(let i=0;i<items.length;i++){
				if(items[i].value==''){
					const label = document.querySelector('label[for="' + items[i].id + '"]');
					alert(label.textContent + '의 수량을 입력하세요.')
					items[i].value = 0;
					items[i].focus();
					return false;
				}
				if(items[0].value==0 && items[1].value==0 && items[2].value==0){
					alert('세가지 음식 중 하나는 꼭 주문해야합니다.')
					return false;
				}
			}
			
		};
		
	};
</script>
</head>
<body>
<form action="s18_order2.jsp" method="post" id="myForm">
	<table>
		<tr>
			<td class="title">식사류</td>
			<td>
				<ul>
					<li>
						<label for="c0">짜장면</label>
						<input type="number" name="food_c0" id="c0" 
							min="0" max="99" value="0">
					</li>
					<li>
						<label for="c1">짬뽕</label>
						<input type="number" name="food_c1" id="c1" 
							min="0" max="99" value="0">
					</li>
					<li>
						<label for="c2">볶음밥</label>
						<input type="number" name="food_c2" id="c2" 
							min="0" max="99" value="0">
					</li>
				</ul>
			</td>
		</tr>
		<tr align="center">
			<td colspan="2">
				<input type="submit" value="전송">
			</td>
		</tr>
	</table>
</form>
</body>
</html>