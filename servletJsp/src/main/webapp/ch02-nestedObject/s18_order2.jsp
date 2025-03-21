<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문서 확인</title>
</head>
<body>
<%--
짜장면 4,000원		짬뽕 5,000원	볶음밥 6,000원
출력예시
[주문 음식]  -> s17에서 최소 1개이상 주문하게끔 조건체크 추가하기
짜장면 : 2개
짬뽕 : 1개
총 지불금액 : 13,000원
 --%>
 <%
 	String [] foodName = {"짜장면","짬뽕","볶음밥"};
 	int[] orderArray = {4000,5000,6000};
 	int sum = 0;
 %>
 [주문 음식] <br>
 <%
 	for(int i=0;i<foodName.length;i++){
 		int cnt = Integer.parseInt(request.getParameter("food_c"+i));
 		sum += cnt * orderArray[i];
 		
 		if(cnt > 0){
 %>
 			<%=foodName[i] %> : <%= cnt %>개 <br>
 <%		
 		}
 	}
 %>
 
 총 지불금액 : <%=String.format("%,d원",sum) %>

</body>
</html>