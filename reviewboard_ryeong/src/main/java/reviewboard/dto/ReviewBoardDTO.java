package reviewboard.dto;

import java.util.Date;

public class ReviewBoardDTO {
	private int num;  // 글번호
	private String writer;  // 작성자
	private String email;  // 이메일
	private String subject;  // 제목
	private String password;  // 비밀번호
	private Date reg_date;  // 작성일
	private int readcount;  // 조회수
	private int stars;  // 별점
	private String content;  // 글 내용
	private String upload1;  // 이미지1
	private String upload2;  // 이미지2
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUpload1() {
		return upload1;
	}
	public void setUpload1(String upload1) {
		this.upload1 = upload1;
	}
	public String getUpload2() {
		return upload2;
	}
	public void setUpload2(String upload2) {
		this.upload2 = upload2;
	}
}
