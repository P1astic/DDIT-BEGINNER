package kr.or.ddit.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.util.JDBCUtil;
import kr.or.ddit.vo.AdminVo;
import kr.or.ddit.vo.BookVo;
import kr.or.ddit.vo.MemberVo;

public class ForestTripDao {
	private static ForestTripDao instance;
	JDBCUtil jdbc = JDBCUtil.getInstance();

	private ForestTripDao() {

	}

	public static ForestTripDao getInstance() {
		if (instance == null) {
			instance = new ForestTripDao();
		}
		return instance;
	}
	
	public MemberVo userLogin(List<Object> param){
		String sql ="SELECT * FROM FOREST_MEMBER \r\n" + 
					"WHERE MEM_ID = ? AND MEM_PW = ? AND QUIT_FLAG='N'";
		return jdbc.selectOne(sql, param, MemberVo.class);
	}
	
	public MemberVo idFind(List<Object> param) {
		String sql ="SELECT * FROM FOREST_MEMBER \r\n" + 
					"WHERE MEM_NAME = ? AND MEM_PHONE = ? AND QUIT_FLAG='N'";
		return jdbc.selectOne(sql, param, MemberVo.class);
		
	}
	
	public MemberVo pwFind(List<Object> param) {
		String sql ="SELECT * FROM FOREST_MEMBER \r\n" + 
					"WHERE MEM_ID = ? AND MEM_NAME = ? AND QUIT_FLAG='N'";
		return jdbc.selectOne(sql,param, MemberVo.class);
	}
	
	public void pwUpdate(List<Object> param) {
		String sql ="UPDATE FOREST_MEMBER\r\n" + 
					"SET MEM_PW = ?\r\n" + 
					"WHERE MEM_ID = ?";
		jdbc.update(sql, param);
	}
	
	public void adminPWUpdate(List<Object> param) {
		// TODO Auto-generated method stub
		String sql ="UPDATE FOREST_ADMIN\r\n" + 
					"SET ADM_PW = ?\r\n" + 
					"WHERE ADM_ID = ?";
	jdbc.update(sql, param);
	}
	
	public MemberVo myPage(List<Object> param) {
      String sql ="SELECT * FROM FOREST_MEMBER\r\n" + 
               	  "WHERE MEM_PW = ?";
      
      return jdbc.selectOne(sql, param, MemberVo.class);
	      
    }
	
	public void phoneUpdate(List<Object> param) {
		String sql ="UPDATE FOREST_MEMBER\r\n" + 
					"SET MEM_PHONE = ?\r\n" + 
					"WHERE MEM_ID = ?";
		
		jdbc.update(sql, param);
	}
	
	public void mailUpdate(List<Object> param) {
		String sql ="UPDATE FOREST_MEMBER\r\n" + 
					"SET MEM_MAIL = ?\r\n" + 
					"WHERE MEM_ID = ?";
	
		jdbc.update(sql, param);
	}
	
	public AdminVo adminLogin(List<Object> param) {
		String sql ="SELECT * FROM FOREST_ADMIN\r\n" + 
					"WHERE ADM_ID = ? AND ADM_PW = ?";
		
		return jdbc.selectOne(sql, param, AdminVo.class);
	}

	public void userSignUp(List<Object> param) {
		
		String sql="INSERT INTO FOREST_MEMBER\r\n" + 
					"VALUES(\r\n" + 
					"    (SELECT 'M' || TO_CHAR(MAX(TO_NUMBER(SUBSTR(ADM_NO, 2))) + 1) FROM FOREST_ADMIN),\r\n" + 
					"    ?,\r\n" + 
					"    ?,\r\n" + 
					"    ?,\r\n" + 
					"    ?,\r\n" + 
					"    'N',\r\n" + 
					"    ?,\r\n" + 
					"    ?)";
		
		jdbc.update(sql,param);
	}
	
	public void adminSignUp(List<Object> param) {
		String sql ="INSERT INTO FOREST_ADMIN\r\n" + 
					"VALUES (\r\n" + 
					"    (SELECT 'A' || TO_CHAR(MAX(TO_NUMBER(SUBSTR(ADM_NO, 2))) + 1) FROM FOREST_ADMIN),\r\n" + 
					"    ?,\r\n" + 
					"    ?,\r\n" + 
					"    ?,\r\n" + 
					"    ?\r\n" + 
					")";
		jdbc.update(sql, param);
		
	}
	
