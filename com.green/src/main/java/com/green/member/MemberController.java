package com.green.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
//	MemberService 클래스 DI로 의존 객체화
	@Autowired
	MemberService memberservice;
	
//	회원가입 입력 폼 페이지
	@GetMapping("/member/signup")
	public String signup() {
		System.out.println("memberController - siginup 메소드 실행");
		String nextPage = "member/signup_form";
		
		return nextPage;
	}
	
//	회원가입 확인페이지
	@PostMapping("/member/signup_confirm")
	public String signupConfirm(MemberDTO mdto, Model model) {
		System.out.println("memberController - signupConfirm 메소드 실행");
		String nextPage = "member/siginup_result";
		
//		회원가입이 제대로 되었는지, 실패했는지 예외처리
		int result = memberservice.signupConfirm(mdto);
		
//		회원가입이 성공했을 경우 회원목록으로 redirect(새로운 주소로 이동)
		if(result == memberservice.user_id_success) {
			return "redirect:/member/list";
			
//		회원가입 실패한 경우
		}else {
			model.addAttribute("result", result);
			return nextPage;
		}
	}
	
//	회원가입한 유저 전체 출력 페이지
	@GetMapping("/member/list")
	public String memeberList(Model model) {
		List<MemberDTO> memberList = memberservice.printAlluser();
		
		model.addAttribute("list", memberList);
//		┖> html 에서 list 라는 이름으로 불러올거라는 뜻
	
		String nextPage = "/member/memberList";
		return nextPage;
	}
}
