package kr.or.ddit.view;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ddit.controller.EgovController;
import kr.or.ddit.service.ForestTripService;
import kr.or.ddit.service.MemberService;
import kr.or.ddit.util.ScanUtil;

public class ForestTripView {
	public static ArrayList<Object> address_output = new ArrayList<>();

	protected void mainStart() throws IOException, ParseException {
		String logo = "\r\n"
				+ "'    _____                                        _     _                     ______                               _                  \r\n"
				+ "'   |  __ \\                                      | |   (_)                   |  ____|                             | |                 \r\n"
				+ "'   | |__) |   ___    ___   _ __    ___    __ _  | |_   _    ___    _ __     | |__      ___    _ __    ___   ___  | |_                \r\n"
				+ "'   |  _  /   / _ \\  / __| | '__|  / _ \\  / _` | | __| | |  / _ \\  | '_ \\    |  __|    / _ \\  | '__|  / _ \\ / __| | __|               \r\n"
				+ "'   | | \\ \\  |  __/ | (__  | |    |  __/ | (_| | | |_  | | | (_) | | | | |   | |      | (_) | | |    |  __/ \\__ \\ | |_                \r\n"
				+ "'   |_|  \\_\\  \\___|  \\___| |_| ____\\___|  \\__,_|  \\__| |_|  \\___/  |_| |_|   |_|   _   \\___/  |_|     \\___| |___/  \\__|               \r\n"
				+ "'                             |  __ \\                                             | |   (_)                                           \r\n"
				+ "'                             | |__) |   ___   ___    ___   _ __  __   __   __ _  | |_   _    ___    _ __                             \r\n"
				+ "'                             |  _  /   / _ \\ / __|  / _ \\ | '__| \\ \\ / /  / _` | | __| | |  / _ \\  | '_ \\                            \r\n"
				+ "'                             | | \\ \\  |  __/ \\__ \\ |  __/ | |     \\ V /  | (_| | | |_  | | | (_) | | | | |                           \r\n"
				+ "'    __  __                   |_|  \\_\\  \\___| |___/  \\___| |_|      \\_/    \\__,_|  \\__| |_|__\\___/  |_| |_|    _                      \r\n"
				+ "'   |  \\/  |                                                                   | |      / ____|               | |                     \r\n"
				+ "'   | \\  / |   __ _   _ __     __ _    __ _    ___   _ __ ___     ___   _ __   | |_    | (___    _   _   ___  | |_    ___   _ __ ___  \r\n"
				+ "'   | |\\/| |  / _` | | '_ \\   / _` |  / _` |  / _ \\ | '_ ` _ \\   / _ \\ | '_ \\  | __|    \\___ \\  | | | | / __| | __|  / _ \\ | '_ ` _ \\ \r\n"
				+ "'   | |  | | | (_| | | | | | | (_| | | (_| | |  __/ | | | | | | |  __/ | | | | | |_     ____) | | |_| | \\__ \\ | |_  |  __/ | | | | | |\r\n"
				+ "'   |_|  |_|  \\__,_| |_| |_|  \\__,_|  \\__, |  \\___| |_| |_| |_|  \\___| |_| |_|  \\__|   |_____/   \\__, | |___/  \\__|  \\___| |_| |_| |_|\r\n"
				+ "'                                      __/ |                                                      __/ |                               \r\n"
				+ "'                                     |___/                                                      |___/                                \r\n"
				+ "";
		System.out.println(logo);
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
//			case REVIEW_CHECK:
//				view = reviewCheck();
//				break;
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
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	// 돌아가기와 0을 눌러 돌아가기는 다른 기능

	protected View main() throws IOException {
		// TODO Auto-generated method stub

		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t\t★Welcome★");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		System.out.println("\t\t\t\t\t\t\t\t\t1.로그인하기");
		System.out.println("\t\t\t\t\t\t\t\t\t2.비회원으로 조회하기");
		System.out.println("\t\t\t\t\t\t\t\t\t3.회원가입하기");
		System.out.println("\t\t\t\t\t\t\t\t\t4.아이디 비밀번호 찾기");
		System.out.println("\t\t\t\t\t\t\t===========================================");

		int sel = ScanUtil.nextInt("\t\t\t\t\t\t\t메뉴 번호를 입력하시오 ▶   ");

		switch (sel) {
		case 1:
			return View.USER_LOGIN;
		case 2:
			return View.FOREST_SEARCH;
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
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t\t1" + "★LoginPage★");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		System.out.println("\t\t\t\t\t\t\t\t***'GB' 입력 시 돌아가기***");
		String user_id = ScanUtil.nextLine("\t\t\t\t\t\t\t\t아이디 : ");

		if (user_id.equals("GB")) {
			return View.MAIN;
		} else {
			String user_pw = ScanUtil.nextLine("\t\t\t\t\t\t\t\t비밀번호 : ");
			List<Object> param = new ArrayList<Object>();
			param.add(user_id);
			param.add(user_pw);

			boolean flag = memberService.userLogin(param);
			if (!flag) {
				System.out.println();
				System.out.println("\t\t\t\t\t\t\t\tID 또는 PW가 일치하지 않습니다.");
				System.out.println("\t\t\t\t\t\t\t===========================================");
				return View.USER_LOGIN;
			}
			return View.USER_HOME;
		}
	}

	protected View userHome() {
		Object user_name = sessionStorage.get("mem_id");
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t       ★" + user_name + "님 환영합니다.★");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		System.out.println("\t\t\t\t\t\t\t\t1. 휴양림 검색하기");
		System.out.println("\t\t\t\t\t\t\t\t2. 마이페이지로 가기");
		System.out.println("\t\t\t\t\t\t\t\t3. 로그아웃하기");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		int sel = ScanUtil.nextInt("\t\t\t\t\t\t\t메뉴 번호를 입력하시오 ▶  ");

		if (sel == 1) {
			return View.FOREST_SEARCH;
		} else if (sel == 2) {
			return View.USER_MYPAGE;
		} else if (sel == 3) {
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t로그아웃 되었습니다.");
			System.out.println();
			sessionStorage.clear();
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
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t회원가입을 시작합니다.");
		System.out.println("\t\t\t\t\t\t\t=================================");
		String user_name = ScanUtil.nextLine("\t\t\t\t\t\t\t이름을 입력하세요 : ");
		// 아이디 입력 및 중복 검사
		do {
			user_id = ScanUtil.nextLine("\t\t\t\t\t\t\t아이디를 입력하세요: ");
			if (!isUsernameValid(user_id)) {
				System.out.println();
				System.out.println("\t\t\t\t\t\t아이디는 5자 이상 15자 미만의 영문과 숫자의 조합이어야 합니다.");
			}
		} while (!isUsernameValid(user_id));

		// 비밀번호 입력 및 검사
		do {
			user_pw = ScanUtil.nextLine("\t\t\t\t\t\t\t비밀번호를 입력하세요: ");
			if (!isPasswordValid(user_pw)) {
				System.out.println();
				System.out.println("\t\t\t\t\t\t비밀번호는 5자 이상 15자 미만의 영문, 숫자, 특수기호의 조합이어야 합니다.");
			}
		} while (!isPasswordValid(user_pw));

		// 이메일 입력 및 유효성 검사
		do {
			user_email = ScanUtil.nextLine("\t\t\t\t\t\t\t이메일을 입력하세요: ");
			if (!isEmailValid(user_email)) {
				System.out.println();
				System.out.println("\t\t\t\t\t\t\t유효하지 않은 이메일 형식입니다. 다시 입력해주세요.");
			}
		} while (!isEmailValid(user_email));

		// 전화번호 입력 및 형식 검사
		do {
			user_phone = ScanUtil.nextLine("\t\t\t\t\t\t\t전화번호를 입력하세요 (ex. 010-1234-5678): ");

			if (!isPhoneNumberValid(user_phone)) {
				System.out.println("\t\t\t\t\t\t\t유효하지 않은 전화번호 형식입니다.");
				// 전화번호 형식이 잘못되었을 경우, 원하는 방법으로 처리할 수 있습니다.
			}
		} while (!isPhoneNumberValid(user_phone));

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
			System.out.println("\t\t\t\t\t\t\t======================================================================");
			for (int i = start; i < end; i++) {
				if (end > index) {
					for (i = start; i < index; i++) {
						System.out.println("\t\t\t\t\t\t\t" + (i + 1) + "번 행 " + address_output.get(i));
					}
					break;
				}
				System.out.println("\t\t\t\t\t\t\t" + (i + 1) + "번 행 " + address_output.get(i));
			}

			System.out.println("\t\t\t\t\t\t\t======================================================================");
			System.out.println("\t\t\t\t\t\t\t" + "<이전페이지\t\t\t\t\t\t     다음페이지>");
			String user_addr_input = ScanUtil.nextLine("\t\t\t\t\t\t\t본인이 거주하고 있는 주소의 행 번을 입력하세요(주소 재검색은 0번) : ");

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
					System.out.println("\t\t\t\t\t\t\t\t\t\t★마지막페이지입니다★");
					sessionStorage.put("pageNo", pageNo);
					continue;
				}
				sessionStorage.put("pageNo", ++pageNo);
				continue;
			} else if (user_addr_input.charAt(0) == '0') {
				return View.USER_SIGNUP;
			} else {

				String addr = (String) address_output.get(Integer.parseInt(user_addr_input) - 1);
				String addr_detail = ScanUtil.nextLine("\t\t\t\t\t\t\t상세 주소 입력해주세요 : ");
				String addr_result = addr + " " + addr_detail;

				// 돌아가기 알고리즘 필요
				System.out.println("돌아가기");
				param.add(user_id);
				param.add(user_pw);
				param.add(user_name);
				param.add(addr_result);
				param.add(user_phone);
				param.add(user_email);

				System.out.println(param);

				memberService.userSignUp(param);
				System.out.println("\t\t\t\t\t\t\t    ★회원가입이 완료되었습니다★");
				System.out.println();
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
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t    ID/PW FIND");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		System.out.println("\t\t\t\t\t\t\t    ***'GB' 입력 시 돌아가기***");
		System.out.println("\t\t\t\t\t\t\t\t1.아이디찾기");
		System.out.println("\t\t\t\t\t\t\t\t2.비밀번호찾기");
		String sel = ScanUtil.nextLine("\t\t\t\t\t\t\t   메뉴 번호를 입력하시오 ▶  ");
		if (sel.charAt(0) - '0' == 1) {
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t      ★ID FIND★");
			System.out.println("\t\t\t\t\t\t\t===========================================");
			String user_name = ScanUtil.nextLine("\t\t\t\t\t\t\t\t성함을 입력하세요 : ");
			String user_phone = ScanUtil.nextLine("\t\t\t\t\t\t\t\t'-'기호를 포함해 전화번호를 입력하세요 \n\t\t\t\t\t\t\t\t: ");
			List<Object> param = new ArrayList<Object>();
			param.add(user_name);
			param.add(user_phone);

			boolean idCheck = memberService.idFind(param);
			if (idCheck == false) {
				System.out.println("\t\t\t\t\t\t\t\t회원정보와 일치하는 아이디가 없습니다.");
				return View.USER_FIND;
			}
			System.out.println("\t\t\t\t\t\t\t\t회원님의 ID는 " + sessionStorage.get("mem_id") + "입니다.");
			return View.MAIN;
		} else if (sel.charAt(0) - '0' == 2) {
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t      ★PW FIND★");
			System.out.println("\t\t\t\t\t\t\t===========================================");
			String user_id = ScanUtil.nextLine("\t\t\t\t\t\t\t\t아이디를 입력하세요 : ");
			String user_name = ScanUtil.nextLine("\t\t\t\t\t\t\t\t성함을 입력하세요 : ");
			List<Object> param = new ArrayList<Object>();
			param.add(user_id);
			param.add(user_name);

			List<Object> pw_input = new ArrayList<Object>();
			pw_input.add(user_id);
			boolean check_flag = memberService.pwFind(param);
			while (true) {
				if (check_flag == true) {
					String pw = ScanUtil.nextLine("\t\t\t\t\t\t\t\t바꿀 비밀번호를 입력하세요 : ");

					String pw_check = ScanUtil.nextLine("\t\t\t\t\t\t\t\t비밀번호 재확인 : ");
					if (pw.equals(pw_check)) {
						pw_input.add(pw);
						memberService.pwUpdate(pw_input);
						System.out.println("\t\t\t\t\t\t\t\t비밀번호가 바뀌었습니다.");
						return View.MAIN;
					} else {
						System.out.println("\t\t\t\t\t\t\t\t입력하신 비밀번호가 일치하지 않습니다.");
						continue;
					}
				}
				if (check_flag == false) {
					System.out.println("\t\t\t\t\t\t\t\t아이디와 성함이 다릅니다.");
					return View.USER_FIND;
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
		System.out.println("\t\t\t\t\t\t\t\t\t\t\t★My Page★");
		System.out.println("\t\t\t\t\t\t\t======================================================================");
		String pw = ScanUtil.nextLine("\t\t\t\t\t\t\t마이페이지에 들어가기 위해 비밀번호를 입력하세요 : ");
		List<Object> param = new ArrayList<Object>();
		param.add(pw);

		boolean flag = memberService.myPage(param);
		if (!flag) {
			System.out.println("\t\t\t\t\t\t\t비밀번호를 재입력하세요.");
			return View.USER_MYPAGE;
		} else {
			System.out.println("\t\t\t\t\t\t\t1. 사용자 정보 수정");
			System.out.println("\t\t\t\t\t\t\t2. 휴양림 / 숙소 리뷰 작성");
			System.out.println("\t\t\t\t\t\t\t3. 회원탈퇴");
			System.out.println("\t\t\t\t\t\t\t0. 돌아가기");
			System.out.println("\t\t\t\t\t\t\t======================================================================");
			int sel = ScanUtil.nextInt("\t\t\t\t\t\t\t메뉴 번호를 입력하시오 ▶  ");

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
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t         ★My Page Edit★");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		System.out.println("\t\t\t\t\t\t\t1. 비밀번호 수정");
		System.out.println("\t\t\t\t\t\t\t" + "2. 전화번호 수정");
		System.out.println("\t\t\t\t\t\t\t3. 주소 수정");
		System.out.println("\t\t\t\t\t\t\t0. 돌아가기");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		int sel = ScanUtil.nextInt("\t\t\t\t\t\t\t메뉴 번호를 입력하시오 ▶  ");
		if (sel == 1) {
			String user_pw;
			do {
				user_pw = ScanUtil.nextLine("\t\t\t\t\t\t\t변경할 비밀번호를 입력하세요: ");
				if (!isPasswordValid(user_pw)) {
					System.out.println();
					System.out.println("\t\t\t\t\t\t비밀번호는 5자 이상 15자 미만의 영문, 숫자, 특수기호의 조합이어야 합니다.");
				}
			} while (!isPasswordValid(user_pw));

			String pw_re = ScanUtil.nextLine("\t\t\t\t\t\t\t비밀번호를 한번 더 입력하세요 : ");
			if (user_pw.equals(pw_re)) {
				List<Object> pw_input = new ArrayList<Object>();
				pw_input.add(user_pw);
				pw_input.add(sessionStorage.get("mem_id"));
				memberService.pwUpdate(pw_input);
				System.out.println();
				System.out.println("\t\t\t\t\t\t\t비밀번호가 변경되었습니다.");
				return View.USER_MYPAGE;
			} else {
				System.out.println("\t\t\t\t\t\t\t비밀번호가 일치하지않습니다.");
				return View.USER_MYPAGE_EDIT;
			}
		} else if (sel == 2) {
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t    ★My Phone Edite★");
			String user_phone;
			System.out.println("\t\t\t\t\t\t\t===========================================");
			do {
				user_phone = ScanUtil.nextLine("\t\t\t\t\t\t\t전화번호를 입력하세요 (ex. 010-1234-5678): ");

				if (!isPhoneNumberValid(user_phone)) {
					System.out.println("\t\t\t\t\t\t\t유효하지 않은 전화번호 형식입니다.");
					// 전화번호 형식이 잘못되었을 경우, 원하는 방법으로 처리할 수 있습니다.
				}
			} while (!isPhoneNumberValid(user_phone));
			System.out.println("\t\t\t\t\t\t\t===========================================");
			List<Object> phone_input = new ArrayList<Object>();
			phone_input.add(user_phone);
			phone_input.add(sessionStorage.get("mem_id"));
			memberService.phoneUpdate(phone_input);
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t전화번호가 변경되었습니다.");
			System.out.println();
			return View.USER_MYPAGE;

		} else if (sel == 3) {
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t\t★My Address Edit★");
			System.out.println("\t\t\t\t\t\t\t======================================================================");
			String address = ScanUtil.nextLine("\t\t\t\t\t\t\t변경할 주소를 입력하세요 : ");
			egov.EgovController(address);
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
							System.out.println("\t\t\t\t\t\t\t" + (i + 1) + "번 행 " + address_output.get(i));
						}
						break;
					}
					System.out.println("\t\t\t\t\t\t\t" + (i + 1) + "번 행 " + address_output.get(i));
				}
				System.out.println("\t\t\t\t\t\t\t" + "<이전페이지\t\t\t\t\t\t     다음페이지>");
				String user_addr_input = ScanUtil
						.nextLine("\t\t\t\t\t\t\t본인이 거주하고 있는 주소의 행 번을 입력하세요 : \n" + "\t\t\t\t\t\t\t(주소 재검색은 0번)");

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
						System.out.println("\t\t\t\t\t\t\t마지막페이지입니다.");
						sessionStorage.put("pageNo", pageNo);
						continue;
					}
					sessionStorage.put("pageNo", ++pageNo);
					continue;
				} else if (user_addr_input.charAt(0) == '0') {
					return View.USER_SIGNUP;
				} else {

					String addr = (String) address_output.get(Integer.parseInt(user_addr_input) - 1);
					String addr_detail = ScanUtil.nextLine("\t\t\t\t\t\t\t상세 주소 입력해주세요 : ");
					String addr_result = addr + " " + addr_detail;

					List<Object> param = new ArrayList<>();
					param.add(sessionStorage.get("mem_id"));
					param.add(addr_result);
					memberService.addressUpdate(param);
					System.out.println("\t\t\t\t\t\t\t\t\t\t★주소가 변경되었습니다.★");
					return View.USER_HOME;
				}
			}
		} else if (sel == 4) {
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t    ★My E-Mail Edite★");
			System.out.println("\t\t\t\t\t\t\t===========================================");
			String mail = ScanUtil.nextLine("\t\t\t\t\t\t\t변경할 메일을 입력하세요 : ");
			List<Object> phone_input = new ArrayList<Object>();
			phone_input.add(mail);
			phone_input.add(sessionStorage.get("mem_id"));
			System.out.println("\t\t\t\t\t\t\t★메일이 변경되었습니다.★");
			return View.USER_MYPAGE;
		}

		return View.USER_HOME;
	}

