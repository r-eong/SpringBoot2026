package com.green;

//DTO(Data Transfer Odject) 는 데이터 전송객체
//DTO 라는 가방에 입력한 자료를 담아서 이동시킨다
public class MemberDTO {
//	멤버변수는 접근제어자 private 이용
//	단, privata 는 자기자신 클래스에서만 접근 가능하므로 getter/setter 할 것
	private String id;
	private String pw;
	private String email;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
