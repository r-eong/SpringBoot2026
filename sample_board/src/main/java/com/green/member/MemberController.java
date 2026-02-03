package com.green.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	@Autowired
	MemberService memberservice;
	
//	회원가입 폼 페이지로 이동
	@GetMapping("/member/signup")
	public String signup() {
		System.out.println("MemberController - signup");
		
		String nextPage = "member/signup_form";
		return nextPage;
	}
	
//	회원가입 후 - 확인 페이지
	@PostMapping("/member/signup_confirm")
	public String signupConfirm(MemberDTO mdto, Model model) {
		System.out.println("MemberController - signupConfirm");
		
		String nextPage = "member/signup_result";
		
//		회원가입 제대로 되었는지 실패했는지 예외처리
		int result = memberservice.signupConfirm(mdto);
		
		model.addAttribute("result", result);
		
//		회원가입 성공하면 목록에 추가
		if(result == memberservice.user_id_success) {
			return nextPage;
		}else {
			return nextPage;
		}
	}
	
//	회원가입한 유저 전체 출력
	@GetMapping("/member/list")
	public String memberList(Model model) {
		System.out.println("MemberController - memberList");
		
		List<MemberDTO> memberList = memberservice.printAllMember();
		
		model.addAttribute("list", memberList);
		
		String nextPage = "member/memberList";
		return nextPage;
	}
	
//	유저 1명의 정보 상세
	@GetMapping("member/memberInfo")
	public String memberInfo(Model model, @RequestParam("id") String id) {
		System.out.println("MemberController - memberInfo");
		
		MemberDTO oneMember = memberservice.oneMember(id);
		model.addAttribute("oneMember", oneMember);
		
		String nextPage = "member/member_info";
		return nextPage;
	}
	
//	유저 1명의 개인정보 수정하는 페이지로 이동
	@GetMapping("/member/modify")
	public String modfiyForm(Model model, MemberDTO mdto) {
		System.out.println("MemberController - modfiyForm");
		
		MemberDTO newInfo = memberservice.oneMember(mdto.getId());
		model.addAttribute("member", newInfo);
		
		String nextPage = "member/member_modify";
		return nextPage;
	}
	
//	유저 1명의 개인정보 수정 처리 - 비밀번호 일치하는지 확인
	@PostMapping("/member/modify")
	public String modfiySubmit(MemberDTO mdto, RedirectAttributes ra) {
		System.out.println("MemberController - modfiySubmit");
		
		boolean result = memberservice.modfiyOneMember(mdto);
		
//		업데이트 성공 - 화면이동
		if(result) {
			ra.addAttribute("msg", "회원정보가 수정되었습니다.");
			return "redirect:/member/list";
			
//		업데이트 실패 - 화면이동 없음
		}else {
			ra.addAttribute("msg", "비밀번호를 다시 입력해주세요.");
			return "redirect:/member/modify?id=" + mdto.getId();
		}
	}
	
//	유저 1명 삭제
	@GetMapping("/member/delMember")
	public String delMember(@RequestParam("id") String id, RedirectAttributes ra) {
		System.out.println("MemberController - delMember");
		
//		삭제되면 true, 안되면 false
		boolean result = memberservice.oneDelMember(id);
		
//		입력된 id 존재 - 삭제 - 화면이동
		if(result) {
			ra.addAttribute("msg", "회원이 삭제되었습니다.");
			return "redirect:/member/list";
			
//		입력된 id 존재안함 - 삭제불가 - 화면이동 없음
		}else {
			ra.addAttribute("msg", "삭제되지 않았습니다.");
			return "redirect:/member/memberInfo?id=" + id;
		}
	}
	
//	로그인
	@GetMapping("/member/login")
	public String loginForm() {
		System.out.println("MemberController - loginForm");
		
		String nextPage = "/member/login_form";
		return nextPage;
	}
	
//	로그인 처리
	@PostMapping("/member/loginPro")
	public String loginPro(MemberDTO mdto, HttpSession session) {
		System.out.println("MemberController - loginPro");
		
		MemberDTO loginMember = memberservice.loginConfirm(mdto);
		
//		로그인 성공 - 화면이동
		if(loginMember != null) {
			session.setAttribute("loginMember", loginMember);
			
			return "redirect:/";
		}else {
			return "redirect:/member/login";
		}
	}
	
//	세션 로그아웃
	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
		System.out.println("MemberController - logout");
		
//		세션 완전삭제
		session.invalidate();
		System.out.println("로그아웃 완료");
//		홈으로 이동
		return "redirect:/";
	}
}