	protected View userMyPageForestReview() {
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t    ★Review Comment★");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		String sel = ScanUtil.nextLine("\t\t\t\t\t\t\t어떤 휴양림의 리뷰를 남기시겠습니까?");
		// +어떤 휴양림인지에 대한 코딩
		int sel2 = ScanUtil.nextInt("\t\t\t\t\t\t\t평점을 입력해주세요(최대5점)");
		if (sel2 <= 5 || sel2 >= 0) {
			System.out.println(sel + "의 평점을" + sel2 + "로 남기셨습니다.");
		} else {
			System.out.println("\t\t\t\t\t\t\t잘못입력하셨습니다. 리뷰작성페이지로 돌아갑니다.");
			return View.USER_MYPAGE_FOREST_REVIEW;
		}
		String sel3 = ScanUtil.nextLine("\t\t\t\t\t\t\t" + sel + "숙소의 후기를 작성해주십이오.");
		String sel4 = ScanUtil.nextLine("\t\t\t\t\t\t\t정말 등록하시겠습니까?(Y/N)\n\t\t\t\t\t\t\t(잘못입력시 메인페이지로 돌아갑니다)");
		if (sel4 == "Y" || sel == "y") {
			System.out.println("\t\t\t\t\t\t\t후기등록이 다음과 같이 등록되었습니다.");
			System.out.println("\t\t\t\t\t\t\t" + sel + "의 평점 : " + sel2 + "\n\t\t\t\t\t\t\t" + sel + "의 후기 : " + sel3);
			System.out.println("\t\t\t\t\t\t\t소중한 리뷰 감사합니다.");
			return View.USER_HOME;
		} else if (sel4 == "N" || sel4 == "n") {
			System.out.println("\t\t\t\t\t\t\t리뷰 등록을 취소하였습니다.");
			return View.USER_MYPAGE_FOREST_REVIEW;
		} else {
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t리뷰 등록을 취소하였습니다.");
			return View.USER_HOME;
		}
	}

