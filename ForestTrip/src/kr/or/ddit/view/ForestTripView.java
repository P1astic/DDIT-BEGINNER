package kr.or.ddit.view;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ddit.controller.EgovController;
import kr.or.ddit.service.ForestTripService;
import kr.or.ddit.service.MemberService;
import kr.or.ddit.util.ScanUtil;
import kr.or.ddit.vo.BookVo;

public class ForestTripView {
	public static ArrayList<Object> address_output = new ArrayList<>();

	protected void mainStart() throws IOException {
		View view = View.MAIN;
		while (true) {
			switch (view) {
			case MAIN:
				view = main();
				break;
			case USER_HOME:
				view = userHome();
				break;
			case USER_LOGIN:
				view = userLogin();
				break;
			case USER_SIGNUP:
				view = signUp();
				break;
			case USER_FIND:
				view = userFind();
				break;
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
			case ADMIN_FOREST_LIST:
				view = adminForestList();
				break;
			case ADMIN_FOREST_REGISTER:
				view = adminForestRegister();
				break;
			case ADMIN_FOREST_DETAIL_REGI:
				view = adminForestRoomInsert();
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
			default:
				break;
			}
		}
	}

	static public Map<String, Object> sessionStorage = new HashMap<>();
	ForestTripService forestService = ForestTripService.getInstance();
	MemberService memberService = MemberService.getInstance();
	EgovController egov = new EgovController();
	Calendar cal = Calendar.getInstance();
	LocalDate now = LocalDate.now();

	// 돌아가기와 0을 눌러 돌아가기는 다른 기능

