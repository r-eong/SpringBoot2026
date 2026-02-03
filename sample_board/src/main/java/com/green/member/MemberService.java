package com.green.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.green.member.mapper.MemberMapper;

@Service
public class MemberService {
//	회원가입 중복 확인
	public final static int user_id_chk = 0;
//	회원가입 성공여부 확인
	public final static int user_id_success = 1;
//	회원가입 실패 확인
	public final static int user_id_failChk = -1;
	
	@Autowired
	private MemberMapper membermapper;
	
	@Autowired
	PasswordEncoder passwordencoder;
	
//	회원가입
	public int signupConfirm(MemberDTO mdto) {
		System.out.println("MemberService - signupConfirm");
		
//		아이디 중복체크
		boolean isMember = membermapper.isMember(mdto.getId());
		
//		아이디 중복체크 통과하면 실행
//		id없으면 false
		if(isMember == false) {
//			비밀번호(pw) 를 암호화 (인간어 -> 기계어)
			String encodePw = passwordencoder.encode(mdto.getPw());
			
//			암호화된 비밀번호를 넣음
			mdto.setPw(encodePw);
			
			int result = membermapper.addMember(mdto);
			
//			가입 성공
			if(result > 0) {
				return user_id_success;  // 1
			}else {
				return user_id_failChk;  // -1
			}
		}else {
			return user_id_chk;  // 0
		}
	}
	
//	회원가입한 유저 전체출력
	public List<MemberDTO> printAllMember(){
		System.out.println("MemberService - printAllMember");
		
		return membermapper.allMember();
	}
	
//	유저 1명 정보
	public MemberDTO oneMember(String id) {
		System.out.println("MemberService - oneMember");
		
		return membermapper.oneMember(id);
	}
	
//	유저 1명의 비밀번호 가져오기
	public String onePw(String id) {
		System.out.println("MemberService - onePw");
		
		return membermapper.getPw(id);
	}
	
//	유저 1명의 정보 수정 - DB의 패스워드와 일치하는지 비교
	public boolean modfiyOneMember(MemberDTO mdto) {
		System.out.println("MemberService - modfiyOneMember");
		
//		DB 조회
		String DBpw = membermapper.getPw(mdto.getId());
		
//		비교
		if(DBpw.equals(mdto.getPw()) && DBpw != null) {
			return membermapper.updateMember(mdto) == 1;
		}else {
			return false;
		}
	}
	
//	유저 1명 삭제
	public boolean oneDelMember(String id) {
		System.out.println("MemberService - oneDelMember");
		
		return membermapper.delMember(id) == 1;
	}
	
//	암호화된 DB를 복호화(기계어 -> 인간어)해서 로그인하는 메소드
	public MemberDTO loginConfirm(MemberDTO mdto) {
		System.out.println("MemberService - loginConfirm");
		
//		DB에서 해당하는 id 가져오기
		MemberDTO DBMember = membermapper.oneMember(mdto.getId());
		
//		DB에서 꺼내온 id의 비밀번호와 input에 입력한 값이 일치하는지 확인
		if(DBMember != null && DBMember.getPw() != null) {
//			복호화해서 비교
			if(passwordencoder.matches(mdto.getPw(), DBMember.getPw())) {
				System.out.println("로그인 성공");
				return DBMember;
			}
		}
		
		System.out.println("로그인 실패");
		return null;
	}
}
