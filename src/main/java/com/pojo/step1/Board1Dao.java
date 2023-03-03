package com.pojo.step1;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.util.MyBatisCommonFactory;
/*
FrontMVC11 -> Board1Controller -> Board1Logic -> Board1Dao -> MyBatis Layer
MyBatis가 실직적인 코드를 줄여 주는 부분은 어디 ?
1 커넥션 연결
; 단, 오라클 서버에 대한 정보 제공 필요
오라클 드라이버 클래스
오라클 서버의 URL 정보 : 멀티 티어에서 유리한 thin 드라이버 방식 내포, 계정 이름과 비밀번호
 */
public class Board1Dao {
	Logger logger = Logger.getLogger(Board1Dao.class);
	MyBatisCommonFactory mcf = new MyBatisCommonFactory();

	public List<Map<String, Object>> getBoardList() {
		logger.info("getBoardList 호출");
		SqlSessionFactory sqlSessionFactory = null;
	    SqlSession sqlSession = null;
		// null로 하는 이유 ? 로직 클래스에서 인스턴스화 해 두었기 때문
		// NPE 발생하지 않음
		List<Map<String, Object>> boardList = null;
		try {
			// 오라클 서버에 대한 정보를 가진 MyBatisConfig.xml 문서 읽음(:MyBatisCommonFactory)
			// SqlSessionFactory().build(resource)
			// MyBatisCommonFactory에서 처리된 결과를 getter 메서드를 통해 주입 받음 
	         sqlSessionFactory = mcf.getSqlSessionFactory();
	         // 34열에서 생성된 객체가 제공하는 openSession()가 SqlSession 객체 생성
	         sqlSession = sqlSessionFactory.openSession();
	         // sqlSession은 쿼리문 요청이 가능한 insert(), update(), delete(), selectOne, selectMap 등 제공
	         boardList = sqlSession.selectList("getBoardList", null);
	         logger.info(boardList);
		} catch (Exception e) {
			logger.info(e.toString());
		}
		return boardList;
	}
}
