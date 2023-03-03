package com.pojo.step1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// 클래스 설계에 인터페이스 필요
// 인터페이스 중심의 코딩 전개하는 것이 결합도를 낮춤 ; 의존성 낮다 -> 단위 테스트를 가능하게 해 줌으로서 신뢰도를 높이는 코드

// HttpServlet에서 강제(Override: doGet, doPost)하는 void를 다른 타입으로 바꾸어 사용해 보자
// 오버라이딩의 경우 선언부(파라미터, 리턴 타입)를 손댈 수 없음
// ActionForward1은 getter, setter를 가진 VO패턴의 클래스
// 변수 2개 보유 - private 접근 제한자 선언 : 캡슐화
// String path -> res.sendRedirect(path), req.getRequestDispatcher(path)
// 스크립틀릿 init() - service() - destory() ; 서블릿 라이프 사이클
// 개발자는 service에만 관여
// path는 응답 페이지 이름[.jsp]이거나 응답 페이지로 forward될 서블릿의 이름이 온다
// ; jsp -> a.jsp(jsp-api.jar) a_jsp.java a_jsp_clas
// WAS마다 클래스명에 대한 명명규칙이 다름 (재사용의 어려움)
// 목록 -> 글쓰기 -> 글내용 -> 저장(서블릿 sendRedirect) -> insert -> select(서블릿 forward) -> jsp
// 두 번째 전변은 boolean isRedirect / true = sendRedirect, flase = forward
// 아래와 같이 void 변경하였지만 파라미터 자리의 req, res는 개발자가 인스턴스화 하는 것이 아니고 톰캣이 주입
// 이러한 문제를 어떻게 해결하는지 관전 포인트로 삼자
public interface Action1 {
	public ActionForward1 execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
