package kr.or.ddit.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.util.JDBCUtil;
import kr.or.ddit.vo.AdminVo;
import kr.or.ddit.vo.CodeVo;
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
		
		String sql="";
		
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


}
