package com.pojo.step1;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class Board1Controller implements Action1 {
	Logger logger = Logger.getLogger(Board1Controller.class);
	@Override
	public ActionForward1 execute(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		logger.info("execute 호출");
		logger.info("requset" + req);
		logger.info("response" + res);
		// ActionForward1 af = null; NullPointException 주의
		ActionForward1 af = new ActionForward1();
		// FrontMVC11에서 결정된 정보를 Board1Controller에서 사용하기 위해 
		//요청 객체 저장소에 있는 값 가져오기	
		String upmu[] = (String[])req.getAttribute("upmu");
		Board1Logic board1Logic = new Board1Logic();
		// FrontMVC11은 실제 업무를 처리하는 클래스 아님
		// 실제 게시판 구현의 마지막 단계는 Board1Controller 클래스니까
		// 여기서 path 정보와 리다이랙트 여부를 결정해 줌 ; 업무 담당자의 마지막 위치이기 때문
		String path = null;
		boolean isRedirect = false;
		// 게시글 목록 출력 ??
		if ("getBoardList".equals(upmu[1])) { // upmu[1]
			List<Map<String, Object>> boardList = board1Logic.getBoardList();
			// 조호된 결과를 요청 객체에 담기
			req.setAttribute("boardList", boardList);
			// 응답 페이지 이름 결정
			path = "getDeptList.jsp";
			// 만일 vue.js나 react.js와 같은 UI 라이브러리와 연계 시에는 json 포맷을 생성하는
			// jsp 페이지로 연결시켜 줘야 함을 주의
			// path = "jsonGetBoardList.jsp" appliacatio/json
			isRedirect = false; // false 이면 forward ; 요청 유지, 주소창은 그대로지만 페이지는 변경됨
		}
		else if ("jsonBoardList".equals(upmu[1])) {
			String jsonDoc = board1Logic.jsonBoardList();
			logger.info(jsonDoc);
			req.setAttribute("jsonDoc", jsonDoc);
			path = "jsonBoardList.jsp";
			isRedirect = false;
		}
		// 부서 등록 ???
		else if ("boardInsert".equals(upmu[1])) {
			// insert into board_master_t(bm_no, bm_wirter, bm_title, ...) valuse(?, ?, ?)
			int result = board1Logic.boardInsert();
		}
		// 부서 정보 수정 ??
		else if ("boardUpdate".equals(upmu[1])) {
			// update board_master_t set bm_title = ?, bm_wirter = ?, bm_content =?, ...
			int result = board1Logic.boardUpdate();
			
		}
		// 부서 삭제 ??
		else if ("boardDelete".equals(upmu[1])) {
			// delete form board_master_t where bm_no = ?
			int result = board1Logic.boardDelete();
		}
		af.setPath(path);
		af.setRedirect(isRedirect);
		return af;
	}
	public static void main(String args[]) {
		Board1Controller bc = new Board1Controller();
		try {
			bc.execute(null, null); // null이 뜨는 이유는 톰캣 서버를 경유하지 않아서
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
