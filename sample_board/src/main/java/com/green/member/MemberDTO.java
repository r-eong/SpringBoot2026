package com.green.member;

import org.springframework.stereotype.Repository;

@Repository
public class MemberDTO {
	private String id;  // 아이디
	private String pw;  // 비밀번호
	private String mail;  // 메일
	private String phone;  // 휴대폰번호
	private String reg_date;  // 가입날짜
	private String mod_date;  // 사용자 정보 수정일
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getMod_date() {
		return mod_date;
	}
	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}
}
