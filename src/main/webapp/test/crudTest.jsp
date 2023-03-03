<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String menu = (String)request.getAttribute("menu");
	String side = (String)request.getAttribute("side");
	// 서블릿에서 주입 받은 request에 담겨 있는 것이지 페이지 이동으로 출력된 화면이 가진
	// request에는 menu가 담기지 않았음(원본이 복사된 것이 아니라 새로 생성)
	// 기존의 요청 끊어지고 새로운 요청(변경된 URL)이 서버에 전달되어서 페이지 출력
	out.print("내가 선택한 메뉴 ?" + menu + "이고, 음료 ?? " + side); 
	// null 출력 왜 ??? sendRedirect이기 때문
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>crudTest</title>
</head>
<body>
crudTest 페이지
<!-- 기존의 요청 끊어지고 새로운 요청 발생
http://localhost:9000/아무이름.st1(확장자는 항상 st1) 요청 시
web.xml -> st1 찾음 -> servlet-name -> servlet -> classname
get 방식임으로 doGet 메서드 호출(톰캣 서버에서 시스템 호출 시 callback)
그 안에서 doService 호출함 이때, 파라미터로 톰캣 서버에서 주입 받은 req, res를 넘긴다
> http://localhost:9000/test/crudTest.jsp 
서블릿이 호출될 때 톰캣 서버로부터 주입 받은 request 객체와 response 객체 아닌
새로운 req, res 객체 -->
</body>
</html>