	protected View main() throws IOException {
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
		case 5:
			return View.ADMIN_LOGIN;
		case 6:
			return View.FOREST_BOOKING;
		default:
			return View.MAIN;
		}
	}

	/**
	 * @return 로그인 로직 구현 완료 24.4.13. 14:54 홍정호
	 */
	protected View userLogin() {
		System.out.println("로그인 하기");
		System.out.println("***'GB'를 입력해 돌아가기***");
		String user_id = ScanUtil.nextLine("아이디 : ");
		if (user_id.equals("GB")) {
			return View.USER_LOGIN;
		} else {
			String user_pw = ScanUtil.nextLine("비밀번호 : ");
			List<Object> param = new ArrayList<Object>();
			param.add(user_id);
			param.add(user_pw);

			boolean flag = memberService.userLogin(param);
			if (!flag) {
				System.out.println("ID 또는 PW가 일치하지 않습니다.");
				return View.USER_LOGIN;
			}
			return View.USER_HOME;
		}
	}

	protected View userHome() {
		Object user_name = sessionStorage.get("mem_id");
		System.out.println(user_name + "님 환영합니다.");
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

	/**
	 * @return 회원가입 기능 구현 완료 홍정호 4.16. 17:34
	 * @throws IOException
	 */
	protected View signUp() throws IOException {
		String user_id;
		String user_pw;
		String user_email;
		String user_phone;
		int cut = 0;
		int start = 0;
		int end = 0;
		int index = 0;
		int pageNo = 1;

		// 값 입력 체크 알고리즘 필요
		List<Object> param = new ArrayList<Object>();
		System.out.println("회원가입을 시작합니다.");

		String user_name = ScanUtil.nextLine("이름을 입력하세요 : ");
		// 아이디 입력 및 중복 검사
		do {
			user_id = ScanUtil.nextLine("아이디를 입력하세요: ");
			if (!isUsernameValid(user_id)) {
				System.out.println("아이디는 5자 이상 15자 미만의 영문과 숫자의 조합이어야 합니다.");
			}
		} while (!isUsernameValid(user_id) && isUsernameDuplicate(user_id));

		// 비밀번호 입력 및 검사
		do {
			user_pw = ScanUtil.nextLine("비밀번호를 입력하세요: ");
			if (!isPasswordValid(user_pw)) {
				System.out.println("비밀번호는 5자 이상 15자 미만의 영문, 숫자, 특수기호의 조합이어야 합니다.");
			}
		} while (!isPasswordValid(user_pw));

		// 이메일 입력 및 유효성 검사
		do {
			user_email = ScanUtil.nextLine("이메일을 입력하세요: ");
			if (!isEmailValid(user_email)) {
				System.out.println("유효하지 않은 이메일 형식입니다. 다시 입력해주세요.");
			}
		} while (!isEmailValid(user_email));

		// 전화번호 입력 및 형식 검사
		user_phone = ScanUtil.nextLine("전화번호를 입력하세요 (ex. 010-1234-5678): ");
		if (!isPhoneNumberValid(user_phone)) {
			System.out.println("유효하지 않은 전화번호 형식입니다.");
			// 전화번호 형식이 잘못되었을 경우, 원하는 방법으로 처리할 수 있습니다.
		}

		String user_addr = ScanUtil.nextLine("주소 도로명으로 검색 하세요 : ");
		egov.EgovController(user_addr);
		index = address_output.size();
		while (true) {
			if (sessionStorage.containsKey("pageNo")) {
				pageNo = (int) sessionStorage.remove("pageNo");
			}
			cut = 10;
			start = (pageNo - 1) * cut;
			end = pageNo * cut;

			for (int i = start; i < end; i++) {
				if (end > index) {
					for (i = start; i < index; i++) {
						System.out.println(i + 1 + "번 행 " + address_output.get(i));
					}
					break;
				}
				System.out.println(i + 1 + "번 행 " + address_output.get(i));
			}

			System.out.println("<이전페이지\t\t다음페이지>");
			String user_addr_input = ScanUtil.nextLine("본인이 거주하고 있는 주소의 행 번을 입력하세요(주소 재검색은 0번) : ");

			if (user_addr_input.charAt(0) == '<') {
				if (pageNo > 1) {
					sessionStorage.put("pageNo", --pageNo);
					start -= 10;
					end -= 10;
					continue;
				} else {
					continue;
				}
			} else if (user_addr_input.charAt(0) == '>') {
				if (end > index) {
					System.out.println("마지막페이지입니다.");
					sessionStorage.put("pageNo", pageNo);
					continue;
				}
				sessionStorage.put("pageNo", ++pageNo);
				continue;
			} else if (user_addr_input.charAt(0) == '0') {
				return View.USER_SIGNUP;
			} else {

				String addr = (String) address_output.get(Integer.parseInt(user_addr_input) - 1);
				String addr_detail = ScanUtil.nextLine("상세 주소 입력해주세요 : ");
				String addr_result = addr + " " + addr_detail;

//				// 돌아가기 알고리즘 필요
//				System.out.println("돌아가기");
				param.add(user_id);
				param.add(user_pw);
				param.add(user_name);
				param.add(addr_result);
				param.add(user_phone);
				param.add(user_email);

				System.out.println(param);

				memberService.userSignUp(param);
				System.out.println("회원가입이 완료되었습니다.");
				return View.MAIN;
			}

		}

	}

	// 아이디 유효성 검사 메서드
	public static boolean isUsernameValid(String user_name) {
		return user_name.matches("^[a-zA-Z][a-zA-Z\\d]{4,14}$");
	}

	// 아이디 중복 검사 메서드 (가정)
	public static boolean isUsernameDuplicate(String user_id) {
		// 여기에 중복 검사하는 코드를 추가하세요. - 김동오 24.4.13.17:26
		// 실제로는 데이터베이스에서 아이디를 검색하여 중복을 확인할 수 있습니다.

		return false; // 가정상 중복이 없다고 가정
	}

	// 비밀번호 유효성 검사 메서드
	public static boolean isPasswordValid(String user_pw) {
		return user_pw.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{5,15}$");
	}

	// 이메일 유효성 검사 메서드
	public static boolean isEmailValid(String user_email) {
		return user_email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
	}

	// 전화번호 형식 검사 메서드
	public static boolean isPhoneNumberValid(String user_phone) {
		return user_phone.matches("^\\d{3}-\\d{3,4}-\\d{4}$");
	}

	protected View userFind() {
		System.out.println("아이디/비밀번호찾기 페이지입니다.");

		System.out.println("1. 아이디 찾기");
		System.out.println("2. 비밀번호 찾기");
		System.out.println("***'GB'를 입력해 돌아가기***");

		String sel = ScanUtil.nextLine("메뉴번호를 입력하세요 : ");
		if (sel.charAt(0) - '0' == 1) {
			String user_name = ScanUtil.nextLine("성함을 입력하세요 : ");
			String user_phone = ScanUtil.nextLine("'-'기호를 포함해 전화번호를 입력하세요 : ");
			List<Object> param = new ArrayList<Object>();
			param.add(user_name);
			param.add(user_phone);

			boolean idCheck = memberService.idFind(param);
			if (idCheck == false) {
				System.out.println("회원정보와 일치하는 아이디가 없습니다.");
				return View.USER_FIND;
			}
			System.out.println("회원님의 ID는 " + sessionStorage.get("mem_id"));
			return View.MAIN;
		} else if (sel.charAt(0) - '0' == 2) {
			String user_id = ScanUtil.nextLine("아이디를 입력하세요 : ");
			String user_name = ScanUtil.nextLine("성함을 입력하세요 : ");
			List<Object> param = new ArrayList<Object>();
			param.add(user_id);
			param.add(user_name);

			List<Object> pw_input = new ArrayList<Object>();
			pw_input.add(user_id);
			boolean check_flag = memberService.pwFind(param);
			while (true) {
				if (check_flag == true) {
					String pw = ScanUtil.nextLine("바꿀 비밀번호를 입력하세요 : ");

					String pw_check = ScanUtil.nextLine("비밀번호 재확인 : ");
					if (pw.equals(pw_check)) {
						pw_input.add(pw);
						memberService.pwUpdate(pw_input);
						System.out.println("비밀번호가 바뀌었습니다.");
						return View.MAIN;
					} else {
						System.out.println("입력하신 비밀번호가 일치하지 않습니다.");
						continue;
					}
				}
			}

		} else if (sel.equals("GB")) {
			return View.MAIN;
		}
		return View.USER_HOME;
	}

	/**
	 * @return 마이페이지 접근 기능 구현 완료 24.4.15.홍정호 09:23 test
	 */
	protected View userMyPage() {// 1
		System.out.println("사용자 마이페이지");
		String pw = ScanUtil.nextLine("마이페이지에 들어가기 위해 비밀번호를 입력하세요 : ");
		List<Object> param = new ArrayList<Object>();
		param.add(pw);

		boolean flag = memberService.myPage(param);
		if (!flag) {
			System.out.println("비밀번호를 재입력하세요.");
			return View.USER_MYPAGE;
		} else {
			int sel = ScanUtil.nextInt("메뉴 번호를 선택하세요 : ");
			System.out.println("1. 사용자 정보 수정");
			System.out.println("2. 휴양림 / 숙소 리뷰 작성");
			System.out.println("3. 회원탈퇴");
			System.out.println("0. 돌아가기");

			if (sel == 1) {
				return View.USER_MYPAGE_EDIT;
			} else if (sel == 2) {
				return View.USER_MYPAGE_FOREST_REVIEW;
			} else if (sel == 3) {
				return View.USER_QUIT;
			} else if (sel == 0) {
				return View.USER_HOME;
			}
		}

		return View.USER_HOME;
	}

	protected View userMyPageEdit() throws IOException {
		int cut = 0;
		int start = 0;
		int end = 0;
		int index = 0;
		int pageNo = 1;
		System.out.println("사용자정보 수정페이지");
		System.out.println("비밀번호는 1, 전화번호는 2, 주소는 3, 메일은 4를 입력하세요");
		int sel = ScanUtil.nextInt("메뉴번호를 입력하세요 : ");
		if (sel == 1) {
			System.out.println("비밀번호 수정 페이지입니다.");
			String pw1 = ScanUtil.nextLine("변경할 비밀번호를 입력하세요 : ");
			String pw_re = ScanUtil.nextLine("비밀번호를 한번 더 입력하세요 : ");
			if (pw1.equals(pw_re)) {
				List<Object> pw_input = new ArrayList<Object>();
				pw_input.add(pw1);
				pw_input.add(sessionStorage.get("mem_id"));
				memberService.pwUpdate(pw_input);
				System.out.println("비밀번호가 변경되었습니다.");
				return View.USER_MYPAGE;
			}
		} else if (sel == 2) {
			String phone = ScanUtil.nextLine("변경할 전화번호를 입력하세요 : ");
			List<Object> phone_input = new ArrayList<Object>();
			phone_input.add(phone);
			phone_input.add(sessionStorage.get("mem_id"));
			memberService.phoneUpdate(phone_input);
			System.out.println("전화번호가 변경되었습니다.");
			return View.USER_MYPAGE;

		} else if (sel == 3) {

			// 주소검색 api 적용 필요
			System.out.println("-------------------주소검색");
			String address = ScanUtil.nextLine("변경할 주소를 입력하세요 : ");
			egov.EgovController(address);
			index = address_output.size();
			while(true) {
				if(sessionStorage.containsKey("pageNo")) {
					pageNo = (int) sessionStorage.remove("pageNo");
				}
				cut = 10;
				start = (pageNo -1) * cut;
				end = pageNo * cut;
				
				for(int i = start; i<end; i++) {
					if(end>index) {
						for(i=start; i<index; i++) {
							System.out.println(i+1 + "번 행 " + address_output.get(i));
						}
						break;
					}
					System.out.println(i+1 + "번 행 " + address_output.get(i));
				}
				System.out.println("<이전페이지\t\t다음페이지>");
				String user_addr_input = ScanUtil.nextLine("본인이 거주하고 있는 주소의 행 번을 입력하세요(주소 재검색은 0번) : ");
				
				if (user_addr_input.charAt(0) == '<') {
					if (pageNo > 1) {
						sessionStorage.put("pageNo", --pageNo);
						start -= 10;
						end -= 10;
						continue;
					} else {
						continue;
					}
				} else if (user_addr_input.charAt(0) == '>') {
					if (end > index) {
						System.out.println("마지막페이지입니다.");
						sessionStorage.put("pageNo", pageNo);
						continue;
					}
					sessionStorage.put("pageNo", ++pageNo);
					continue;
				} else if (user_addr_input.charAt(0) == '0') {
					return View.USER_SIGNUP;
				} else {

					String addr = (String) address_output.get(Integer.parseInt(user_addr_input) - 1);
					String addr_detail = ScanUtil.nextLine("상세 주소 입력해주세요 : ");
					String addr_result = addr + " " + addr_detail;
					
					List<Object> param = new ArrayList<>();
					param.add(sessionStorage.get("mem_id"));
					param.add(addr_result);
					memberService.addressUpdate(param);
					System.out.println("회원가입이 완료되었습니다.");
					return View.MAIN;
				}
			}
		} else if (sel == 4) {
			String mail = ScanUtil.nextLine("변경할 메일을 입력하세요 : ");
			List<Object> phone_input = new ArrayList<Object>();
			phone_input.add(mail);
			phone_input.add(sessionStorage.get("mem_id"));

			return View.USER_MYPAGE;
		}

		return View.USER_HOME;
	}

	protected View userMyPageForestReview() {
		// +user의 휴양림 갔다 온 곳 리스트 뽑기
		String sel = ScanUtil.nextLine("어떤 휴양림의 리뷰를 남기시겠습니까?");
		// +어떤 휴양림인지에 대한 코딩
		int sel2 = ScanUtil.nextInt("평점을 입력해주세요(최대5점)");
		if (sel2 <= 5 || sel2 >= 0) {
			System.out.println(sel + "의 평점을" + sel2 + "로 남기셨습니다.");
		} else {
			System.out.println("잘못입력하셨습니다. 리뷰작성페이지로 돌아갑니다.");
			return View.USER_MYPAGE_FOREST_REVIEW;
		}
		String sel3 = ScanUtil.nextLine(sel + "숙소의 후기를 작성해주십이오.");
		String sel4 = ScanUtil.nextLine("정말 등록하시겠습니까?(Y/N)(잘못입력시 메인페이지로 돌아갑니다)");
		if (sel4 == "Y" || sel == "y") {
			System.out.println("후기등록이 다음과 같이 등록되었습니다.");
			System.out.println(sel + "의 평점 : " + sel2 + "\n" + sel + "의 후기 : " + sel3);
			System.out.println("소중한 리뷰 감사합니다.");
			return View.USER_HOME;
		} else if (sel4 == "N" || sel4 == "n") {
			System.out.println("리뷰 등록을 취소하였습니다.");
			return View.USER_MYPAGE_FOREST_REVIEW;
		} else {
			System.out.println("리뷰 등록을 취소하였습니다.");
			return View.USER_HOME;
		}
	}

	protected View userQuit() {
		// 사용자 탈퇴기능, 탈퇴하기 페이지 진입시 패스워드 입력, 최종 탈퇴전 정말 탈퇴하시겠습니까? 메시지 출력, y/n y시 탈퇴 n은
		// 메인화면으로
		System.out.println("회원탈퇴 페이지입니다 패스워드를 입력해주세요");
		// 패스워드입력코드
		String pw = ScanUtil.nextLine("PW를 입력해주세요");
		// +sql id pw 동일 시 다음 줄
		// +sql id pw 틀릴 시 다시입력하기 or id찾기 or pw찾기 or 유저_홈가기

		String sel = ScanUtil.nextLine("회원 탈퇴를 진행하시겠습니까?(Y/N)");
		if (sel == "Y" || sel == "y") {
			String sel2 = ScanUtil.nextLine("정말 회원 탈퇴를 진행하시겠습니까?(Y/N)");
			if (sel2 == "Y" || sel2 == "y") {
				System.out.println("회원탈퇴가 진행이 완료되었습니다.");
				return View.USER_HOME;
			} else if (sel2 == "N" || sel2 == "n") {
				System.out.println("회원탈퇴 진행을 취소하였습니다.");
				return View.USER_HOME;
			} else {
				System.out.println("잘못 입력하셨습니다. 회원탈퇴 처음페이지로 돌아갑니다.");
				return View.USER_QUIT;
			}
		} else if (sel == "N" || sel == "n") {
			System.out.println("회원탈퇴 진행을 취소하였습니다.");
			return View.USER_HOME;
		} else {
			System.out.println("잘못 입력하셨습니다.");
			return View.USER_QUIT;
		}
	}

	/**
	 * @return 어드민 로그인 페이지 기능 구현 완료 24.4.15.홍정호 10:25
	 */
	protected View adminLogin() {
		System.out.println("어드민 로그인 페이지");
		List<Object> login_input = new ArrayList<Object>();
		String id_input = ScanUtil.nextLine("관리자 아이디를 입력하세요 : ");
		String pw_input = ScanUtil.nextLine("관리자 비밀번호를 입력하세요 : ");
		login_input.add(id_input);
		login_input.add(pw_input);

		boolean flag = memberService.adminLogin(login_input);
		if (!flag) {
			System.out.println("ID 또는 PW가 일치하지 않습니다.");
			return View.ADMIN_LOGIN;
		}

		return View.ADMIN_MAIN;
	}

	/**
	 * @return 어드민 메인페이지 기능 구현 완료 24.4.15. 홍정호 10:25
	 */
	protected View adminMain() {
		System.out.println("어드민 메인 페이지입니다.");
		System.out.println(sessionStorage.get("adm_id") + "님 환영합니다.");

		int roll = (int) sessionStorage.get("adm_roll"); // 로그인 후 roll 값을 여기다가 초기화
		if (roll == 1) {
			// 최고관리자일경우
			System.out.println("최고관리자");
			System.out.println("1. 관리자 등록");
			System.out.println("2. 관리자 목록");
			System.out.println("4. 휴양림 목록");
			System.out.println("5. 휴양림 기본정보 추가");
			System.out.println("6. 휴양림 세부정보 추가");
			System.out.println("7. 휴양림 수정");
			System.out.println("8. 휴양림 통계조회");

			int sel = ScanUtil.nextInt("메뉴 번호를 입력하세요 : ");
			if (sel == 1) {
				return View.ADMIN_REGISTER;
			} else if (sel == 2) {
				return View.ADMIN_LIST;
			} else if (sel == 3) {
//				return View.ADMIN_REGISTER_ROLL;
			} else if (sel == 4) {
				return View.ADMIN_FOREST_LIST;
			} else if (sel == 5) {
				return View.ADMIN_FOREST_REGISTER;
			} else if (sel == 6) {
				return View.ADMIN_FOREST_DETAIL_REGI;
			} else if (sel == 7) {
				return View.ADMIN_FOREST_EDIT;
			} else if (sel == 8) {
				return View.ADMIN_STATISTIC;
			}
		} else if (roll == 2) {
			// 도담당자일경우
			System.out.println("도담당자");
			System.out.println("1. 계정 정보 수정\t2. 휴양림 목록");
			System.out.println("3. 휴양림 기본정보 추가\t4. 휴양림 세부정보 추가");
			System.out.println("5. 휴양림 수정\t6. 휴양림 통계조회");

			int sel = ScanUtil.nextInt("메뉴 번호를 입력하세요 : ");
			if (sel == 1) {
				return View.ADMIN_EDIT;
			} else if (sel == 2) {
				return View.ADMIN_FOREST_LIST;
			} else if (sel == 3) {
				return View.ADMIN_FOREST_REGISTER;
			} else if (sel == 4) {
				return View.ADMIN_FOREST_DETAIL_REGI;
			} else if (sel == 5) {
				return View.ADMIN_FOREST_EDIT;
			} else if (sel == 6) {
				return View.ADMIN_STATISTIC;
			}
		} else if (roll == 3) {
			// 휴양림담당자일경우
			System.out.println("휴양림담당자");
			System.out.println("1. 계정 정보 수정");
			System.out.println("2. 휴양림 목록");
			System.out.println("3. 휴양림 기본정보 추가");
			System.out.println("4. 휴양림 세부정보 추가");
			System.out.println("5. 휴양림 수정");
			System.out.println("6. 휴양림 통계조회");

			int sel = ScanUtil.nextInt("메뉴 번호를 입력하세요 : ");
			if (sel == 1) {
				return View.ADMIN_EDIT;
			} else if (sel == 2) {
				return View.ADMIN_FOREST_LIST;
			} else if (sel == 3) {
				return View.ADMIN_FOREST_REGISTER;
			} else if (sel == 4) {
				return View.ADMIN_FOREST_DETAIL_REGI;
			} else if (sel == 5) {
				return View.ADMIN_FOREST_EDIT;
			} else if (sel == 6) {
				return View.ADMIN_STATISTIC;
			}
		}
		return View.ADMIN_MAIN;
	}

	/**
	 * @return 관리자 등록 기능 수정완료 - 홍정호 4.15. 18:50
	 */
	protected View adminRegister() {
		System.out.println("어드민 등록 페이지");
		List<Object> input = new ArrayList<Object>();
		System.out.println("생성하려는 관리자의 권한은 어떻게 되십니까?");
		System.out.println("1. 최고관리자, 2. 도담당자, 3. 휴양림담당자");
		int roll = ScanUtil.nextInt("생성하려는 관리자의 권한은 어떻게 되십니까?");
		if (roll == 1) {
			int code = 9999999;
			String id = ScanUtil.nextLine("등록할 어드민 아이디 입력 : ");
			String pw = ScanUtil.nextLine("등록할 어드민 비밀번호 입력 : ");
			String pw_re = ScanUtil.nextLine("비밀번호 확인 : ");
			if (pw.equals(pw_re)) {
				input.add(code);
				input.add(id);
				input.add(pw);
				input.add(roll);
				return View.ADMIN_MAIN;
			} else {
				System.out.println("입력하신 비밀번호가 일치하지 않습니다.");
				return View.ADMIN_REGISTER;
			}
		} else if (roll == 2) {
			int cut = 0;
			int start = 0;
			int end = 0;
			int index = 1;
			int count = 0;
			List<Map<String, Object>> sidoCode = new ArrayList<>();
			while (true) {
				System.out.println("지역코드(시도) 불러오기");
				int pageNo = 1;
				if (sessionStorage.containsKey("pageNo")) {
					pageNo = (int) sessionStorage.remove("pageNo");
				}
				cut = 5;
				start = 1 + (pageNo - 1) * cut;
				end = pageNo * cut;

				List<Object> page = new ArrayList<Object>();
				page.add(start);
				page.add(end);

				sidoCode = forestService.sidoList(page);

				if (sidoCode != null && !sidoCode.isEmpty()) {
					for (Map<String, Object> codeVo : sidoCode) {
						System.out.println(index++ + "번 행 " + codeVo);
					}
				}
				System.out.println("<이전페이지\t\t다음페이지>");
				String num = ScanUtil.nextLine("행 번호를 입력하세요 : "); // num-1

				if (num.charAt(0) == '<') {
					if (pageNo > 1) {
						sessionStorage.put("pageNo", --pageNo);
						start -= 10;
						end -= 10;
						index -= 10;
						count -= 5;
						continue;
					} else {
						continue;
					}
				} else if (num.charAt(0) == '>') {
					if (end > index) {
						System.out.println("마지막페이지입니다.");
						sessionStorage.put("pageNo", pageNo);
						continue;
					}
					sessionStorage.put("pageNo", ++pageNo);
					count += 5;
					continue;
				} else {
					BigDecimal code = (BigDecimal) sidoCode.get(Integer.parseInt(num) - 1 - count).get("CODE");
					System.out.println(code);
					String id = ScanUtil.nextLine("등록할 어드민 아이디 입력 : ");
					String pw = ScanUtil.nextLine("등록할 어드민 비밀번호 입력 : ");
					String pw_re = ScanUtil.nextLine("비밀번호 확인 : ");
					if (pw.equals(pw_re)) {
						input.add(code);
						input.add(id);
						input.add(pw);
						input.add(roll);
						memberService.adminSignUp(input);
						System.out.println("등록되었습니다.");
						return View.ADMIN_MAIN;
					} else {
						System.out.println("입력하신 비밀번호가 일치하지 않습니다.");
						return View.ADMIN_REGISTER;
					}
				}
			}

		} else if (roll == 3) {
			int cut = 0;
			int start = 0;
			int end = 0;
			int index = 1;
			int count = 0;
			List<Map<String, Object>> sigunCode = new ArrayList<>();
			while (true) {
				System.out.println("지역코드(시군)불러오기");
				int pageNo = 1;
				if (sessionStorage.containsKey("pageNo")) {
					pageNo = (int) sessionStorage.remove("pageNo");
				}
				cut = 15;
				start = 1 + (pageNo - 1) * cut;
				end = pageNo * cut;

				List<Object> page = new ArrayList<Object>();
				page.add(start);
				page.add(end);

				sigunCode = forestService.sigunList(page);

				if (sigunCode != null && !sigunCode.isEmpty()) {
					for (Map<String, Object> codeVo : sigunCode) {
						System.out.println(index++ + "번 행 " + codeVo);
					}
				}

				System.out.println("<이전페이지\t\t다음페이지>");
				String num = ScanUtil.nextLine("행 번호를 입력하세요 : "); // num-1

				if (num.charAt(0) == '<') {
					if (pageNo > 1) {
						sessionStorage.put("pageNo", --pageNo);
						start -= 30; // cut * 2도 고려하기
						end -= 30; // cut * 2도 고려하기
						index -= 30; // cut * 2도 고려하기
						count -= 15;
						continue;
					} else {
						continue;
					}
				} else if (num.charAt(0) == '>') {
					if (end > index) {
						System.out.println("마지막페이지입니다.");
						sessionStorage.put("pageNo", pageNo);
						continue;
					}
					sessionStorage.put("pageNo", ++pageNo);
					count += 15;
					continue;
				} else {
					BigDecimal code = (BigDecimal) sigunCode.get(Integer.parseInt(num) - 1 - count).get("CODE");
					String id = ScanUtil.nextLine("등록할 어드민 아이디 입력 : ");
					String pw = ScanUtil.nextLine("등록할 어드민 비밀번호 입력 : ");
					String pw_re = ScanUtil.nextLine("비밀번호 확인 : ");
					if (pw.equals(pw_re)) {
						input.add(code);
						input.add(id);
						input.add(pw);
						input.add(roll);
						memberService.adminSignUp(input);
						System.out.println("등록되었습니다.");
						return View.ADMIN_MAIN;
					} else {
						System.out.println("입력하신 비밀번호가 일치하지 않습니다.");
						return View.ADMIN_REGISTER;
					}
				}
			}
		}
		return View.ADMIN_REGISTER;
	}

	/**
	 * @return 로직 구현 완료 -홍정호 24.4.15. 19:48
	 */
	protected View adminEdit() {
		if (sessionStorage.get("editFlag") == null) {
			System.out.println("접속한 어드민 계정의 정보 수정 페이지");
			System.out.println("0을 입력해 돌아가기");
			System.out.println("1. 비밀번호 수정");
			int sel = ScanUtil.nextInt("메뉴 번호를 입력하세요 : ");
			if (sel == 1) {
				String pw = ScanUtil.nextLine("수정할 비밀번호를 입력하세요 : ");
				String pw_re = ScanUtil.nextLine("비밀번호 확인 : ");
				List<Object> pw_update = new ArrayList<Object>();
				if (pw.equals(pw_re)) {
					pw_update.add(pw);
					pw_update.add(sessionStorage.get("adm_id"));
					memberService.adminPWUpdate(pw_update);
					System.out.println("비밀번호가 변경되었습니다.");
					return View.ADMIN_MAIN;
				} else {
					System.out.println("입력하신 비밀번호가 일치하지 않습니다.");
					return View.ADMIN_EDIT;
				}
			} else if (sel == 0) {
				System.out.println("돌아가기");
				return View.ADMIN_MAIN;
			}
		} else {
			// 로직 추가중 4.15. 18:53
			System.out.println("선택한 어드민 계정의 정보 수정 페이지");
			System.out.println("0을 입력해 돌아가기");
			System.out.println("1. 비밀번호 수정");
			int sel = ScanUtil.nextInt("메뉴 번호를 입력하세요 : ");
			if (sel == 1) {
				String pw = ScanUtil.nextLine("수정할 비밀번호를 입력하세요 : ");
				String pw_re = ScanUtil.nextLine("비밀번호 확인 : ");
				List<Object> pw_update = new ArrayList<Object>();
				if (pw.equals(pw_re)) {
					pw_update.add(pw);
					pw_update.add(sessionStorage.get("editFlag"));
					memberService.adminPWUpdate(pw_update);
					System.out.println("선택한 관리자의 비밀번호가 변경되었습니다.");
					return View.ADMIN_MAIN;
				} else {
					System.out.println("입력하신 비밀번호가 일치하지 않습니다.");
					return View.ADMIN_EDIT;
				}
			} else if (sel == 0) {
				System.out.println("돌아가기");
				return View.ADMIN_MAIN;
			}
		}
		return View.ADMIN_MAIN;
	}

	protected View adminList() {
		int cut = 0;
		int start = 0;
		int end = 0;
		int index = 1;
		int count = 0;
		List<Map<String, Object>> adminList = new ArrayList<>();
		while (true) {
			System.out.println("최고관리자의 관리자 정보 수정을 위한 목록 페이지");
			// 1번 비번 수정 -- 작성중, 4.15. 17:32, 2번 권한 수정
			int pageNo = 1;
			if (sessionStorage.containsKey("pageNo")) {
				pageNo = (int) sessionStorage.remove("pageNo");
			}
			cut = 15;
			start = 1 + (pageNo - 1) * cut;
			end = pageNo * cut;

			List<Object> page = new ArrayList<Object>();
			page.add(start);
			page.add(end);

			adminList = memberService.adminList(page);
			if (adminList != null && !adminList.isEmpty()) {
				for (Map<String, Object> adminVo : adminList) {
					System.out.println(index++ + "번 행 " + adminVo);
				}
			}

			System.out.println("<이전페이지\t\t다음페이지>");
			String num = ScanUtil.nextLine("행 번호를 입력하세요 : "); // num-1
			if (num.charAt(0) == '<') {
				if (pageNo > 1) {
					sessionStorage.put("pageNo", --pageNo);
					start -= 30;
					end -= 30;
					index -= 30;
					count -= 15;
					continue;
				} else {
					continue;
				}
			} else if (num.charAt(0) == '>') {
				if (end > index) {
					System.out.println("마지막페이지입니다.");
					sessionStorage.put("pageNo", pageNo);
					continue;
				}
				count += 15;
				sessionStorage.put("pageNo", ++pageNo);
				continue;
			} else {
				String adm_id = (String) adminList.get(Integer.parseInt(num) - 1 - count).get("ADM_ID");
				sessionStorage.put("editFlag", adm_id);
				return View.ADMIN_EDIT;

			}
		}

	}

	protected View adminForestList() {

		int cut = 0;
		int start = 0;
		int end = 0;
		int index = 1;
		int count = 0;
		List<Map<String, Object>> forestList = new ArrayList<>();
		int code = (int) sessionStorage.get("adm_code");
		int roll = (int) sessionStorage.get("adm_roll");
		String adm_no = (String) sessionStorage.get("adm_no");
		// sql로 로그인 한 계정의 roll 가져오기

		System.out.println("어드민의 휴양림 목록 출력 페이지");

		if (roll == 1) {
		} else if (roll == 2) {
			// 도담당자 << 도 밑에 있는거 전부다
		} else if (roll == 3) {
			// 휴양림담당자 <<휴양림담당장에 배정된 휴양림만
			int pageNo = 1;
			cut = 10;
			start = 1 + (pageNo - 1) * cut;
			end = pageNo * cut;

			List<Object> param = new ArrayList<>();
			param.add(start);
			param.add(end);
//			param.add(code);
			param.add(adm_no);

			// 최고관리자 << 싹다
			forestList = forestService.ForestList(param);

			if (forestList != null && !forestList.isEmpty()) {
				for (Map<String, Object> forestVo : forestList) {
					System.out.println(index++ + "번 행 " + forestVo);
				}
			}
			System.out.println("<이전페이지\t\t다음페이지>");
			String num = ScanUtil.nextLine("행 번호를 입력하세요 : "); // num-1
		}
		// sql selectList

		System.out.println("어드민 계정이 도담당자일경우 도 단위의 목록 출력");
		// sql selectList

		System.out.println("어드민 계정이 휴양림담당자일경우 해당 휴양림만 출력");

		// sql selectList

		System.out.println("수정할 휴양림의 목록번호를 입력");

		return null;
	}

	protected View adminForestRegister() {

		System.out.println("어드민의 휴양림 등록");

//		List<AdminVo> RoomList = forestService.roomInsert();
		String forest_name = ScanUtil.nextLine("1. 휴양림명 등록 :");
//		String forest_addr_base = ("2. 기본주소(시도, 시군)는 자동 입력");
		// sql을 통해 관리자 계정의 시도 시군 스트링 값 가지고 오기

		String forest_addr_detail = ScanUtil.nextLine("3. 주소 검색 후 상세주소 입력 :");
		String room_name = ScanUtil.nextLine("4. 객실 명을 입력하세요. 추가 객실은 상세 정보에서 등록이 가능합니다. :");
		int room_qty = ScanUtil.nextInt("5. 최대 이용인원 등록 : ");

		String room_notice = ScanUtil.nextLine("6. 공지사항 : ");

		List<Object> param = new ArrayList();
		param.add(forest_name);
		param.add(forest_addr_detail);
		param.add(room_name);
		param.add(room_qty);
		param.add(room_notice);

		forestService.forestInsert(param);
		return View.ADMIN_MAIN;
	}

	protected View adminForestEdit() {
		System.out.println("Admin의 휴양림 정보 수정 페이지입니다.");
//	    System.out.println(forestList);
		int cut = 0;
		int start = 0;
		int end = 0;
		int index = 1;
		int count = 0;
		List<Map<String, Object>> forestList = new ArrayList<>();
		List<Object> param = new ArrayList<>();
		while (true) {
			System.out.println("휴양림 목록 불러오기");
			int pageNo = 1;
			if (sessionStorage.containsKey("pageNo")) {
				pageNo = (int) sessionStorage.remove("pageNo");
			}
			cut = 10;
			start = 1 + (pageNo - 1) * cut;
			end = pageNo * cut;

			List<Object> page = new ArrayList<>();
			page.add(start);
			page.add(end);
			page.add(sessionStorage.get("adm_no"));

			forestList = forestService.ForestList(page);

			if (forestList != null && !forestList.isEmpty()) {
				for (Map<String, Object> forestVo : forestList) {
					System.out.println(index++ + "번 행 " + forestVo);
				}
			}

			System.out.println("<이전페이지\t\t다음페이지>");
			String num = ScanUtil.nextLine("상세 정보를 보실 forest의 행 번호를 입력하세요 : "); // num-1

			if (num.charAt(0) == '<') {
				if (pageNo > 1) {
					sessionStorage.put("pageNo", --pageNo);
					start -= 20;
					end -= 20;
					index -= 20;
					count -= 10;
					continue;
				} else {
					continue;
				}
			} else if (num.charAt(0) == '>') {
				if (end > index) {
					System.out.println("마지막페이지입니다.");
					sessionStorage.put("pageNo", pageNo);
					continue;
				}
				sessionStorage.put("pageNo", ++pageNo);
				count += 10;
				continue;
			} else {
				/// 로직 추가 필요 4.16. 16:21 김동오 홍정호

				System.out.println("선택하신 휴양림의 상세정보입니다.");

//				List<Object> param;
//				List<Object> a=forestService.ForestList(param);
				int sel = ScanUtil.nextInt("수정할 메뉴를 선택해주세요.");
				System.out.println("1.휴양림이름  2.휴양림 소개  3.휴양림 공지  4.휴양림 주소");
				if (sel == 1) {
					System.out.println("정보 수정 시 사용자가 이를 모를 수도 있습니다.");
					String Forest_Name = ScanUtil.nextLine("수정 할 휴양림 이름을 입력해주세요.");

				} else if (sel == 2) {
					System.out.println("정보 수정 시 사용자가 이를 모를 수도 있습니다.");
					String Forest_Intro = ScanUtil.nextLine("수정 할 휴양림 소개를 입력해주세요.");

				} else if (sel == 3) {
					System.out.println("정보 수정 시 사용자가 이를 모를 수도 있습니다.");
					String Forest_Notice = ScanUtil.nextLine("수정 할 휴양림 공지를 입력해주세요.");

				} else if (sel == 4) {
					System.out.println("정보 수정 시 사용자가 이를 모를 수도 있습니다.");
					String Forest_Addr = ScanUtil.nextLine("수정 할 휴양림 주소를 입력해주세요.");

				} else {
					System.out.println("잘못입력하셨습니다 휴양림 정보 수정페이지로 돌아갑니다.");
					return View.ADMIN_FOREST_EDIT;
				}

////				String forest_No = ScanUtil.nextLine("상세 정보를 보실 FOREST_NO를 입력해주세요.");
//				// sql selectList, 상세정보는 객실명, 수용인원, 공지사항
//				System.out.println("상세 정보에는 최초로 등록한 모든 정보가 출력되고 사용자는" + "컬럼을 선택해서 그 정보를 수정");
//				
////				int num = ScanUtil.nextInt("컬럼 번호를 입력하세요.");
//				// pagination 구현 필요
//
//				System.out.println("수정 시 경고문");
//				System.out.println("예시) 정보를 바꿀 시 사용자가 이를 모를 수도 있다~~ 이런식으로");
//
//				System.out.println("돌아가기");

				return View.ADMIN_MAIN;
			}
		}

	}

	protected View adminForestRoomInsert() {
		System.out.println("휴양림 객실 추가 등록(휴양림 목록 페이지에서 접근)");

		System.out.println("객실 추가등록(객실명, 수용인원)");// 공지사항은 객실에 종속 또는 휴양림 공지사항

		String room_name = ScanUtil.nextLine("객실명을 입력하세요 : ");
		int room_qty = ScanUtil.nextInt("수용인원을 입력하세요 : ");

		List<Object> param = new ArrayList();
		param.add(room_name);
		param.add(room_qty);

		forestService.forestRoomInsert(param);
		return View.ADMIN_MAIN;
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
		System.out.println("검색 조건 : 1.도 입력  또는 시(군)입력  3.인원입력 "); // 2. 날짜선택
		String input = ScanUtil.nextLine("1. 시도 또는 시군 입력 ex)강원, 강릉, 대전: ");
		// 시도 시군 검색 목록 보여주기 json 또는 생성자

		int people_qty = ScanUtil.nextInt("3. 인원 입력 : ");
		// 날짜는 현재날짜 기준으로

		// 날짜 선택 시 달력만들기

		int qty = ScanUtil.nextInt("인원을 입력해주세요 : ");
		// 인원입력 한 검색목록 보여주기

		System.out.println("*******프로젝트 진행 속도에 따라 사용자는 1박만 하게 해야 할 수 있음*******");

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
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		List<Map<String, Object>> list = new ArrayList<>();
		while (true) {
			list = memberService.bookflagList();
			for (Map<String, Object> map : list) {
				System.out.println(map.get("BOOK_D_START"));
				System.out.println(map.get("BOOK_D_END"));
			}

			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, 1);
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			int startDay = cal.get(Calendar.DAY_OF_WEEK);

			int currentDay = 1;
			System.out.println(year + "년 " + month + "월\n");
			System.out.println("일\t월\t화\t수\t목\t금\t토");

			for (int i = 1; i <= lastDay + startDay - 1; i++) {
				if (i < startDay) {
					System.out.print("\t");
				} else {
					// if
					System.out.printf("%02d\t", currentDay);
					currentDay++;
				}
				if (i % 7 == 0) {
					System.out.println();
				}
			}
			System.out.println();
			System.out.println("<이전달\t\t다음달>");
			String input = ScanUtil.nextLine("예약을 원하는 일자를 입력하세요 : ");

			if (input.charAt(0) == '<') {
				if (month == 4) {
					System.out.println("2024년 4월 이전으로는 갈 수 없습니다.");
					continue;
				}
				month--;
			} else if (input.charAt(0) == '>') {
				if (month == 6) {
					System.out.println("2024년 6월 이후로는 갈 수 없습니다.");
					continue;
				}
				month++;
			} else {
				System.out.println("해당 일자로 예약을 진행합니다.");
			}
		}

	}

}
