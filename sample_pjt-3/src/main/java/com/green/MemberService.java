package com.green;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service 의 의미 : MemberService 클래스는 비즈니스 로직을 작성하는 클래스
//┖> 이걸 사용하면 AppConfig 이게 필요없음!
@Service
public class MemberService {
//	MemberDAO 클래스를 MemberService 클래스에서 사용하는 방법
//	DI 이용해 외부로부터 객체를 주입하여 사용
//	DI를 의미하는 @Autowired 를 사용
	@Autowired
	MemberDAO memberDao;
	
	public void signUpConfirm(MemberDTO mdto) {
		System.out.println("회원가입 정보 출력화면");
		memberDao.insertMember(mdto);
	}

	public void signInConfirm(MemberDTO mdto) {
		System.out.println("로그인 정보 출력화면");
		MemberDTO loginMember = memberDao.selectMember(mdto);
		
//		Cannot invoke "com.green.MemberDTO.getId()" because "loginMember" is null
//		┖> 로그인할 id, pw 가 비어있는데도 실행버튼을 눌랐을 때 발생하는 오류
//		   그러므로 반드시 null 을 예외처리 해야함
		
		if(loginMember != null && 
				mdto.getId().equals(loginMember.getId()) && 
				mdto.getPw().equals(loginMember.getPw())) {
			System.out.println("id : " + loginMember.getId());
			System.out.println("pw : " + loginMember.getPw());
			System.out.println("로그인 성공");
		}else {
			System.out.println("로그인 실패");
		}
	}

}