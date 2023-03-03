<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String datas = (String)request.getAttribute("jsonDoc");
	out.print(datas);
%>