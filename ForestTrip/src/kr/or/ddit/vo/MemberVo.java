package kr.or.ddit.vo;

import lombok.Data;

@Data
public class MemberVo {
	private String mem_no;
	private String mem_id;
	private String mem_pw;
	private String mem_name;
	private String mem_addr;
	private String quit_flag;
}