	public List<Map<String, Object>> sidoList(List<Object> page){
		
		String sql ="SELECT CODE, SIDO FROM (\r\n" + 
					"    SELECT CODE, SIDO, ROWNUM AS RN\r\n" + 
					"    FROM FOREST_CODE\r\n" + 
					"    WHERE SIDO = SIGUN AND CODE != 9999999\r\n" + 
					")\r\n" + 
					"WHERE RN >= ? AND RN <= ?";
		return jdbc.selectList(sql, page);
	}
	
	public List<Map<String, Object>> sigunList(List<Object> page){
		String sql ="SELECT CODE, SIDO, SIGUN FROM ( \r\n" + 
				"    SELECT CODE, SIDO, SIGUN, ROWNUM AS RN\r\n" + 
				"    FROM FOREST_CODE\r\n" + 
				"	 WHERE SIDO != SIGUN AND CODE != 9999999) \r\n" + 
				"    WHERE RN >= ? AND RN <= ? ";
		
		return jdbc.selectList(sql,page);
	}
	
	public List<Map<String, Object>> adminList(List<Object> page){
		String sql ="select code, adm_id, roll from FOREST_ADMIN\r\n" + 
				"WHERE ROWNUM >= ? AND ROWNUM <= ? ";
		
		return jdbc.selectList(sql, page);
	}
	
	public List<Map<String, Object>> bookflagList(){
		String sql = "SELECT ROOM_NO, BOOK_QTY,\r\n" + 
				"TO_CHAR(BOOK_D_START,'YYYY/MM/DD') AS BOOK_D_START, TO_CHAR(BOOK_D_END,'YYYY/MM/DD') AS BOOK_D_END\r\n" + 
				"FROM FOREST_BOOK";
		
		return jdbc.selectList(sql);
		
	}
	//휴양림 등록
	public void forestInsert(List<Object>param){
		String sql = "INSERT INTO FOREST  \r\n" + 
				"VALUES (SELECT 'A' || TO_CHAR(MAX(TO_NUMBER(SUBSTR(FOREST_NO, 2))) + 1) FROM FOREST),\\r\\n\","
				+ "SELECT 'A' || TO_CHAR(MAX(TO_NUMBER(SUBSTR(ADM_NO, 2))) + 1) FROM FOREST),\\r\\n\","
				+ "?,"	//CODE
				+ "?,"	//FOREST_NAME
				+ "?,"	//FOREST_INTRO
				+ "?,"	//FOREST_NOTICE
				+ "?)";	//FOREST_ADDR
		//room_no,adm_no,code,forest_name,forest_intro,forest_notice,forest_addr
		jdbc.update(sql,param);
	}
	
	//휴양림 상세등록  
	public void forestRoomInsert(List<Object>param) {
		String sql ="INSERT INTO FOREST_ROOM  \r\n" + 
					"VALUES (SELECT 'A' || TO_CHAR(MAX(TO_NUMBER(SUBSTR(ROOM_NO, 2))) + 1) FROM FOREST_ADMIN),\\r\\n\","
					+ "SELECT 'A' || TO_CHAR(MAX(TO_NUMBER(SUBSTR(FOREST_NO, 2))) + 1) FROM FOREST_ADMIN),\\r\\n\","
					+ "?,"	//ROOM_NAME
					+ "?,"	//ROOM_QTY
					+ "'N')\r\n";
		//room_no,forest_no,room_name,room_qty,room_flag
		jdbc.update(sql,param);
	}
	
	//휴양림 목록
	public List<Map<String, Object>> ForestList(List<Object> param) {
		
		String sql= "SELECT FOREST_NAME, FOREST_INTRO, FOREST_NOTICE, FOREST_ADDR\r\n" + 
					"FROM FOREST\r\n" + 
					"WHERE ROWNUM >= ? AND ROWNUM <= ? AND ADM_NO = ?";
		return jdbc.selectList(sql,param);//page 시작, 끝, roll
		// TODO Auto-generated method stub
	}
	