	protected View userQuit() {
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t    ★Delete Account★");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		String pw = ScanUtil.nextLine("\t\t\t\t\t\t\tPW를 입력해주세요");
		// +sql id pw 동일 시 다음 줄
		// +sql id pw 틀릴 시 다시입력하기 or id찾기 or pw찾기 or 유저_홈가기

		String sel = ScanUtil.nextLine("\t\t\t\t\t\t\t회원 탈퇴를 진행하시겠습니까?(Y/N)");
		if (sel.equalsIgnoreCase("Y")) {
			String sel2 = ScanUtil.nextLine("\t\t\t\t\t\t\t정말 회원 탈퇴를 진행하시겠습니까?(Y/N)");
			if (sel2.equalsIgnoreCase("Y")) {
				System.out.println("\t\t\t\t\t\t\t회원탈퇴가 진행이 완료되었습니다.");
				return View.USER_HOME;
			} else if (sel2.equalsIgnoreCase("N")) {
				System.out.println("\t\t\t\t\t\t\t회원탈퇴 진행을 취소하였습니다.");
				return View.USER_HOME;
			} else {
				System.out.println("\t\t\t\t\t\t\t잘못 입력하셨습니다.\n\t\t\t\t\t\t\t회원탈퇴 처음페이지로 돌아갑니다.");
				return View.USER_QUIT;
			}
		} else if (sel.equalsIgnoreCase("N")) {
			System.out.println("\t\t\t\t\t\t\t회원탈퇴 진행을 취소하였습니다.");
			return View.USER_HOME;
		} else {
			System.out.println("\t\t\t\t\t\t\t잘못 입력하셨습니다.");
			return View.USER_QUIT;
		}
	}

