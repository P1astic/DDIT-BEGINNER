package kr.or.ddit.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.dao.ForestTripDao;
import kr.or.ddit.vo.ForestVo;

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

	public List<Map<String, Object>> sigunList(List<Object> page) {

		return forestDao.sigunList(page);
	}

	public List<Map<String, Object>> userForestSearch(List<Object> param) {

		return forestDao.userForestSearch(param);
	}

	// 휴양림 등록
	public void forestInsert(List<Object> page) {
		forestDao.forestInsert(page);
	}

	// 휴양림 상세정보등록
	public void forestRoomInsert(List<Object> param) {
		forestDao.forestRoomInsert(param);
	}

	public List<Map<String, Object>> ForestList(List<Object> param) {
		forestDao.ForestList(param);
		return forestDao.ForestList(param);
	}

	// 관리자 수정 전 휴양림 상세정보보기
	public Map<String, Object> forestDetail(List<Object> param) {
		forestDao.forestDetail(param);
		return forestDao.forestDetail(param);
	}

	// 관리자 휴양림 수정하기
	public void ForestRoomEdit(List<Object> param) {
		forestDao.ForestRoomEdit(param, 0);
	}

	public List<Map<String, Object>> user_peopleSearch(List<Object> param) {
		return forestDao.user_peopleSearch(param);

	}

	// 예약자 확인 및 START - END 해결하기 필요할 때 쓸 것
	public void user_bookCheck(List<Object> param) {

	}

	// 숙소리뷰확인
	public List<Map<String, Object>> reviewCheck(List<Object> param) {

		return forestDao.reviewCheck(param);
	}

	// 숙소 예약하기
	public void forest_Book(List<Object> param) {
		forestDao.forest_Book(param);
	}

	public List<Map<String, Object>> roomList(List<Object> param) {
		return forestDao.roomList(param);
	}

	// 최고관리자 통계확인
	public List<Map<String, Object>> super_adm_statistic(List<Object> param) {
		return forestDao.super_adm_statistic(param);

	}

	// 시도관리자 통계확인
	public void sido_adm_statistic(List<Object> param) {
		forestDao.sido_adm_statistic();
	}

	// 휴양림관리자 통계확인
	public void forest_adm_statistic(List<Object> param) {
		forestDao.forest_adm_static();
	}

	public List<Map<String, Object>> superForestList(List<Object> param) {
		// TODO Auto-generated method stub
		return forestDao.superForestList(param);
	}

	public List<Map<String, Object>> doForestList(List<Object> param) {
		// TODO Auto-generated method stub
		return forestDao.doForestList(param);
	}
	
	public List<Map<String, Object>> gunForestList(List<Object> param) {
		// TODO Auto-generated method stub
		return forestDao.gunForestList(param);
	}

}
