package com.green.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	public int signupConfirm(MemberDTO mdto) {
		System.out.println("MemberService - signupConfirm 메소드 실행");
		
//		회원가입 중복체크
		boolean isMember = memberdao.isMember(mdto.getId());
		
//		회원가입 중복체크 통과시 실행
//		id 없음 = false
		if(isMember == false) {
//			중복된 id 존재하지 않을때 사용자의 정보가 DB에 추가되어야함
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
		return memberdao.insertMember();
	}
}
