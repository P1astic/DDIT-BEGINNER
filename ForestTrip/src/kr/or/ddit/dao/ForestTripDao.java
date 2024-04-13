package kr.or.ddit.dao;

import java.util.List;

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
	
	public List<Object> userLogin(List<Object> param){
		
		return null;
	}
}
