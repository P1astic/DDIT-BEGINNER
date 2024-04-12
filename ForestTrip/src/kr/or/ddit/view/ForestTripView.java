package kr.or.ddit.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ddit.service.ForestTripService;
import kr.or.ddit.service.MemberService;
import kr.or.ddit.util.ScanUtil;

public class ForestTripView {

	protected void mainStart() {
		View view = View.MAIN;
		while (true) {
			switch (view) {
			case MAIN:
				view = main();
				break;
			case USER_HOME:
				view = userHome();
			case USER_LOGIN:
				view = userLogin();
				break;
			case USER_SIGNUP:
				view = signUp();
				break;
			case USER_FIND:
				view = userFind();
				break;
//			case ID_FIND:
//				view = idFind();
//				break;
//			case PW_FIND:
//				view = pwFind();
//				break;
			case USER_MYPAGE:
				view = userMyPage();
				break;
			case USER_MYPAGE_EDIT:
				view = userMyPageEdit();
				break;
			case USER_MYPAGE_FOREST_REVIEW:
				view = userMyPageForestReview();
				break;
			case USER_QUIT:
				view = userQuit();
				break;
			case ADMIN_LOGIN:
				view = adminLogin();
				break;
			case ADMIN_MAIN:
				view = adminMain();
				break;
			case ADMIN_REGISTER:
				view = adminRegister();
				break;
			case ADMIN_EDIT:
				view = adminEdit();
				break;
			case ADMIN_LIST:
				view = adminList();
				break;
			case ADMIN_REGISTER_ROLL:
				view = adminRegisterRoll();
				break;
			case ADMIN_FOREST_LIST:
				view = adminForestList();
				break;
			case ADMIN_FOREST_REGISTER:
				view = adminForestRegister();
				break;
			case ADMIN_FOREST_DETAIL_REGI:
				view = adminForestDetailRegi();
				break;
			case ADMIN_FOREST_EDIT:
				view = adminForestEdit();
				break;
			case ADMIN_STATISTIC:
				view = adminStatistic();
				break;
			case FOREST_SEARCH:
				view = forestSearch();
				break;
			case FOREST_DETAIL:
				view = forestDetail();
				break;
			case FOREST_BOOKING:
				view = forestBooking();
				break;
			case FOREST_LIST:
				view = forestList();
				break;
			default:
				break;
			}
		}
	}

	static public Map<String, Object> sessionStorage = new HashMap<>();
	ForestTripService forestService = ForestTripService.getInstance();
	MemberService memberService = MemberService.getInstance();

	// 돌아가기와 0을 눌러 돌아가기는 다른 기능

	protected View main() {
		// TODO Auto-generated method stub
		// 관리자모드는 숨김처리해야함

		System.out.println("1.로그인하기");
		System.out.println("2.비회원으로 조회하기");
		System.out.println("3.회원가입하기");
		System.out.println("4.아이디 비밀번호 찾기");

		int sel = ScanUtil.nextInt("메뉴 번호를 입력하시오 : ");

		switch (sel) {
		case 1:
			return View.USER_LOGIN;
		case 2:
			return View.FOREST_LIST;
		case 3:
			return View.USER_SIGNUP;
		case 4:
			return View.USER_FIND;
		default:
			return View.MAIN;
		}

	}

	protected View userLogin() {
		System.out.println("로그인메뉴");
		System.out.println("로그인 하기");
		String user_id = ScanUtil.nextLine("아이디 : ");
		String user_pw = ScanUtil.nextLine("비밀번호 : ");

		List<Object> param = new ArrayList<Object>();
		param.add(user_id);
		param.add(user_pw);

		boolean flag = true;// memberService.login(param);
		if (!flag) {
			System.out.println("ID 또는 PW가 일치하지 않습니다.");
			return View.USER_LOGIN;
		}

		View view = (View) sessionStorage.get("view");
		if (view == null)
			return View.MAIN;

		return view;
	}

	protected View userHome() {
		System.out.println("1. 휴양림 검색하기");
		System.out.println("2. 마이페이지로 가기");
		System.out.println("3. 로그아웃하기");

		int sel = ScanUtil.nextInt("메뉴를 입력하세요 : ");

		if (sel == 1) {
			return View.FOREST_SEARCH;
		} else if (sel == 2) {
			return View.USER_MYPAGE;
		} else if (sel == 3) {
			return View.MAIN;
		}
		return View.MAIN;
	}

	protected View signUp() {
		// 값 입력 체크 알고리즘 필요

		System.out.println("회원가입 페이지입니다.");

		String user_id = ScanUtil.nextLine("아이디를 입력하세요 : ");
		String user_pw = ScanUtil.nextLine("PW를 입력하세요 : ");
		String user_pw_check = ScanUtil.nextLine("PW를  재입력하세요 : ");

		String user_name = ScanUtil.nextLine("이름을 입력하세요 : ");
		String user_phone = ScanUtil.nextLine("휴대폰 번호를 입력하세요 : ");

		// 주소 검색 알고리즘 필요
		System.out.println("---------------주소 검색 : ");

		// 돌아가기 알고리즘 필요
		System.out.println("돌아가기");

		return null;
	}

