package kr.or.ddit.util;

import java.util.Scanner;

public class ScanUtil   {
	// ��ĳ�ʸ� �ս��� ����� �� �ִ� static �޼��带 ����������
	static Scanner sc = new Scanner(System.in);
	
	public static String nextLine(String print) {
		System.out.print(print);
		return nextLine();
	}
	
	private static String nextLine() {
		return sc.nextLine();
	}
	
	public static int nextInt(String print) {
		System.out.print(print);
		return nextInt();
	}
	
	public static int menu() {
		return nextInt("�޴� : ");
	}
	
	private static int nextInt() {
		while(true) {
			try {
				int result = Integer.parseInt(sc.nextLine());
				return result;
			}catch (NumberFormatException e) {
				System.out.println("�߸� �Է�!!");
			}
		}
	}
}
