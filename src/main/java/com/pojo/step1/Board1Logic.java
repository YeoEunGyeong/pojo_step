package com.pojo.step1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
/*
모델(Model => XXX.Logic + XXX.Dao) 계층 클래스 설계 부분
넓은 의미로 보면 Dao(Data Access Object)도 모델 계층의 일부
오라클 서버와 직접적인 연동은 XXXDao 클래스에게 전담
이유 ? MyBatis와 같은 ORM 솔류션과 연계하려면 독립된 클래스로 설계하는 것이 유리
또 다른 이유로는 Hibernate와 같ㅇ느 또 다른 솔루션으로 커스터마이징하거나 리펙토링 시
독릭접인 클래스 구성이 재조립 시 편리하기 때문
XXXLogic 클래스는 업무에 대한 판단, 결정, 선택을 하는 계층에 해당
주로 이런 결정권을 가진 자 팀, 과, 차, 부장
공통된 관심사를 분리하기 위한 목적으로도 로직 클래스 분리는 꼭 필요한 부분
ex) 트랜잭션 처리를 코드가 아닌 시스템 레벨에서 자동으로 제어하려면
	서비스 계층에 해당되는 클래스는 반드시 분리할 수 있는 독립적인 클래스여야 함
여기서 여러가지의 DAO 클래스의 메서드가 다중으로 호출되게 됨에 따라 커밋이나 롤백의 대상이 되는 경우임
*/

public class Board1Logic {
	Logger logger = Logger.getLogger(Board1Logic.class);
	Board1Dao board1Dao = new Board1Dao();
	public List<Map<String, Object>> getBoardList() {
		logger.info("getBoardList 호출");
		List<Map<String, Object>> boardList = new ArrayList<>();
		boardList = board1Dao.getBoardList();
		return boardList;
	}
	public String jsonBoardList() {
		logger.info("jsonBoardList 호출");
		List<Map<String, Object>> boardList = new ArrayList<>();
		boardList = board1Dao.getBoardList();
		String temp = null;
		Gson g = new Gson();
		temp = g.toJson(boardList);
		logger.info(temp);
		return temp;
	}
	public int boardInsert() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int boardUpdate() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int boardDelete() {
		// TODO Auto-generated method stub
		return 0;
	}
}