	protected View userFind() {
		System.out.println("아이디/비밀번호찾기 페이지입니다.");

		int sel = ScanUtil.nextInt("메뉴번호를 입력하세요 : ");
		if (sel == 1) {
			System.out.println("1. 아이디 찾기");
			String name = ScanUtil.nextLine("성함을 입력하세요 : ");
			String phone = ScanUtil.nextLine("전화번호를 입력하세요 : ");
			System.out.println("돌아가기");
		} else if (sel == 2) {
			System.out.println("2. 비밀번호 찾기");
			String id = ScanUtil.nextLine("아이디를 입력하세요 : ");
			String name = ScanUtil.nextLine("성함을 입력하세요 : ");

			boolean check_flag = false;
			if (check_flag == true) {
				String pw = ScanUtil.nextLine("바꿀 비밀번호를 입력하세요 : ");
				String pw_check = ScanUtil.nextLine("비밀번호 재확인 : ");

				System.out.println("돌아가기");
			}
		}
		return null;
	}

//	protected View idFind() {
//		System.out.println("아이디 찾기 페이지입니다.");
//
//		System.out.println("성함을 입력하세요");
//		System.out.println("전화번호를 입력하세요");
//
//		System.out.println("돌아가기");
//		return null;
//	}

//	protected View pwFind() {
//		System.out.println("비밀번호 찾기 페이지입니다.");
//
//		System.out.println("아이디를 입력하세요");
//		System.out.println("성함을 입력하세요");
//		System.out.println("바꿀 비밀번호를 입력하세요");
//		System.out.println("비밀번호 재확인");
//
//		System.out.println("돌아가기");
//		return null;
//	}

	////////////////////
	protected View userMyPage() {
		System.out.println("사용자 마이페이지");
		
		System.out.println("돌아가기");
		String pw = ScanUtil.nextLine("마이페이지에 들어가기 위해 비밀번호를 입력하세요 : ");
		
		boolean flag = false;
		if(!flag) {
			String pw_re = ScanUtil.nextLine("비밀번호를 재 입력하세요 : ");
			
		}else {
			int sel = ScanUtil.nextInt("메뉴 번호를 선택하세요 : ");
			System.out.println("1. 사용자 정보 수정");
			System.out.println("2. 휴양림 / 숙소 리뷰 작성");
			System.out.println("3. 회원탈퇴");
			System.out.println("0. 돌아가기");
			
			if(sel == 1) {
				return View.USER_MYPAGE_EDIT;
			}else if(sel == 2) {
				return View.USER_MYPAGE_FOREST_REVIEW;
			}else if(sel == 3) {
				
			}
		}
		
		return null;
	}

	protected View userMyPageEdit() {
		System.out.println("사용자정보 수정");
		System.out.println("사용자는 비밀번호, 전화번호, 주소를 변경할 수 있음");
		System.out.println("비밀번호는 1, 전화번호는 2, 주소는 3을 입력하세요");

		System.out.println("돌아가기");
		return null;
	}

	protected View userMyPageForestReview() {
		System.out.println("마이페이지 휴양림 리뷰작성 페이지");
		System.out.println("평점 입력");
		System.out.println("후기 입력");

		System.out.println("돌아가기");
		return null;
	}
	
	protected View userQuit() {
		
		return null;
	}

	protected View adminLogin() {
		System.out.println("어드민 로그인 페이지");
		System.out.println("관리자 아이디를 입력하세요 : ");
		System.out.println("관리자 비밀번호를 입력하세요 : ");

		return null;
	}

	protected View adminMain() {
		System.out.println("어드민 메인 페이지입니다.");
		// 최고관리자일경우
//		System.out.println("1. 관리자 등록");
		System.out.println("1. 관리자 목록");
//		System.out.println("3. 관리자 권한 수정");
		System.out.println("2. 휴양림 목록");
//		System.out.println("5. 휴양림 추가");
//		System.out.println("6. 휴양림 수정");

		// 도담당자일경우
		System.out.println("1. 계정 정보 수정");
		System.out.println("2. 휴양림 목록");

		// 휴양림담당자일경우
		System.out.println("1. 계정 정보 수정");
		System.out.println("2. 휴양림 목록");
		return null;
	}

	protected View adminRegister() {
		System.out.println("어드민 등록 페이지");
		System.out.println("등록할 어드민 아이디 입력 : ");
		System.out.println("등록할 어드민 비밀번호 입력 : ");
		System.out.println("비밀번호 확인 : ");

		System.out.println("돌아가기");

		return null;
	}

