package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.dao.ForestTripDao;
import kr.or.ddit.vo.memberVo;

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

	public void userSignUp(List<Object> param) {
		forestDao.userSignUp(param);
	}

}
