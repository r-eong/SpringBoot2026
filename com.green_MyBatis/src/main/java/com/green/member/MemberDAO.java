package com.green.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	public List<MemberDTO> allSelectMember() {
		System.out.println("MemberDAO - allSelectMember 메소드 실행");
		
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
	
//	-------------------------------- 2026년 1월 27일 추가쿼리 작성부분 --------------------------------
	
//	유저 1명의 정보
	public MemberDTO oneSelectMember(String id) {
		System.out.println("MemberDAO - oneSelectMember 메소드 실행");
		
//		반환받을 MemberDTO 객체생성
		MemberDTO mdto = new MemberDTO();
		
//		sql 구문
		String sql = "SELECT * FROM user_mamber WHERE id=?";
		
//		예외처리
//		자동 close 를 위해 Connection 설정
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
//			실행구문
//			? 물음표(id=?) 대응이 우선
			pstmt.setString(1, id);
			
//			SELECT문은 반드시 ResultSet 객체에 담는다
			ResultSet rs = pstmt.executeQuery();
			
//			rs.next() 없이 값을 꺼내오면 항상 빈 DTO 임을 주의하기 ★
			if(rs.next()) {
				mdto.setNo(rs.getInt("no"));
				mdto.setId(rs.getString("id"));
				mdto.setPw(rs.getString("pw"));
				mdto.setMail(rs.getString("mail"));
				mdto.setPhone(rs.getString("phone"));
				mdto.setReg_date(rs.getString("reg_date"));
				mdto.setMod_date(rs.getString("mod_date"));
			}
			//rs.close();
		}catch (SQLException s) {
			s.printStackTrace();
			System.out.println("SQL 오류 ");
		}
		
		return mdto;
	}
	
//	유저 1명의 정보를 수정하는 쿼리
	public int updateMember(MemberDTO mdto) {
		System.out.println("MemberDAO - updateMember 메소드 실행");
		
		int result = 0;
		String sql = "UPDATE user_mamber SET mail=?, phone=? WHERE id=?";
//		┖> mail = 1 / phone = 2 / id = 3 이 됨!!
		
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
//			물음표 대응
			pstmt.setString(1, mdto.getMail());
			pstmt.setString(2, mdto.getPhone());
			pstmt.setString(3, mdto.getId());
			
			result = pstmt.executeUpdate();
			
			System.out.println("UPDATE result : " + result);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
//	유저 1명의 비밀번호 리턴하는 쿼리
	public String getPass(String id) {
		System.out.println("MemberDAO - getPass 메소드 실행");
		
		String pass = "";
		String sql = "SELECT pw FROM user_mamber WHERE id=?";
		
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
//			물음표 대응
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				pass = rs.getString(1);  // 1 : 비밀번호 값에 저장된 매핑 index 번호
			}
			
			System.out.println("getPass - pw : " + pass);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return pass;
	}
	
//	유저 1명 삭제 메소드
	public int delMember(String id) {
		System.out.println("MemberDAO - delMember 메소드 실행");
		
		int result = 0;
		String sql = "DELETE FROM user_mamber WHERE id=?";
		
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
//			물음표 대응
			pstmt.setString(1, id);
			
//			쿼리문 실행할 때 executeUpdate 는 delete, insert, update 에 사용
//			select 문 실핼할 때는 executeQuery 사용
			result = pstmt.executeUpdate();
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		
		return result;
	}
	
//	-------------------------------- 2026년 1월 29일 추가쿼리 작성부분 --------------------------------
	
////	로그인 메소드  <- 없어도 됨!!
//	public MemberDTO setloginForm(String id, String pw) {
//		System.out.println("MemberDAO - singInForm 메소드 실행");
//		
//		MemberDTO mdto = new MemberDTO();
//		
////		sql문
//		String sql = "SELECT * FROM user_mamber WHERE id=?, pw=?";
//		
//		try(Connection conn = dataSource.getConnection();
//			PreparedStatement pstmt = conn.prepareStatement(sql);){
////			물음표 대응
//			pstmt.setString(1, id);
//			pstmt.setString(1, pw);
//			
//			ResultSet rs = pstmt.executeQuery();
//			
//			if(rs.next()) {
//				pstmt.setString(3, mdto.getId());
//				pstmt.setString(3, mdto.getPw());
//			}
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return mdto;
//	}
}
