package kr.or.ddit.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.dao.ForestTripDao;

public class ForestTripService {
	
	private static ForestTripService instance;
	ForestTripDao forestDao = ForestTripDao.getInstance();

	private ForestTripService() {

	}

	public static ForestTripService getInstance() {
		if (instance == null) {
			instance = new ForestTripService();
		}
		return instance;
	}
	
	public List<Map<String, Object>> sidoList(List<Object> page) {
		return forestDao.sidoList(page);
	}
	
	public List<Map<String, Object>> sigunList(List<Object> page){
		
		return forestDao.sigunList(page);
	}
	
}
