<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--  상단 시작 -->
<%
	String company = request.getParameter("company");
%>
<b><%= company %></b>
상단 메뉴 :
<a href="#">HOME</a>
<a href="#">INFO</a>

<!--  상단 끝 -->