	protected View adminEdit() {
		System.out.println("어드민 정보 수정 페이지");
		System.out.println("0을 입력해 돌아가기");
		System.out.println("1. 비밀번호, 사용자명 등 일반정보 수정");
		System.out.println("2. 권한 수정 / 권한 수정은 최고 관리자만 보이게");

		System.out.println("돌아가기");
		return null;
	}

	protected View adminList() {
		System.out.println("최고관리자의 관리자 정보 수정을 위한 목록 페이지");

		System.out.println("모든 어드민 목록 출력(출력시에는 모든 정보가 포함되어 나오게");
		System.out.println("목록 번호를 눌러 수정 페이지로 넘어가기");

		System.out.println("돌아가기");
		return null;
	}

	protected View adminRegisterRoll() {
		System.out.println("어드민 권한 수정 페이지");
		System.out.println("이 아이디가 가지고 있는 권한 출력");
		System.out.println("0을 입력해 돌아가기");
		System.out.println("1. 00도 담당자로 수정");
		System.out.println("2. 00도 00시 00 휴양림 담당자로 수정");

		// 권한이 null 또는 없을시
		System.out.println("권한 부여 : 1. 도담당자 2. 휴양림 담당자");

		// 1. 도 담당자일경우
		System.out.println("도를 숫자로 입력하시오 : 1. 강원, 2.경기.... ");

		// 2. 휴양림 담당자일경우
		System.out.println("휴양림 검색 또는 키워드 입력");

		return null;
	}

	protected View adminForestList() {
		System.out.println("어드민의 휴양림 목록 출력 페이지");

		System.out.println("어드민 계정이 도담당자일경우 도 단위의 목록 출력");
		System.out.println("어드민 계정이 휴양림담당자일경우 해당 휴양림만 출력");

		System.out.println("수정할 휴양림의 목록번호를 입력");

		return null;
	}

	protected View adminForestRegister() {

		System.out.println("어드민의 휴양림 등록");
		System.out.println("1. 휴양림명 등록");
		System.out.println("2. 기본주소(시도, 시군)는 자동 입력");
		System.out.println("3. 주소 검색 후 상세주소 입력");
		System.out.println("4. 객실 등록");
		System.out.println("5. 최대 이용인원 등록");
		System.out.println("6. 이용료 등록");

		return null;
	}

	protected View adminForestEdit() {
		System.out.println("어드민의 휴양림 정보 수정");

		System.out.println("목록에서 선택 후 상세 정보로 이동");
		System.out.println("상세 정보에는 최초로 등록한 모든 정보가 출력되고 사용자는" + "컬럼을 선택해서 그 정보를 수정");

		System.out.println("수정 시 경고문");
		System.out.println("예시) 정보를 바꿀 시 사용자가 이를 모를 수도 있다~~ 이런식으로");

		System.out.println("돌아가기");

		return null;

	}

	protected View adminForestDetailRegi() {
		System.out.println("휴양림 상세정보 등록(휴양림 목록 페이지에서 접근)");
		System.out.println("주의사항, 공지사항 작성");

		System.out.println("공지사항 입력: ");

		System.out.println("주의사항 입력: ");

		return null;
	}

	protected View adminStatistic() {
		System.out.println("각 휴양림별 통계 출력 페이지");
		System.out.println("최고 관리자는 모든 휴양림 통계");
		System.out.println("도 담당자는 관할 구역 휴양림 통계");
		System.out.println("휴양림 관리자는 관할 휴양림 통계");

		System.out.println("통계는 이용자 수(00가구 00명이 참가 정도), 리뷰 점수 정도");

		System.out.println("*******프로젝트 진행 속도에 따라 통계 생략할 수 있음*******");

		return null;

	}

	protected View forestSearch() {
		// 사용자 휴양림 검색 페이지
		System.out.println("휴양림 검색 페이지, 비회원도 가능 단 예약시에는 회원만 가능");
		System.out.println("검색 조건 : 1.도 입력   2.시(군)입력  3.날짜선택  4.인원입력 ");
		System.out.println("*******프로젝트 진행 속도에 따라 사용자는 1박만 하게 해야 할 수 있음*******");

		return null;
	}

	protected View forestList() {
		// 사용자 휴양림 목록 페이지
		System.out.println("forestSearch 조건에 부합하는 휴양림 목록 출력");
		System.out.println("사용자는 목록에서 번호 입력해서 상세 정보 조회 페이지로 이동");

		System.out.println("돌아가기");
		return null;
	}

	protected View forestDetail() {
		System.out.println("forestList에서 사용자가 선택한 휴양림의 상세정보 출력");
		System.out.println("상세정보로는 휴양림 소개, 위치, 시설물, 공지사항, 리뷰 등");

		System.out.println("리뷰보기");
		System.out.println("예약하기");
		System.out.println("돌아가기");
		return null;
	}

	protected View forestBooking() {
		System.out.println("예약하기 페이지");
		System.out.println("검색조건에서 입력된 정보를 자동으로 입력");

		System.out.println("돌아가기");
		return null;
	}

}
