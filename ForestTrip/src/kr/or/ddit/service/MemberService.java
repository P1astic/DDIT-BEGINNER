package kr.or.ddit.service;

import kr.or.ddit.dao.ForestTripDao;

public class MemberService {
	private static MemberService instance;

	private MemberService() {

	}

	public static MemberService getInstance() {
		if (instance == null) {
			instance = new MemberService();
		}
		return instance;
	}

	ForestTripDao forestDao = ForestTripDao.getInstance();

}