	public void ForestRoomEdit(List<Object>param, int sel) {
		
		if(sel == 1) {
			String sql ="UPDATE FOREST\r\n" + 
					"SET FOREST_NAME=?\r\n" + 
					"WHERE FOREST_NO= ? \r\n" + 
					";";
			jdbc.update(sql, param);
		}else if(sel ==2) {
			String sql ="UPDATE FOREST\r\n" + 
					"SET FOREST_INTRO=?\r\n" + 
					"WHERE FOREST_NO= ? \r\n" + 
					";";
			jdbc.update(sql, param);
		}else if(sel ==3) {
			String sql ="UPDATE FOREST\r\n" + 
					"SET FOREST_NOTICE=?\r\n" + 
					"WHERE FOREST_NO= ? \r\n" + 
					";";
			jdbc.update(sql, param);
		}else if(sel==4) {
			String sql ="UPDATE FOREST\r\n" + 
					"FOREST_ADDR=?\r\n" + 
					"WHERE FOREST_NO= ? \r\n" + 
					";";
			jdbc.update(sql, param);
		}

	}
	
	public Map<String, Object> ForestRoomDetail(List<Object>param) {
		String sql = "SELECT FOREST_NO, ADM_NO, FOREST_NAME, FOREST_INTRO, FOREST_NOTICE, FOREST_ADDR\r\n" + 
				"FROM FOREST\r\n" + 
				"WHERE FOREST_NO= ? ";
		return jdbc.selectOne(sql, param);
	}
	
	//유저 시도시군 검색
	public List<Map<String, Object>> user_sidogunSearch(List<Object> param) {
		String sql = "SELECT B.FOREST_NO, B.ADM_NO, B.FOREST_NAME, B.FOREST_INTRO, B.FOREST_NOTICE, B.FOREST_ADDR,A.ROOM_NAME,A.ROOM_QTY\r\n" + 
				"FROM FOREST_ROOM A, FOREST B\r\n" + 
				"WHERE FOREST_ADDR LIKE '%'+?+'%' AND A.FOREST_NO=B.FOREST_NO;";
		
		return jdbc.selectList(sql, param);
	}
	
	//휴양림 인원검색
	public List<Map<String,Object>> user_peopleSearch(List<Object> param) {
		String sql = "SELECT B.FOREST_NO, B.ADM_NO, B.FOREST_NAME, B.FOREST_INTRO, B.FOREST_NOTICE, B.FOREST_ADDR,A.ROOM_NAME,A.ROOM_QTY\r\n" + 
				"FROM FOREST_ROOM A, FOREST B \r\n" + 
				"WHERE ROOM_QTY = ? AND A.FOREST_NO=B.FOREST_NO;";
		
		return jdbc.selectList(sql,param);
	}

	//예약자 확인 및 START - END 해결하기 필요할 때 쓸 것
	public void user_bookCheck(){
		String sql = "SELECT A.MEM_NAME, C.ROOM_NAME, B.BOOK_QTY, B.BOOK_D_START, B.BOOK_D_END\r\n" + 
				"FROM FOREST_MEMBER A, FOREST_BOOK B, FOREST_ROOM C\r\n" + 
				"WHERE A.MEM_NO=B.MEM_NO AND B.ROOM_NO=C.ROOM_NO\r\n" + 
				"      AND B.MEM_NO = ? ";
	}
	
	//숙소 리뷰확인
	public void review_Check(){
		String sql = "SELECT B.FOREST_NAME,A.REVIEW_BODY,A.REVIEW_SCORE\r\n" + 
				"FROM FOREST_REVIEW A, FOREST B\r\n" + 
				"WHERE A.FOREST_NO=B.FOREST_NO AND B.FOREST_NAME LIKE '%'+ ? +'%'";
	}
	
	//숙소 예약하기
	public void forest_Book(){
		String sql = "INSERT INTO FOREST_BOOK (BOOK_NO, MEM_NO, ROOM_NO, BOOK_QTY, BOOK_D_START, BOOK_D_END)\r\n" + 
				"VALUES ('B00004', 'M10004', 'R00004', 4, TO_DATE('20240509', 'YYYYMMDD'), TO_DATE('20240512', 'YYYYMMDD'))";
	}


}
