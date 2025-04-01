<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>formatNumber 태그</title>
</head>
<body>
숫자 : <fmt:formatNumber value="100000" type="number"/><br>
<%-- 엣지 : 소수점 둘째자리까지 출력, 크롬 : 소수점 표시 없음 --%>
통화 : <fmt:formatNumber value="100000" type="currency" currencySymbol="$"/><br>
통화 : <fmt:formatNumber value="100000" type="currency" currencySymbol="\\"/><br>
퍼센트 : <fmt:formatNumber value="0.3" type="percent"/><br>
패턴 : <fmt:formatNumber value="12567.346" pattern="00,000,000.00"/><br>

</body>
</html>