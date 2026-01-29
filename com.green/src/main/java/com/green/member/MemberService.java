package com.green.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//controller(최초의 질문) -> service(질문):다리역할 -> DAO(질문) -> DB(데이터값 찾음)
//DB(데이터값 보냄) -> DAO(대답) -> service(대답) -> controller(최종 대답)

@Service
public class MemberService {
//	아이디 중복체크, 성공, 실패 - 상수변수 정의
//	회원가입 중복 확인하는 상수 변수
	public final static int user_id_already_exit = 0;
//	회원가입 성공여부 확인하는 상수 변수
	public final static int user_id_success = 1;
//	회원가입 실패 확인하는 상수 변수
	public final static int user_id_fail = -1;
	
//	MemberDAO DI 의존객체 정의
	@Autowired
	MemberDAO memberdao;
	
//	PasswordEncoder 객체 DI 정의
	@Autowired
	PasswordEncoder passwordEncoder;

	public int signupConfirm(MemberDTO mdto) {
		System.out.println("MemberService - signupConfirm 메소드 실행");
		
//		회원가입 중복체크
		boolean isMember = memberdao.isMember(mdto.getId());
		
//		회원가입 중복체크 통과시 실행
//		id 없음 = false
		if(isMember == false) {
//			중복된 id 존재하지 않을때 사용자의 정보가 DB에 추가되어야함
//			DB에 회원정보가 추가될 때 암호화가 이루어져야한다.
			
//			문자인 pw 를 암호화됨 비밀번호로 변환하는 코드
			String encodePw = passwordEncoder.encode(mdto.getPw());
//			encode : 암호화. 인간언어 -> 기계어
//			decode : 복호화. 기계어 -> 인간언어
			
//			암호화된 encodePw 를 mdto.getPw() 로 암호화해서 수정해줘야함
			mdto.setPw(encodePw);  // 암호화된 값이 들어감
			
			int result = memberdao.insertMember(mdto);
			
			if(result > 0) {  // 가입 성공
				return user_id_success;  // result = 1
			}else {  // 가입 실패
				return user_id_fail;  // result = -1
			}
		}else {
//			중복된 아이디 존재
			return user_id_already_exit;  // result = 0
		}
	}
	
//	회원가입한 유저 전체 출력 메소드
	public List<MemberDTO> printAlluser() {
		System.out.println("MemberService - printAlluser 메소드 실행");
		
		return memberdao.allSelectMember();
	}
	
//	-------------------------------- 2026년 1월 27일 서비스로직 작성부분 --------------------------------
	
//	유저 1명의 정보
	public MemberDTO oneSelect(String id) {
		System.out.println("MemberService - oneSelect 메소드 실행");
		
		return memberdao.oneSelectMember(id);
	}
	
//	유저 1명의 비밀번호만 출력/가져오는 메소드
	public String onePass(String id) {
		System.out.println("MemberService - oneModf 메소드 실행");
		
		return memberdao.getPass(id);
	}
	
//	유저 1명의 정보 수정 메소드
//	DB 의 패스워드와 같은지 비교.
//	DB 의 패스워드와 내가 입력한 패스워드가 같을 때 / 다를 때 실행문.
	public boolean modfiyMember(MemberDTO mdto) {
		System.out.println("MemberService - modfiyMember 메소드 실행");
		
//		1. DB 조회
		String dbPass = memberdao.getPass(mdto.getId());
		
//		2. if 로 비교
		if(dbPass.equals(mdto.getPw()) && dbPass != null) {
//			내가 입력한 DB 의 패스워드가 존재할 때 
			return memberdao.updateMember(mdto) == 1;  // T/F 로 반환하려고
		}else {
//			내가 입력한 DB 의 패스워드가 존재하지 않을 때
			return false;
		}
	}
	
//	유저 1명 삭제하는 메소드
	public boolean oneDel(String id) {
		System.out.println("MemberService - oneDel 메소드 실행");
//		현재 delMember 의 결과값이 result = 0 또는 1 이 나옴.
//		삭제되면 1, 아니면 0
//		memberdao.delMember(id) -> 1 == true
//		memberdao.delMember(id) -> 0 == false
		return memberdao.delMember(id) == 1;
	}
	
//	-------------------------------- 2026년 1월 29일 추가쿼리 작성부분 --------------------------------
	
//	암호화된 DB를 복호화하여 로그인하는 메소드
	public MemberDTO loginConfirm(MemberDTO mdto) {
		System.out.println("MemberService - loginConfirm 메소드 실행");
		
//		1. DB에서 해당 정보의 id 가져오기
		MemberDTO dbMember = memberdao.oneSelectMember(mdto.getId());
		
//		2. DB에서 꺼내온 id의 비밀번호와 input에 입력한 값이 일치하는지 확인
		if(dbMember!= null && dbMember.getPw() != null) {
			if(passwordEncoder.matches(mdto.getPw(), dbMember.getPw())) {
//			┖> 암호화된 데이버를 passEncoder.matches(사용자 입력값, DB에 저장되어있는 암호화되어있는 값)
//			matches : 복호화해서 비교해주는 애
				System.out.println("로그인 성공");
				return dbMember;
			}
		}
		
		System.out.println("로그인 실패");
		return null;  // 로그인 실패
	}
}
