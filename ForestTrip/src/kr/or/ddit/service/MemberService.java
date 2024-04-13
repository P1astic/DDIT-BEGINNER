package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.dao.ForestTripDao;

public class MemberService {
	private static MemberService instance;
	ForestTripDao forestDao = ForestTripDao.getInstance();

	private MemberService() {

	}

	public static MemberService getInstance() {
		if (instance == null) {
			instance = new MemberService();
		}
		return instance;
	}

	public boolean userLogin(List<Object> param) {
		List<Object> loginCheck = forestDao.userLogin(param);
		
		
		return true;
	}

}
