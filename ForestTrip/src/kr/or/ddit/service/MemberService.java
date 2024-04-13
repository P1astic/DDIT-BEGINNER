package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.dao.ForestTripDao;
import kr.or.ddit.view.ForestTripView;
import kr.or.ddit.vo.MemberVo;

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
		MemberVo loginCheck= forestDao.userLogin(param);
		if (loginCheck == null) {
			return false;
		}

		ForestTripView.sessionStorage.put("loginCheck", loginCheck);
		ForestTripView.sessionStorage.put("mem_id", loginCheck.getMem_name());
		
		return true;
	}
	
	public boolean idFind(List<Object> param) {
		MemberVo idCheck = forestDao.idFind(param);
		if(idCheck == null) {
			return false;
		}
		ForestTripView.sessionStorage.put("mem_id", idCheck.getMem_id());
		return true;
	}
	
	public boolean pwFind(List<Object> param) {
		MemberVo checkFlag = forestDao.pwFind(param);
		if(checkFlag == null) {
			return false;
		}
		return true;
	}
	
	public void pwUpdate(List<Object> param) {
		forestDao.pwUpdate(param);
		
	}

}
