


package kr.or.ddit.view;

public enum View {
	//메인화면
	MAIN,
	
	//회원이 사용하는 기능
	USER_HOME,
	USER_LOGIN, // 유저 로그인 
	USER_SIGNUP, // 유저 회원가입
	USER_FIND, // 아이디, 비밀번호 찾기 페이지
	ID_FIND, // 아이디 찾기 기능
	PW_FIND, // 비밀번호 찾기 기능
	USER_MYPAGE, // 마이페이지 안에 자기가 갔던 휴양림 목록 출력
	USER_MYPAGE_EDIT,
	USER_MYPAGE_FOREST_REVIEW,
	USER_QUIT,
	
	//결제 정보를 입력해야할까말까
	
	
	//관리자가 사용하는 기능
	// 관리자에는 최고관리자(아이디 부여, 권한부여, 데이터 총괄관리), 도담당자(관할 도 담당), 휴양림 담당자(휴양림 담당)가 있다.
	ADMIN_LOGIN,
	ADMIN_MAIN,
	ADMIN_REGISTER,
	ADMIN_EDIT,
	ADMIN_LIST,
	ADMIN_REGISTER_ROLL,
	ADMIN_FOREST_LIST,
	ADMIN_FOREST_REGISTER,
	ADMIN_FOREST_DETAIL_REGI, //공지사항 등
	ADMIN_FOREST_EDIT, // 전체 수정
	
	ADMIN_STATISTIC,
	
	
	
	
	//시스템 기능
	FOREST_SEARCH,
	FOREST_LIST,
	FOREST_DETAIL,
	FOREST_BOOKING
	
	
}
