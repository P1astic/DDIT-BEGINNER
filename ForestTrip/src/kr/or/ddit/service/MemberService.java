package kr.or.ddit.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.dao.ForestTripDao;
import kr.or.ddit.view.ForestTripView;
import kr.or.ddit.vo.AdminVo;
import kr.or.ddit.vo.BookVo;
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
		MemberVo loginCheck = forestDao.userLogin(param);
		if (loginCheck == null) {
			return false;
		}
 
		ForestTripView.sessionStorage.put("mem_id", loginCheck.getMem_id());
		ForestTripView.sessionStorage.put("mem_name", loginCheck.getMem_name());
		ForestTripView.sessionStorage.put("mem_addr", loginCheck.getMem_addr());
		ForestTripView.sessionStorage.put("mem_phone", loginCheck.getMem_phone());
		ForestTripView.sessionStorage.put("mem_mail", loginCheck.getMem_mail());

		return true;
	}

	public boolean idFind(List<Object> param) {
		MemberVo idCheck = forestDao.idFind(param);
		if (idCheck == null) {
			return false;
		}
		ForestTripView.sessionStorage.put("mem_id", idCheck.getMem_id());
		return true;
	}

	public boolean pwFind(List<Object> param) {
		MemberVo checkFlag = forestDao.pwFind(param);
		if (checkFlag == null) {
			return false;
		}
		return true;
	}

	public void pwUpdate(List<Object> param) {
		forestDao.pwUpdate(param);

	}
	public void adminPWUpdate(List<Object> param) {
		forestDao.adminPWUpdate(param);
	}

	public boolean myPage(List<Object> param) {
		MemberVo checkFlag = forestDao.myPage(param);
		if (checkFlag == null) {
			return false;
		}
		return true;
	}
	
	public void phoneUpdate(List<Object> param) {
		forestDao.phoneUpdate(param);
	}
	
	public void mailUpdate(List<Object> param) {
		forestDao.mailUpdate(param);
	}
	
	public boolean adminLogin(List<Object> param) {
		AdminVo checkFlag = forestDao.adminLogin(param);
		if(checkFlag == null) {
			return false;
		}
		
		ForestTripView.sessionStorage.put("adm_id", checkFlag.getAdm_id());
		ForestTripView.sessionStorage.put("adm_code", checkFlag.getCode());
		ForestTripView.sessionStorage.put("adm_roll", checkFlag.getRoll());
		
		return true;
	}

	public void userSignUp(List<Object> param) {
		forestDao.userSignUp(param);
	}
	
	public void adminSignUp(List<Object> param) {
		forestDao.adminSignUp(param);
	}
	
	public List<Map<String, Object>> adminList(List<Object> param) {
		return forestDao.adminList(param);
	}
	
	public List<Map<String, Object>> bookflagList(){
		return forestDao.bookflagList();
	}
	

}