	/**
	 * @return 어드민 로그인 페이지 기능 구현 완료 24.4.15.홍정호 10:25
	 */
	protected View adminLogin() {
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t\t★Admin Login★");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		List<Object> login_input = new ArrayList<Object>();
		String id_input = ScanUtil.nextLine("\t\t\t\t\t\t\t관리자 아이디를 입력하세요 : ");
		String pw_input = ScanUtil.nextLine("\t\t\t\t\t\t\t관리자 비밀번호를 입력하세요 : ");
		login_input.add(id_input);
		login_input.add(pw_input);

		boolean flag = memberService.adminLogin(login_input);
		if (!flag) {
			System.out.println("\t\t\t\t\t\t\tID 또는 PW가 일치하지 않습니다.");
			return View.ADMIN_LOGIN;
		}

		return View.ADMIN_MAIN;
	}

	/**
	 * @return 어드민 메인페이지 기능 구현 완료 24.4.15. 홍정호 10:25
	 */
	protected View adminMain() {
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t\t★Admin Main★");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		System.out.println("\t\t\t\t\t\t\t\t" + sessionStorage.get("adm_id") + "님 환영합니다.");

		int roll = (int) sessionStorage.get("adm_roll"); // 로그인 후 roll 값을 여기다가 초기화
		if (roll == 1) {
			// 최고관리자일경우
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t★최고 관리자★");
			System.out.println("\t\t\t\t\t\t\t===========================================");
			System.out.println("\t\t\t\t\t\t\t\t1. 관리자 등록");
			System.out.println("\t\t\t\t\t\t\t\t2. 관리자 목록");
			System.out.println("\t\t\t\t\t\t\t\t3. 휴양림 목록");
			System.out.println("\t\t\t\t\t\t\t\t4. 휴양림 기본정보 추가 등록");
			System.out.println("\t\t\t\t\t\t\t\t5. 휴양림 객실정보 추가등록");
			System.out.println("\t\t\t\t\t\t\t\t6. 휴양림 수정");
			System.out.println("\t\t\t\t\t\t\t\t7. 휴양림 통계조회");
			System.out.println("\t\t\t\t\t\t\t===========================================");

			int sel = ScanUtil.nextInt("\t\t\t\t\t\t\t메뉴 번호를 입력하시오 ▶  ");
			if (sel == 1) {
				return View.ADMIN_REGISTER;
			} else if (sel == 2) {
				return View.ADMIN_LIST;
			} else if (sel == 3) {
				return View.ADMIN_FOREST_LIST;
			} else if (sel == 4) {
				return View.ADMIN_FOREST_REGISTER;
			} else if (sel == 5) {
				return View.ADMIN_FOREST_DETAIL_REGI;
			} else if (sel == 6) {
				return View.ADMIN_FOREST_EDIT;
			} else if (sel == 7) {
				return View.ADMIN_STATISTIC;
			}
		} else if (roll == 2) {
			// 도담당자일경우
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t★시도 담당자★");
			System.out.println("\t\t\t\t\t\t\t===========================================");
			System.out.println("\t\t\t\t\t\t\t\t1. 계정 정보 수정");
			System.out.println("\t\t\t\t\t\t\t\t2. 휴양림 목록");
			System.out.println("\t\t\t\t\t\t\t\t3. 휴양림 기본정보 추가");
			System.out.println("\t\t\t\t\t\t\t\t4. 휴양림 세부정보 추가");
			System.out.println("\t\t\t\t\t\t\t\t5. 휴양림 수정");
			System.out.println("\t\t\t\t\t\t\t\t6. 휴양림 통계조회");
			System.out.println("\t\t\t\t\t\t\t===========================================");
			int sel = ScanUtil.nextInt("\t\t\t\t\t\t\t메뉴 번호를 입력하시오 ▶  ");
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
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t★휴양림 담당자★");
			System.out.println("\t\t\t\t\t\t\t===========================================");
			System.out.println("\t\t\t\t\t\t\t\t1. 계정 정보 수정");
			System.out.println("\t\t\t\t\t\t\t\t2. 휴양림 목록");
			System.out.println("\t\t\t\t\t\t\t\t3. 휴양림 기본정보 추가");
			System.out.println("\t\t\t\t\t\t\t\t4. 휴양림 세부정보 추가");
			System.out.println("\t\t\t\t\t\t\t\t5. 휴양림 수정");
			System.out.println("\t\t\t\t\t\t\t\t6. 휴양림 통계조회");
			System.out.println("\t\t\t\t\t\t\t===========================================");
			int sel = ScanUtil.nextInt("\t\t\t\t\t\t\t메뉴 번호를 입력하시오 ▶  ");
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
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t★Admin Register Page★");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		List<Object> input = new ArrayList<Object>();
		System.out.println("\t\t\t\t\t\t\t1. 최고관리자      2. 도담당자  \n\t\t\t\t\t\t\t3. 휴양림담당자    0.돌아가기");
		int roll = ScanUtil.nextInt("\t\t\t\t\t\t\t생성하려는 관리자의 권한은 어떻게 되십니까?");
		if (roll == 1) {
			int code = 9999999;
			String id = ScanUtil.nextLine("\t\t\t\t\t\t\t등록할 어드민 아이디 입력 : ");
			String pw = ScanUtil.nextLine("\t\t\t\t\t\t\t등록할 어드민 비밀번호 입력 : ");
			String pw_re = ScanUtil.nextLine("\t\t\t\t\t\t\t비밀번호 확인 : ");
			if (pw.equals(pw_re)) {
				input.add(code);
				input.add(id);
				input.add(pw);
				input.add(roll);
				return View.ADMIN_MAIN;
			} else {
				System.out.println("\t\t\t\t\t\t\t입력하신 비밀번호가 일치하지 않습니다.");
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
				System.out.println();
				System.out.println("\t\t\t\t\t\t\t지역코드(시도) 불러오기");
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
						System.out.println("\t\t\t\t\t\t\t" + index++ + "번 행 " + codeVo);
					}
				}
				System.out.println("\t\t\t\t\t\t\t" + "<이전페이지\t\t\t다음페이지>");
				String num = ScanUtil.nextLine("\t\t\t\t\t\t\t행 번호를 입력하세요 : "); // num-1

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
						System.out.println("\t\t\t\t\t\t\t마지막페이지입니다.");
						sessionStorage.put("pageNo", pageNo);
						continue;
					}
					sessionStorage.put("pageNo", ++pageNo);
					count += 5;
					continue;
				} else {
					BigDecimal code = (BigDecimal) sidoCode.get(Integer.parseInt(num) - 1 - count).get("CODE");
					System.out.println("\t\t\t\t\t\t\t" + code);
					String id = ScanUtil.nextLine("\t\t\t\t\t\t\t등록할 어드민 아이디 입력 : ");
					String pw = ScanUtil.nextLine("\t\t\t\t\t\t\t등록할 어드민 비밀번호 입력 : ");
					String pw_re = ScanUtil.nextLine("\t\t\t\t\t\t\t비밀번호 확인 : ");
					if (pw.equals(pw_re)) {
						input.add(code);
						input.add(id);
						input.add(pw);
						input.add(roll);
						memberService.adminSignUp(input);
						System.out.println("\t\t\t\t\t\t\t등록이 완료 되었습니다.");
						return View.ADMIN_MAIN;
					} else {
						System.out.println("\t\t\t\t\t\t\t입력하신 비밀번호가 일치하지 않습니다.");
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
				System.out.println();
				System.out.println("\t\t\t\t\t\t\t\t★Sigun Code Import★");
				System.out.println("\t\t\t\t\t\t\t===========================================");
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
						System.out.println("\t\t\t\t\t\t\t" + index++ + "번 행 " + codeVo);
					}
				}

