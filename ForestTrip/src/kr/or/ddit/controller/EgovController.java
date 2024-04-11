package kr.or.ddit.controller;

import java.util.Scanner;

public class EgovController {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		System.out.println("주소를 입력하시오 : ");
		String s1 = sc.next();
		
		System.out.println(s1);
		
	}
	
	public boolean checkSearchedWord(String input) {
		if(input.length() > 0) {
			//Ư������ ����
			if(input.contains("[") || input.contains("%") || input.contains("=") ||
					input.contains(">") || input.contains("<")) {
				System.out.println("Ư�����ڸ� �Է��� �� �����ϴ�.");
				return false;
			}
		}
		
		//sql �����
		String[] arr = new String[] {"OR", "SELECT", "INSERT", "DELETE", "UPDATE", "CREATE", "DROP", "EXEC",
        		 "UNION",  "FETCH", "DECLARE", "TRUNCATE" };
		
		for(int i = 0; i<arr.length; i++) {
			
		}
		
		
		return true;
	}
	
	public boolean getAddrLoc(String input) {
		
		return false;
		
	}
	
}
