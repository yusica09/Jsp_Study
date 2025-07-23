<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 끝에 /넣어서 단독태그로 사용 --%>
<%-- 자바빈 객체를 페이지 영역에 보관 --%>
<jsp:useBean id="member" class="kr.web.member.MemberVO"/>
<%-- setProperty가 파라미터 네임이 일치하면 데이터를 세팅해줌 --%>
<jsp:setProperty name="member" property="*"/>
<%-- 위의 두줄만 추가하면 되서 편하지만, 모델1에서만 사용 가능 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
아이디 : <jsp:getProperty property="id" name="member"/><br>
비밀번호 : <jsp:getProperty property="password" name="member"/><br>
이름 : <jsp:getProperty property="name" name="member"/><br>
이메일 : <jsp:getProperty property="email" name="member"/><br>
주소 : <jsp:getProperty property="address" name="member"/><br>
</body>
</html>