package com.pojo.step1;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

// HttpServlet 상속 받았으므로 서블릿
// 메서드 파라미터로 request, response 받을 수 있음
public class FrontMVC11 extends HttpServlet {
	Logger logger = Logger.getLogger(FrontMVC11.class);
	private static final long serialVersionUID = 1L; // 자바에서 제공하는 ID값
	// 개발자 입장에서는 get, post 방식 요청이든 모두 처리해야 함
	// 그래서 창구를 하나로 통일하기 위해 doService 사용자 메서드 추가
	// doService의 문제점 ?? 톰캣으로부터 요청 객체와 응답 객체를 주입 받지 못함
	// XXX.st1으로 요청 시 doGet만 호출 가능하고 doPost, doService 호출 불가
	// 테스트 URL ; 프로토콜:도메인 주소:포트 번호/작업 폴더/요청 이름
	// 메인 메서드는 없음(로컬에서 돌아가는 프로그램이 아니기 때문)

	// @Override를 사용하지 않는 이유는 사용자 정의 메서드이기 때문
	protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		logger.info("doService 호출");
		// /board/getBoardList.st1 -> web.xml -> url-pattern -> *.st1
		String uri = req.getRequestURI(); // 주소창에 입력된 값 중 도메인과 포트 번호가 제외된 값만 담음
		logger.info(uri); // /dept/getDeptList.st1
		// server.xml < Context path="/"
		String context = req.getContextPath(); // -> server.xml
		logger.info(context); // /dept/getDeptList.st1
		String command = uri.substring(context.length() + 1); // context 정보만 제외된 나머지 경로 정보 담음
		System.out.println(command); // /dept/getDeptList.st1
		int end = command.lastIndexOf(".");
		System.out.println(end); // 16
		command = command.substring(0, end); // /board/getBoardList
		System.out.println(command);
		String upmu[] = null; // upmu[0] = 업무명|폴더명, upmu[1] = 요청 기능 이름 getBoardList st1 제거된 상태
		upmu = command.split("/"); // /dept, getDeptList
		ActionForward1 af = null;
		Board1Controller board1Controller = null;
		// Board1Controller는 서블릿이 아니므로 요청, 응답 객체 주입 못 받음
		// execute 호출 시 파라미터 전달
		// 원본이 넘어가기 때문에 upmu에 배열 저장
		req.setAttribute("upmu", upmu);
		if ("board".equals(upmu[0])) {
			// 여기 진입하기 위한 테스트 url 작성 : http:localhost:9000/board/getBoardList.st1
			board1Controller = new Board1Controller();
			// 서블릿이 아니지만 req, res 필요 ; 웹 서비스 제공
			// 톰캣 서버를 경유했을 때 요청 객체와 응답 객체 활용 가능
			// FrontMVC11 경유
			af = board1Controller.execute(req, res);
		}
		// 페이지 이동 처리 공통 코드 만들기
		// 1 res.sendRedirect("/dept/getDeptList.jsp"); jsp -> 서블릿 -> jsp
		// res.sendRedirect("/dept/getDeptList.st1"); jsp -> 서블릿 -> 서블릿 -> jsp
		if (af != null) {
			if (af.isRedirect()) {
				res.sendRedirect(af.getPath());
			} else {
				RequestDispatcher view = req.getRequestDispatcher(af.getPath());
				view.forward(req, res);
			}
		}
	}

	/*************************************************************************
	 * Restful API GET 방식 브라우저에 인터셉트 당함 헤더에 값이 담기므로 제약 존재 ; 첨부파일 처리에 사용 불가, ; 링크 걸 수
	 * 있음, 단위 테스트 가능(Mockup)
	 *************************************************************************/
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 오버라이딩 하는 doGet, doPost 메서드가 아닌 것은 톰캣 서버로부터 req, res 주입 불가
		// 그래서 get, post 메서드 내에서 doService 메서드 호출할 때 파라미터로 req, res 넘김
		// -> 사용자 정의 메서드에서도 요청, 응답 객체 사용할 수 있음
		// POJO 프레임워크는 요청, 응답 객체에 의존적이라고 볼 수 있음
		// 스프링 프레임워크에서는 요청, 응답 객체 없이도 모든 서비스 가능
		// 없이도 가능하다는 것은 메서드의 파라미터로 주입 받는 것을 의미
		// 스프링에서는 메서드의 파라미터 갯수를 늘리거나 줄일 수 있음 ; 메서드 오버로딩
		doService(req, res);
	}

	/*************************************************************************
	 * Restful API POST 방식 링크를 걸 수 없음 단독으로 호출 테스트 불가 ; postman(GET, POST, PUT,
	 * DELETE) 필요 브라우저에 쿼리스트링에 노출이 안 됨 ; 노출이 되지 않아 브라우저에 인터셉트 당하지 않음 -> 모든 요청이 무조건
	 * 서버로 전달 패킷 바디 부분에 값이 담김 (용량에 제한 없음, 첨부파일
	 *************************************************************************/
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// post방식으로 요청 일어나면 서블릿의 doPost 받음
		// 이때, 톰캣 컨테이너로부터 요청, 응답 객체 주입 받음(의존성 주입)
		// 개발자가 정의한 doService 호출 시 파라미터로 주입 받은 요청, 응답 객체 넘김
		doService(req, res);
	}

}
