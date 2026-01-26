package com.green.member;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {
//	MySQL 브라이버 설치 및 JDBC 환경설정 완료
//	외부에서 DataSource 를 DI 로 삽입
	
	@Autowired
	private DataSource dataSource;
	
	public int insertMember(MemberDTO mdto) {
		System.out.println("MemberDAO - insertMember 메소드 실행");
		
//		실무에서 쿼리문 작성시 주로 대문자로 작성
//		no, reg_date, mod_date 는 default 값이 존재해서 추가하지 않아도 됨
//		추가 할 필드명이 정해져 있을 경우 반드시 (필드명1, 필드명2, ...) 와 같이 필드명을 명시한다
//		예) INSERT INTO USER_MEMBER(id, pw, mail, phone) VALUES(?, ?, ?, ?);
		String sql = "INSERT INTO user_mamber(id, pw, mail, phone) VALUES(?, ?, ?, ?)";
		int result = 0;
		
//		DB 는 네트워크를 통해 자료를 가져오므로 try~catch 구문을 이용
		try(
//			Connection 클래스를 이용해 dataSource 를 getConnection 해야한다
//			Connection 은 연결하는 자원이므로 사용 후 반드시 반납(= close())해야함. 
//			try(Connection~) 을 하면 자동으로 close 됨
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
				){
//			?, ?, ?, ? 값을 대응시켜야함
//			input 입력 -> mdto 가방에 담김 -> 각 열(1~4)에 해당하는 테이블에 input 값(mdto.getId())을 담음
//			mdto 라는 가방에서 필요한 자원을 getId() 에서 꺼냄
			pstmt.setString(1, mdto.getId());
			pstmt.setString(2, mdto.getPw());
			pstmt.setString(3, mdto.getMail());
			pstmt.setString(4, mdto.getPhone());
			
//			insert, delete, update 구문 실행명령 : executeUpdate()
			result = pstmt.executeUpdate();
//			executeUpdate 메소드의 의미 : insert, delete, update 문을 실행하고나면
//			실행 결과를 int 데이터 타입의 행의 개수로 반환한다
//			insert 1건 성공 -> 반환값 : 1
//			insert 0건 중복체크 -> 반환값 : 0
//			update 3건 수정 -> 반환값 : 3
//			delete 5건 삭제 -> 반환값 : 5
			System.out.println("insertMember - result 값 : " + result);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
//		회원가입 성공여부에 따라 result 에 -1, 0, 1 이 담김
	}

	public boolean isMember(String id) {
		System.out.println("MemberDAO - isMember 메소드 실행");
		return false;
	}
	
//	회원가입한 유저 전체 출력 메소드
//	public List<Member> printUser() {
//		System.out.println("MemberDAO - printUser 메소드 실행");
//		
//		try(
//				Connection conn = dataSource.getConnection();
//				Statement stmt = conn.createStatement();
//				ResultSet rs = stmt.executeQuery("select * from user_mamber");
//			){
//			
//			while(rs.next()) {
//				rs.getString("id");
//				rs.getString("pw");
//				rs.getString("mail");
//				rs.getString("phone");
//			}
//			rs.close();
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return "select * from user_mamber";
//	}

	public List<MemberDTO> insertMember() {
		System.out.println("MemberDAO - printUser 메소드 실행");
		
//		전체 목록 검색
		String sql = "SELECT * FROM user_mamber";
//		List<E> 는 인터페이스이므로 구현할 수 없다.
//		고로, ArrayList 를 이용해야한다.
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
//				select 구문은 executeQuery 실행한 결과를 ResultSet에 담는다.
				ResultSet rs = pstmt.executeQuery();
			){
			
//			rs.next() 는 다음 행에 값이 존재하면 true, 아니면 false 를 반환.
//			while 문은 rs.next() 가 false 가 되는 순간 종료됨
			while(rs.next()) {
				MemberDTO mdto = new MemberDTO();
//				mdto 를 rs의 결과값을 저장하는 용도(가방)로 사용
				
//				rs 의 결과값
//				no	id	pw	mail	phone	reg_date	mod_date
//				1	1	1	1		1		2026~		2026~
//				2	2	2	2		2		2026~		2026~

				mdto.setNo(rs.getInt("no"));
				mdto.setId(rs.getString("id"));
				mdto.setPw(rs.getString("pw"));
				mdto.setMail(rs.getString("mail"));
				mdto.setPhone(rs.getString("phone"));
				mdto.setReg_date(rs.getString("reg_date"));
				mdto.setMod_date(rs.getString("mod_date"));
				
//				ArrayList 에 추가
				list.add(mdto);
			}
			rs.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