				System.out.println("\t\t\t\t\t\t\t" + "<이전페이지\t\t\t\t\t\t     다음페이지>");
				String num = ScanUtil.nextLine("\t\t\t\t\t\t\t행 번호를 입력하세요 : "); // num-1

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
						System.out.println("\t\t\t\t\t\t\t마지막페이지입니다.");
						sessionStorage.put("pageNo", pageNo);
						continue;
					}
					sessionStorage.put("pageNo", ++pageNo);
					count += 15;
					continue;
				} else {
					BigDecimal code = (BigDecimal) sigunCode.get(Integer.parseInt(num) - 1 - count).get("CODE");
					String id = ScanUtil.nextLine("\t\t\t\t\t\t\t등록할 어드민 아이디 입력 : ");
					String pw = ScanUtil.nextLine("\t\t\t\t\t\t\t등록할 어드민 비밀번호 입력 : ");
					String pw_re = ScanUtil.nextLine("\t\t\t\t\t\t\t비밀번호 확인 : ");
					if (pw.equals(pw_re)) {
						input.add(code);
						input.add(id);
						input.add(pw);
						input.add(roll);
						memberService.adminSignUp(input);
						System.out.println("\t\t\t\t\t\t\t등록되었습니다.");
						return View.ADMIN_MAIN;
					} else {
						System.out.println("\t\t\t\t\t\t\t입력하신 비밀번호가 일치하지 않습니다.");
						return View.ADMIN_REGISTER;
					}
				}
			}
		} else if (roll == 0) {
			System.out.println("\t\t\t\t\t\t\tAdminMain페이지로 돌아갑니다.");
			return View.ADMIN_MAIN;
		}
		return View.ADMIN_REGISTER;
	}

	/**
	 * @return 로직 구현 완료 -홍정호 24.4.15. 19:48
	 */
	protected View adminEdit() {
		if (sessionStorage.get("editFlag") == null) {
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t★Admin Account Edit★");
			System.out.println("\t\t\t\t\t\t\t===========================================");
			System.out.println("\t\t\t\t\t\t\t\t1. 비밀번호 수정");
			System.out.println("\t\t\t\t\t\t\t\t0. 돌아가기");
			System.out.println("\t\t\t\t\t\t\t===========================================");
			int sel = ScanUtil.nextInt("\t\t\t\t\t\t\t메뉴 번호를 입력하시오 ▶  ");
			if (sel == 1) {
				String pw = ScanUtil.nextLine("\t\t\t\t\t\t\t수정할 비밀번호를 입력하세요 : ");
				String pw_re = ScanUtil.nextLine("\t\t\t\t\t\t\t비밀번호 확인 : ");
				List<Object> pw_update = new ArrayList<Object>();
				if (pw.equals(pw_re)) {
					pw_update.add(pw);
					pw_update.add(sessionStorage.get("adm_id"));
					memberService.adminPWUpdate(pw_update);
					System.out.println("\t\t\t\t\t\t\t비밀번호가 변경되었습니다.");
					return View.ADMIN_MAIN;
				} else {
					System.out.println("\t\t\t\t\t\t\t입력하신 비밀번호가 일치하지 않습니다.");
					return View.ADMIN_EDIT;
				}
			} else if (sel == 0) {
				System.out.println("\t\t\t\t\t\t\t돌아가기");
				return View.ADMIN_MAIN;
			}
		} else {
			// 권한 수정 로직 추가중 4.15. 18:53
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t★Select Admin Account Edit★");
			System.out.println("\t\t\t\t\t\t\t===========================================");
			System.out.println("\t\t\t\t\t\t\t1. 비밀번호 수정");
			System.out.println("\t\t\t\t\t\t\t0. 돌아가기");
			System.out.println("\t\t\t\t\t\t\t===========================================");
			int sel = ScanUtil.nextInt("\t\t\t\t\t\t\t메뉴 번호를 입력하시오 ▶  ");
			if (sel == 1) {
				String pw = ScanUtil.nextLine("\t\t\t\t\t\t\t수정할 비밀번호를 입력하세요 : ");
				String pw_re = ScanUtil.nextLine("\t\t\t\t\t\t\t비밀번호 확인 : ");
				List<Object> pw_update = new ArrayList<Object>();
				if (pw.equals(pw_re)) {
					pw_update.add(pw);
					pw_update.add(sessionStorage.get("editFlag"));
					memberService.adminPWUpdate(pw_update);
					System.out.println("\t\t\t\t\t\t\t선택한 관리자의 비밀번호가 변경되었습니다.");
					return View.ADMIN_MAIN;
				} else {
					System.out.println("\t\t\t\t\t\t\t입력하신 비밀번호가 일치하지 않습니다.");
					return View.ADMIN_EDIT;
				}
			} else if (sel == 0) {
				System.out.println("\t\t\t\t\t\t\t돌아가기");
				return View.ADMIN_MAIN;
			}
		}
		return View.ADMIN_MAIN;
	}

	// 1번 비번 수정 -- 작성중, 4.15. 17:32, 2번 권한 수정
	protected View adminList() {
		int cut = 0;
		int start = 0;
		int end = 0;
		int index = 1;
		int count = 0;
		List<Map<String, Object>> adminList = new ArrayList<>();
		while (true) {
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t★Super Admin Account Edit★");
			System.out.println("\t\t\t\t\t\t\t===========================================");

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
					System.out.println("\t\t\t\t\t\t\t" + index++ + "번 행 " + adminVo);
				}
			}

			System.out.println("\t\t\t\t\t\t\t" + "<이전페이지\t\t\t다음페이지>");
			String num = ScanUtil.nextLine("\t\t\t\t\t\t\t행 번호를 입력하세요 : "); // num-1
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
					System.out.println("\t\t\t\t\t\t\t마지막페이지입니다.");
					sessionStorage.put("pageNo", pageNo);
					index = 1;
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

	/**
	 * @return 홍정호 완 21:47
	 */
	protected View adminForestList() {

		int cut = 0;
		int start = 0;
		int end = 0;
		int index = 1;
		List<Map<String, Object>> forestList = new ArrayList<>();
		int roll = (int) sessionStorage.get("adm_roll");
		String adm_no = (String) sessionStorage.get("adm_no");

		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t★Admin's Forest Lodge List★");
		System.out.println("\t\t\t\t\t\t\t===========================================");

		if (roll == 1) {
			int pageNo = 1;
			cut = 10;
			start = 1 + (pageNo - 1) * cut;
			end = pageNo * cut;

			List<Object> page = new ArrayList<Object>();
			page.add(start);
			page.add(end);

			while (true) {
				forestList = forestService.superForestList(page);

				if (forestList != null && !forestList.isEmpty()) {
					for (Map<String, Object> forestVo : forestList) {
						System.out.println("\t\t\t\t\t\t\t" + index++ + "번 행 " + forestVo);
					}
				}
				System.out.println("\t\t\t\t\t\t\t" + "<이전페이지\t\t\t다음페이지>");
				String num = ScanUtil.nextLine("\t\t\t\t\t\t\t0을 눌러 뒤로가기");

				if (num.charAt(0) == '<') {
					if (pageNo > 1) {
						sessionStorage.put("pageNo", --pageNo);
						start -= 10;
						end -= 10;
						index -= 10;
						continue;
					} else {
						continue;
					}

				} else if (num.charAt(0) == '>') {
					if (end > index) {
						System.out.println("\t\t\t\t\t\t\t마지막페이지입니다.");
						sessionStorage.put("pageNo", pageNo);
						continue;
					}
					sessionStorage.put("pageNo", ++pageNo);
					continue;
				}else if (num.charAt(0) == '0') {
					return View.ADMIN_MAIN;
				}
			}

		} else if (roll == 2) {
			// 도담당자 << 도 밑에 있는거 전부다 code 제일 앞 2자리가 시도 코드
			int pageNo = 1;
			cut = 10;
			start = 1 + (pageNo - 1) * cut;
			end = pageNo * cut;

			List<Object> page = new ArrayList<Object>();
			page.add(start);
			page.add(end);
			int code = Integer.parseInt(sessionStorage.get("adm_code").toString().substring(0, 2)) ;
			page.add(code);
			page.add(code);

			while (true) {
				forestList = forestService.doForestList(page);
				if (forestList != null && !forestList.isEmpty()) {
					for (Map<String, Object> forestVo : forestList) {
						System.out.println("\t\t\t\t\t\t\t" + index++ + "번 행 " + forestVo);
					}
				}
				System.out.println("\t\t\t\t\t\t\t" + "<이전페이지\t\t\t다음페이지>");
				String num = ScanUtil.nextLine("\t\t\t\t\t\t\t0을 눌러 뒤로가기 : ");
				
				if (num.charAt(0) == '<') {
					if (pageNo > 1) {
						sessionStorage.put("pageNo", --pageNo);
						start -= 10;
						end -= 10;
						index -= 10;
						continue;
					} else {
						continue;
					}

				} else if (num.charAt(0) == '>') {
					if (end > index) {
						System.out.println("\t\t\t\t\t\t\t마지막페이지입니다.");
						sessionStorage.put("pageNo", pageNo);
						continue;
					}
					sessionStorage.put("pageNo", ++pageNo);
					continue;
				}else if (num.charAt(0) == '0') {
					return View.ADMIN_MAIN;
				}
			}

	}else if(roll==3)
	{
		// 휴양림담당자 <<휴양림담당장에 배정된 휴양림만
		int pageNo = 1;
		cut = 10;
		start = 1 + (pageNo - 1) * cut;
		end = pageNo * cut;

		List<Object> param = new ArrayList<>();
		param.add(start);
		param.add(end);
		param.add(adm_no);

		while (true) {
			forestList = forestService.gunForestList(param);
			if (forestList != null && !forestList.isEmpty()) {
				for (Map<String, Object> forestVo : forestList) {
					System.out.println("\t\t\t\t\t\t\t" + index++ + "번 행 " + forestVo);
				}
			}
			System.out.println("\t\t\t\t\t\t\t" + "<이전페이지\t\t\t다음페이지>");
			String num = ScanUtil.nextLine("\t\t\t\t\t\t\t0을 눌러 뒤로가기 : ");
			
			if (num.charAt(0) == '<') {
				if (pageNo > 1) {
					sessionStorage.put("pageNo", --pageNo);
					start -= 10;
					end -= 10;
					index -= 10;
					continue;
				} else {
					continue;
				}

			} else if (num.charAt(0) == '>') {
				if (end > index) {
					System.out.println("\t\t\t\t\t\t\t마지막페이지입니다.");
					sessionStorage.put("pageNo", pageNo);
					continue;
				}
				sessionStorage.put("pageNo", ++pageNo);
				continue;
			} else if(num.charAt(0) == '0') {
				return View.ADMIN_MAIN;
			}
		}
	}

	return View.ADMIN_MAIN;
	}

	// sql 수정필요
	protected View adminForestRegister() {

		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t★Admin's Forest Lodge Register★");
		System.out.println("\t\t\t\t\t\t\t===========================================");

//		List<AdminVo> RoomList = forestService.roomInsert();
		String forest_name = ScanUtil.nextLine("\t\t\t\t\t\t\t1. 휴양림명 등록 :");
//		String forest_addr_base = ("2. 기본주소(시도, 시군)는 자동 입력");
		// sql을 통해 관리자 계정의 시도 시군 스트링 값 가지고 오기

		String forest_addr_detail = ScanUtil.nextLine("\t\t\t\t\t\t\t3. 주소 검색 후 상세주소 입력 :");
		String room_name = ScanUtil.nextLine("\t\t\t\t\t\t\t4. 객실 명을 입력하세요. 추가 객실은 상세 정보에서 등록이 가능합니다. :");
		int room_qty = ScanUtil.nextInt("\t\t\t\t\t\t\t5. 최대 이용인원 등록 : ");

		String room_notice = ScanUtil.nextLine("\t\t\t\t\t\t\t6. 공지사항 : ");

		List<Object> param = new ArrayList<>();
		param.add(sessionStorage.get("adm_no"));
		param.add(forest_name);
		param.add(forest_addr_detail);
		param.add(room_name);
		param.add(room_qty);
		param.add(room_notice);

		forestService.forestInsert(param);
		return View.ADMIN_MAIN;
	}

	// 로직 추가 필요
	protected View adminForestEdit() {
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t★Admin's Forest Lodge Edit★");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		int cut = 0;
		int start = 0;
		int end = 0;
		int index = 1;
		int count = 0;
		List<Map<String, Object>> forestList = new ArrayList<>();
		List<Object> param = new ArrayList<>();
		while (true) {
			System.out.println("\t\t\t\t\t\t\t휴양림 목록 불러오기");
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
					System.out.println("\t\t\t\t\t\t\t" + index++ + "번 행 " + forestVo);
				}
			}

			System.out.println("\t\t\t\t\t\t\t" + "<이전페이지\t\t\t\t\t\t     다음페이지>");
			String num = ScanUtil.nextLine("\t\t\t\t\t\t\t상세 정보를 보실 forest의 행 번호를 입력하세요 : "); // num-1

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
					System.out.println("\t\t\t\t\t\t\t마지막페이지입니다.");
					sessionStorage.put("pageNo", pageNo);
					continue;
				}
				sessionStorage.put("pageNo", ++pageNo);
				count += 10;
				continue;
			} else {
				/// 로직 추가 필요 4.16. 16:21 김동오 홍정호

				System.out.println();
				System.out.println("\t\t\t\t\t\t\t\t★Choice Forest Lodge Info Detail★");
				System.out.println("\t\t\t\t\t\t\t===========================================");

				System.out.println();
				System.out.println("\t\t\t\t\t\t\t1.휴양림이름  2.휴양림 소개  3.휴양림 공지  4.휴양림 주소");
				System.out.println("\t\t\t\t\t\t\t===========================================");
				int sel = ScanUtil.nextInt("\t\t\t\t\t\t\t수정할 메뉴를 선택해주세요  ▶  ");
				if (sel == 1) {
					System.out.println("\t\t\t\t\t\t\t정보 수정 시 사용자가 이를 모를 수도 있습니다.");
					String Forest_Name = ScanUtil.nextLine("\t\t\t\t\t\t\t수정 할 휴양림 이름을 입력해주세요.");
					System.out.println();
					System.out.println("\t\t\t\t\t\t\t휴양림 이름이 수정 되었습니다.");

				} else if (sel == 2) {
					System.out.println("\t\t\t\t\t\t\t정보 수정 시 사용자가 이를 모를 수도 있습니다.");
					String Forest_Intro = ScanUtil.nextLine("\t\t\t\t\t\t\t수정 할 휴양림 소개를 입력해주세요.");
					System.out.println();
					System.out.println("\t\t\t\t\t\t\t휴양림 소개가 수정 되었습니다.");

				} else if (sel == 3) {
					System.out.println("\t\t\t\t\t\t\t정보 수정 시 사용자가 이를 모를 수도 있습니다.");
					String Forest_Notice = ScanUtil.nextLine("\t\t\t\t\t\t\t수정 할 휴양림 공지를 입력해주세요.");
					System.out.println();
					System.out.println("\t\t\t\t\t\t\t휴양림 공지가 수정 되었습니다.");

				} else if (sel == 4) {
					System.out.println("\t\t\t\t\t\t\t정보 수정 시 사용자가 이를 모를 수도 있습니다.");
					String Forest_Addr = ScanUtil.nextLine("\t\t\t\t\t\t\t수정 할 휴양림 주소를 입력해주세요.");
					System.out.println();
					System.out.println("\t\t\t\t\t\t\t휴양림 주소가 수정 되었습니다.");

				} else {
					System.out.println("\t\t\t\t\t\t\t잘못입력하셨습니다 휴양림 정보 수정페이지로 돌아갑니다.");
					return View.ADMIN_FOREST_EDIT;
				}

				return View.ADMIN_MAIN;
			}
		}

	}

	// 로직 수정 필요
	protected View adminForestRoomInsert() {
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t★Forest Lodge Add Room Register★");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		System.out.println("\t\t\t\t\t\t\t객실 추가등록(객실명, 수용인원)");// 공지사항은 객실에 종속 또는 휴양림 공지사항

		String room_name = ScanUtil.nextLine("\t\t\t\t\t\t\t객실명을 입력하세요 : ");
		int room_qty = ScanUtil.nextInt("\t\t\t\t\t\t\t수용인원을 입력하세요 : ");

		List<Object> param = new ArrayList();
		param.add(room_name);
		param.add(room_qty);

		forestService.forestRoomInsert(param);
		return View.ADMIN_MAIN;
	}

	// 로직 추가 필요
	protected View adminStatistic() {
		int code = (int) sessionStorage.get("adm_code");
		int roll = (int) sessionStorage.get("adm_roll");
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t★Statistics by Recreation Forest★");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		if (roll == 1) {
			List<Object> param = new ArrayList<>();
			param.add(code);
//			param.add()
			System.out.println("super_adm님 어서오세요.\n전체 통계 List입니다.");
			List<Map<String, Object>> map = new ArrayList<>();
			map = forestService.super_adm_statistic(param);

			for (Map<String, Object> li : map) {
				System.out.println(li);
			}

		} else if (roll == 2) {
			System.out.println("sido_adm님 어서오세요.\n담당 시도 휴양림 통계 List입니다.");

		} else if (roll == 3) {
			System.out.println("휴양림 담당자님 어서오세요.\n담당 휴양림 통계 List입니다.");

		}

		return View.ADMIN_MAIN;

	}

	/**
	 * @return 완료
	 */
	protected View forestSearch() {
		// 사용자 휴양림 검색 페이지
		List<Object> user_input = new ArrayList<>();
		List<Map<String, Object>> forestList = new ArrayList<>();
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t★Forest Lodge Searching Page★");
		System.out.println("\t\t\t\t\t\t\t================================================");
		System.out.println("\t\t\t\t\t\t\t휴양림 검색 페이지, 비회원도 가능 단 예약시에는 회원만 가능");
		System.out.println("\t\t\t\t\t\t\t검색 조건 : 1.도 입력  또는 시(군)입력  3.인원입력 "); // 2. 날짜선택
		String input = ScanUtil.nextLine("\t\t\t\t\t\t\t1. 시도 또는 시군 입력 ex)강원, 강릉, 대전: ");
		user_input.add(input);
		forestList = forestService.userForestSearch(user_input);
		int index = 1;
		for (Map<String, Object> forestVo : forestList) {
			System.out.println(index + "번 행, 휴양림 이름 : " + forestVo.get("FOREST_NAME") + ", 휴양림 소개 : "
					+ forestVo.get("FOREST_INTRO") + ", 휴양림 주소 : " + forestVo.get("FOREST_ADDR"));
			index++;
		}

		int num = ScanUtil.nextInt("\t\t\t\t\t\t\t상세 정보를 보고 싶은 행 번호를 누르세요 : ");
		sessionStorage.put("detail", forestList.get(num - 1).get("FOREST_NO"));

		return View.FOREST_DETAIL;
	}

	protected View forestDetail() {
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t★Forest Detail★");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		System.out.println("forestList에서 사용자가 선택한 휴양림의 상세정보 출력");
		String detail = (String) sessionStorage.get("detail"); // DETAIL IS FOREST_NO
		List<Object> param = new ArrayList<>();
		param.add(detail);
		Map<String, Object> result = forestService.forestDetail(param);
		System.out.println(result);

		System.out.println("\t\t\t\t\t\t\t 1. 리뷰보기");
		System.out.println("\t\t\t\t\t\t\t 2. 예약하기");
		System.out.println("\t\t\t\t\t\t\t 3. 돌아가기");

		int sel = ScanUtil.nextInt("\t\t\t\t\t\t\t 메뉴 번호를 입력하세요 : ");
		int index = 1;
		if (sel == 1) {
			List<Map<String, Object>> reviewResult = forestService.reviewCheck(param);
			for (Map<String, Object> review : reviewResult) {
				System.out.println(
						index + "번 행, 휴양림명 : " + review.get("FOREST_NAME") + "숙소 명 : " + review.get("ROOM_NAME")
								+ ", 리뷰 내용 : " + review.get("REVIEW_BODY") + ", 평점 : " + review.get("REVIEW_SCORE"));
				index++;
			}
			return View.FOREST_DETAIL;
		} else if (sel == 2) {
			return View.FOREST_BOOKING;
		} else if (sel == 3) {
			return View.ADMIN_LIST;
		}

		return View.ADMIN_LIST;
	}

	/**
	 * @return 예약 구현 완료 - 홍정호 4.17. 12:27
	 * @throws ParseException
	 */
	protected View forestBooking() throws ParseException {
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t★Booking Page★");
		System.out.println("\t\t\t\t\t\t\t===========================================");
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		List<Map<String, Object>> list = new ArrayList<>();
		List<Object> param = new ArrayList<>();
		List<Object> param2 = new ArrayList<>();
		List<Object> param3 = new ArrayList<>();
		List<Object> param4 = new ArrayList<>();
		// 방목록 출력
		String detail = (String) sessionStorage.get("detail");
		param4.add(detail);// DETAIL IS FOREST_NO
		int index = 1;

		List<Map<String, Object>> roomList = forestService.roomList(param4);
		for (Map<String, Object> li : roomList) {
			System.out.println(index + "번행  숙소 명 : " + li.get("ROOM_NAME") + " 수용인원 : " + li.get("ROOM_QTY"));
			index++;
		}

		int room_input = ScanUtil.nextInt("\t\t\t\t\t\t\t원하시는 숙소의 행번호를 입력하세요 : ");
		String room_no = (String) roomList.get(room_input - 1).get("ROOM_NO");

		param.add(room_no);
		if (sessionStorage.get("mem_id") == null) {
			System.out.println("\t\t\t\t\t\t\t예약기능은 회원만 가능합니다.");
			sessionStorage.clear();
			return View.MAIN;
		}
		while (true) {
			param2.clear();
			list = memberService.bookflagList(param);

			printCalendar(list, year, month);
			System.out.println("\t\t\t\t\t\t\t" + "<이전달\t\t\t\t다음달>");
			String input = ScanUtil.nextLine("\t\t\t\t\t\t\t예약을 원하는 기간의 시작일을 입력하세요 ex) 2024-05-01 : ");
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(input);
			Timestamp timestamp = new Timestamp(date.getTime());
			if (input.charAt(0) == '<') {
				if (month == 4) {
					System.out.println("\t\t\t\t\t\t\t2024년 4월 이전으로는 갈 수 없습니다.");
					continue;
				}
				month--;
			} else if (input.charAt(0) == '>') {
				if (month == 6) {
					System.out.println("\t\t\t\t\t\t\t2024년 6월 이후로는 갈 수 없습니다.");
					continue;
				}
				month++;
			} else {
				param2.add(timestamp);
				boolean bookCheck = memberService.bookCheck(param2);
				if (bookCheck == false) {
					String input2 = ScanUtil.nextLine("\t\t\t\t\t\t\t예약을 원하는 기간의 종료일을 입력하세요 ex) 2024-05-05 : ");
					param3.add(input);
					param3.add(input2);
					forestService.forest_Book(param3);

				} else {
					System.out.println("\t\t\t\t\t\t\t해당 시작일은 이미 예약건이 있습니다.");
					continue;
				}

				System.out.println("\t\t\t\t\t\t\t입력하신 일자로 예약이 완료되었습니다..");

				return View.USER_HOME;
			}
		}

	}

	static String startDateStr;
	static String endDateStr;
	static int startYear;
	static int startMonth;
	static int startDay;
	static int endYear;
	static int endMonth;
	static int endDay;

	private static void printCalendar(List<Map<String, Object>> list, int year, int month) {
		// 해당 월의 첫 날과 마지막 날 구하기
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);
		int firstDay = cal.get(Calendar.DAY_OF_WEEK); // 해당 월의 첫 번째 날의 요일
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 해당 월의 마지막 날짜

		// 캘린더 출력
		System.out.println("\t\t\t\t\t\t\t" + year + "년 " + month + "월\t\t\t\t\t\t\t\n");
		System.out.println("일\t월\t화\t수\t목\t금\t토");

		// 첫 번째 주에는 첫 번째 날짜 앞에 빈 칸 출력
		for (int i = 1; i < firstDay; i++) {
			System.out.print("\t");
		}

		// 예약된 날짜에 X 표시
		for (int day = 1; day <= lastDay; day++) {
			boolean isReserved = false;
			for (Map<String, Object> reservation : list) {
				startDateStr = (String) reservation.get("BOOK_D_START");
				endDateStr = (String) reservation.get("BOOK_D_END");
				startYear = Integer.parseInt(startDateStr.substring(0, 4));
				startMonth = Integer.parseInt(startDateStr.substring(5, 7));
				startDay = Integer.parseInt(startDateStr.substring(8));
				endYear = Integer.parseInt(endDateStr.substring(0, 4));
				endMonth = Integer.parseInt(endDateStr.substring(5, 7));
				endDay = Integer.parseInt(endDateStr.substring(8));
				if (year == startYear && month == startMonth && year == endYear && month == endMonth && day >= startDay
						&& day <= endDay) {
					isReserved = true;
					break;
				}
			}
			if (isReserved) {
				System.out.print("X\t");
			} else {
				System.out.printf("%02d\t", day);
			}
			// 토요일마다 줄바꿈
			if ((firstDay + day - 1) % 7 == 0) {
				System.out.println();
			}
		}
		System.out.println(); // 마지막 줄바꿈
	}

}
