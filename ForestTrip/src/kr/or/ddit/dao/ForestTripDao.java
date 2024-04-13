package kr.or.ddit.dao;

import java.util.List;

import kr.or.ddit.util.JDBCUtil;


public class ForestTripDao {
	private static ForestTripDao instance;

	private ForestTripDao() {

	}

	public static ForestTripDao getInstance() {
		if (instance == null) {
			instance = new ForestTripDao();
		}
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public void userSignUp(List<Object> param) {
		
		String sql="";
		
		jdbc.update(sql,param);
	}
	
	public List<Object> userLogin(List<Object> param){
		
		return null;
	}
}
