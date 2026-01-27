package com.green.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		System.out.println("memberController - memeberList 메소드 실행");
		
		List<MemberDTO> memberList = memberservice.printAlluser();
		
		model.addAttribute("list", memberList);
//		┖> html 에서 list 라는 이름으로 불러올거라는 뜻
	
		String nextPage = "member/memberList";
		return nextPage;
	}
	
//	-------------------------------- 2026년 1월 27일 컨트롤러 작성부분 --------------------------------
	
//	유저 1명의 정보 상세보기 핸들러
	@GetMapping("/member/memberInfo")
	public String memberInfo(Model model, @RequestParam("id") String id) {
		System.out.println("memberController - memberInfo 메소드 실행" + id);
		
//		MemberDTO oneMemberInfo = memberservice.oneSelect(mdto.getId());
//		┖> memberInfo(Model model,MemberDTO mdto) 로 쓸 경우 이렇게 작성
		MemberDTO oneMemberInfo = memberservice.oneSelect(id);
		model.addAttribute("oneList", oneMemberInfo);
		
		String nextPage = "member/memberInfo";
		return nextPage;
	}
	
//	개인정보 수정하는 화면으로 이동하는 핸들러
	@GetMapping("/member/modify")
	public String modifyForm(Model model, MemberDTO mdto) {
		System.out.println("memberController - UpdateOne 메소드 실행");
		
//		유저 1명의 정보 수정하는데 필요한 정보 : 유저 1명의 데이터
		MemberDTO newInfo = memberservice.oneSelect(mdto.getId());
		model.addAttribute("member", newInfo);
		
		String nextPage = "member/member_modify";
		return nextPage;
	}
	
//	개인정보 수정을 처리하는 핸들러
//	비밀번호가 일치하는지 비교 관련
//	redirect / modifyMember 사용
	@PostMapping("/member/modify")
	public String modifySubmit(MemberDTO mdto, RedirectAttributes ra) {
		System.out.println("memberController - modifySubmit 메소드 실행");
		
		boolean result = memberservice.modfiyMember(mdto);
		
//		업데이트 성공 : 비밀번호 맞음 - 화면(url)이동
		if(result) {
//			RedirectAttributes 는 1번만 데이터를 넘길 수 있다
			ra.addFlashAttribute("msg", "회원정보가 수정되었습니다.");
//			수정이 완료되면 list 로 이동
			return "redirect:/member/list";
			
//		업데이트 실패 : 비밀번호 틀림 - 화면(url)이동 없음
		}else {
			ra.addFlashAttribute("msg", "비밀번호를 다시 확인하세요.");
			return "redirect:/member/modify?id=" + mdto.getId();
//			┖> http://localhost:8090/member/modify?id=gun1234
		}
	}
	
	
//	유저 1명 삭제 핸들러
	@GetMapping("/member/delete")
	public String delMember(@RequestParam("id") String id,
			RedirectAttributes ra) {
		System.out.println("memberController - delMember 메소드 실행");
		
		boolean result = memberservice.oneDel(id);
//		┖> result - 삭제되면 true, 아니면 false
		
//		입력된 id 가 존재 - 삭제 - 삭제되면 화면(url)이동
		if(result) {
			ra.addFlashAttribute("msg", "회원이 삭제되었습니다.");
			return "redirect:/member/list";
			
//		입력된 id 존재하지 않음 - 삭제불가 - 화면(url)이동 없음
		}else {
			ra.addFlashAttribute("msg", "삭제 실패");
			return "redirect:/member/memberInfo?=id" + id;
		}
	}
}
