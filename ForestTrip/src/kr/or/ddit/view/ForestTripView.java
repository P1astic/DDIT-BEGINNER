package kr.or.ddit.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
		case 5:
			return View.ADMIN_LOGIN;
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

	protected View signUp() {

		// 값 입력 체크 알고리즘 필요
		Scanner scanner = new Scanner(System.in);
		List<Object> param = new ArrayList<Object>();
		System.out.println("회원가입을 시작합니다.");
		
        System.out.println("이름을 입력하세요 : ");
        String user_name = scanner.nextLine();
		System.out.println("ID를 입력하세요 : ");
        // 아이디 입력 및 중복 검사
        String user_id;
        do {
            System.out.print("아이디를 입력하세요: ");
            user_id = scanner.nextLine();
            if (!isUsernameValid(user_id)) {
                System.out.println("아이디는 5자 이상 15자 미만의 영문과 숫자의 조합이어야 합니다.");
            }
        } while (!isUsernameValid(user_id) || isUsernameDuplicate(user_id));
		
		System.out.println("PW를 입력하세요.");
		String user_pw;
	     // 비밀번호 입력 및 검사
        do {
            System.out.print("비밀번호를 입력하세요: ");
            user_pw = scanner.nextLine();
            if (!isPasswordValid(user_pw)) {
                System.out.println("비밀번호는 5자 이상 15자 미만의 영문, 숫자, 특수기호의 조합이어야 합니다.");
            }
        } while (!isPasswordValid(user_pw));
        
        // 이메일 입력 및 유효성 검사
        String user_email;
        do {
            System.out.print("이메일을 입력하세요: ");
            user_email = scanner.nextLine();
            if (!isEmailValid(user_email)) {
                System.out.println("유효하지 않은 이메일 형식입니다. 다시 입력해주세요.");
            }
        } while (!isEmailValid(user_email));
        
        // 전화번호 입력 및 형식 검사
        System.out.print("전화번호를 입력하세요 (ex. 010-1234-5678): ");
        String user_phone = scanner.nextLine();
        if (!isPhoneNumberValid(user_phone)) {
            System.out.println("유효하지 않은 전화번호 형식입니다.");
            // 전화번호 형식이 잘못되었을 경우, 원하는 방법으로 처리할 수 있습니다.
        }
        
        param.add(user_id);
        param.add(user_pw);
        param.add(user_name);
        param.add(user_email);
        param.add(user_phone);
        
        
		// 주소 검색 알고리즘 필요
		System.out.println("---------------주소 검색 : ");

		// 돌아가기 알고리즘 필요
		System.out.println("돌아가기");

		//회원가입 주소 검색 및 mem_no 시퀀스 구현 필요 - 홍정호 24.4.13.17:26
		
		memberService.userSignUp(param);
		return null;
	}
    // 아이디 유효성 검사 메서드
    public static boolean isUsernameValid(String user_name) {
        return user_name.matches("^[a-zA-Z](?=.*[a-zA-Z])(?=.*\\\\d)[a-zA-Z\\\\d]{5,15}$");
    }
    // 아이디 중복 검사 메서드 (가정)
    public static boolean isUsernameDuplicate(String user_name) {
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
	protected View userMyPage() {
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

	protected View userMyPageEdit() {
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
			return View.USER_MYPAGE;
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
			System.out.println("3. 관리자 권한 부여/수정");
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
				return View.ADMIN_REGISTER_ROLL;
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

	protected View adminRegister() {
		System.out.println("어드민 등록 페이지");
		List<Object> input = new ArrayList<Object>();
		System.out.println("생성하려는 관리자의 권한은 어떻게 되십니까?");
		System.out.println("1. 최고관리자, 2. 도담당자, 3. 휴양림담당자");
		int roll = ScanUtil.nextInt("생성하려는 관리자의 권한은 어떻게 되십니까?");

		if (roll == 1) {

			String id = ScanUtil.nextLine("등록할 어드민 아이디 입력 : ");
			String pw = ScanUtil.nextLine("등록할 어드민 비밀번호 입력 : ");
			String pw_re = ScanUtil.nextLine("비밀번호 확인 : ");
			if (pw.equals(pw_re)) {
				input.add(id);
				input.add(pw);
			} else {
				System.out.println("입력하신 비밀번호가 일치하지 않습니다.");
				return View.ADMIN_REGISTER;
			}
		} else if (roll == 2) {
			String id = ScanUtil.nextLine("등록할 어드민 아이디 입력 : ");
			String pw = ScanUtil.nextLine("등록할 어드민 비밀번호 입력 : ");
			String pw_re = ScanUtil.nextLine("비밀번호 확인 : ");
			if (pw.equals(pw_re)) {
				input.add(id);
				input.add(pw);
			} else {
				System.out.println("입력하신 비밀번호가 일치하지 않습니다.");
				return View.ADMIN_REGISTER;
			}
		} else if (roll == 3) {
			String id = ScanUtil.nextLine("등록할 어드민 아이디 입력 : ");
			String pw = ScanUtil.nextLine("등록할 어드민 비밀번호 입력 : ");
			String pw_re = ScanUtil.nextLine("비밀번호 확인 : ");
			if (pw.equals(pw_re)) {
				input.add(id);
				input.add(pw);
			} else {
				System.out.println("입력하신 비밀번호가 일치하지 않습니다.");
				return View.ADMIN_REGISTER;
			}
		}
		return null;
	}

	protected View adminEdit() {

		int roll = 0; // 권한 가져와야함

		if (roll != 0) {
			System.out.println("어드민 정보 수정 페이지");
			System.out.println("0을 입력해 돌아가기");
			System.out.println("1. 비밀번호 수정");
			int sel = ScanUtil.nextInt("메뉴 번호를 입력하세요 : ");
			if (sel == 1) {
				String pw = ScanUtil.nextLine("수정할 비밀번호를 입력하세요.");
				String pw_re = ScanUtil.nextLine("비밀번호 확인 : ");
			} else if (sel == 0) {
				System.out.println("돌아가기");

			}
		} else {
			int sel = ScanUtil.nextInt("메뉴 번호를 입력하세요 : ");
			if (sel == 1) {
				String pw = ScanUtil.nextLine("수정할 비밀번호를 입력하세요.");
				String pw_re = ScanUtil.nextLine("비밀번호 확인 : ");
			} else if (sel == 0) {
				System.out.println("돌아가기");
			} else if (sel == 2) {
				return View.ADMIN_REGISTER_ROLL;
			}
		}
		return null;
	}

	protected View adminList() {
		System.out.println("최고관리자의 관리자 정보 수정을 위한 목록 페이지");

		System.out.println("모든 어드민 목록 출력(출력시에는 모든 정보가 포함되어 나오게");
		// sql selectList

		System.out.println("목록 번호를 눌러 수정 페이지로 넘어가기");
		// sql selectone then adminEdit()

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

		int roll = 0;
		// sql로 로그인 한 계정의 roll 가져오기

		System.out.println("어드민의 휴양림 목록 출력 페이지");
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
		String forest_name = ScanUtil.nextLine("1. 휴양림명 등록");
//		String forest_addr_base = ("2. 기본주소(시도, 시군)는 자동 입력");
		// sql을 통해 관리자 계정의 시도 시군 스트링 값 가지고 오기

		String forest_addr_detail = ScanUtil.nextLine("3. 주소 검색 후 상세주소 입력");
		String room_name = ScanUtil.nextLine("4. 객실 명을 입력하세요. 추가 객실은 상세 정보에서 등록이 가능합니다.");
		int room_qty = ScanUtil.nextInt("5. 최대 이용인원 등록 : ");

		return View.ADMIN_MAIN;
	}

	protected View adminForestEdit() {
		System.out.println("어드민의 휴양림 정보 수정");

		System.out.println("목록에서 선택 후 상세 정보로 이동");
		// sql selectList, 상세정보는 객실명, 수용인원, 공지사항
		System.out.println("상세 정보에는 최초로 등록한 모든 정보가 출력되고 사용자는" + "컬럼을 선택해서 그 정보를 수정");
		int num = ScanUtil.nextInt("컬럼 번호를 입력하세요.");
		// pagination 구현 필요

		System.out.println("수정 시 경고문");
		System.out.println("예시) 정보를 바꿀 시 사용자가 이를 모를 수도 있다~~ 이런식으로");

		System.out.println("돌아가기");

		return View.ADMIN_MAIN;

	}

	protected View adminForestDetailRegi() {
		System.out.println("휴양림 상세정보 등록(휴양림 목록 페이지에서 접근)");

		System.out.println("객실 추가등록(객실명, 수용인원), 공지사항 작성");// 공지사항은 객실에 종속 또는 휴양림 공지사항

		String room_name = ScanUtil.nextLine("객실명을 입력하세요 : ");
		int room_qty = ScanUtil.nextInt("수용인원을 입력하세요 : ");
		String room_notice = ScanUtil.nextLine("객실 공지사항을 작성하세요 : ");

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
		System.out.println("검색 조건 : 1.도 입력   2.시(군)입력  3.날짜선택  4.인원입력 ");
		// 도 목록 보여주기 json 또는 생성자
		// 시군 목록 보여주기 json 또는 생성자
		// 날짜는 현재날짜 기준으로
		int qty = ScanUtil.nextInt("인원을 입력해주세요 : ");

		// 날짜 선택 시 달력만들기
		System.out.println("*******프로젝트 진행 속도에 따라 사용자는 1박만 하게 해야 할 수 있음*******");

		return null;
	}

	protected View forestList() {
		// 사용자 휴양림 목록 페이지
		System.out.println("forestSearch 조건에 부합하는 휴양림 목록 출력");
		System.out.println("사용자는 목록에서 번호 입력해서 상세 정보 조회 페이지로 이동");
		int row = ScanUtil.nextInt("목록 번호를 입력하세요 : ");
		// pagination 필요

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
