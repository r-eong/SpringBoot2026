package com.green.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.green.MemberDTO;
import com.green.MemberService;
import com.green.SamplePjt02Application;


@Controller
public class MemberController {
//	DI(의존성 객체 주입)
//	MemberService 가 직접 멤버 서비스를 생성하지 않고
//	Spring 컨테이너가 만든 MemberService 를 주입시키라는 뜻
	@Autowired
	MemberService memberService;
    
//	핸들러 메소드
	
//	회원가입 양식
	// http://localhost:8090/member/signup 의 경로를 매핑(=연결)한다
	@GetMapping("/member/signup")  // 그냥 주소 이름을 지어준것!
	public String signUpForm() {
		System.out.println("signUpForm()");
//		┖> log역할 (console.log 역할!)
		return "signUpForm";
//		┖> 응답에 사용하는 html 파일 이름 반환
//		src/main/resources -> temllates -> signUpForm.html 이름을 쓰는것!
	}
	
//	로그인 양식
	@GetMapping("/member/signin")
	public String signInForm() {
		System.out.println("signInForm()");
		return "signInForm";
	}
	
//	회원가입한 데이터가 signUpResult 페이지로 전달되는 메소드
//	회원가입한 정보를 매개변수로 넘겨줘야 하므로 @RequestParam 사용
//	예) <input type="text" name="id" placeholder="id" /> 의 name 을 괄호 안에 적어줌!
	@PostMapping("/member/signup_confirm")  // 숨겨서 가기
	public String signUpConfirm(MemberDTO mdto, Model model) {
		System.out.println("signUpConfirm()");
		
//		비즈니스 로직을 담당하는 클래스 : MemberService
//		new 키워드 이용해 객체 생성
//		MemberService memberservice = new MemberService();
		memberService.signUpConfirm(mdto);
		
//		가입한 시간 출력
//		YYY-MM-DD 00:00:00
		Date now = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		
//		model 로 담기
		model.addAttribute("new_id", mdto.getId());
		model.addAttribute("new_pw", mdto.getPw());
		model.addAttribute("new_email", mdto.getEmail());
		model.addAttribute("now", sd.format(now));
		
		return "signUpResult";
	}

//	MemberDTO 를 데이터 타입으로 매개변수 지정
	@PostMapping("/member/signin_confirm")
	public ModelAndView signInConfirm(MemberDTO mdto) {
		System.out.println("signInConfirm()");
		
//		MemberService memberservice = new MemberService();
		memberService.signInConfirm(mdto);
		
		ModelAndView modelNview = new ModelAndView();
//		view 페이지 signInResult.html 은
		modelNview.setViewName("signInResult");
//		이렇게 담는다.
		
//		modelNview 에 담기
		modelNview.addObject("id", mdto.getId());
		modelNview.addObject("pw", mdto.getPw());
		
		return modelNview;
	}
}
