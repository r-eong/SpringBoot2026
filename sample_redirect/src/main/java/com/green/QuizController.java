package com.green;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class QuizController {
//	비밀번호 입력 화면
	@GetMapping("/quiz")
	public String quizPage() {
		System.out.println("퀴즈 입력화면 이동 완");
		return "main-view";
	}
	
//	확인버튼
	@PostMapping("/quiz-page")
	public String quiz(RedirectAttributes ra, 
			@RequestParam("pw") String pw) {
		
//		정답이면 메인뷰로 이동
		if(pw.equals("1234")) {
//			ra.addFlashAttribute("main", "리다이렉트를 통해 안전하게 메인페이지에 도착했습니다.");
			return "redirect:/main";
			
//		오답이면 제자리(=다시퀴즈페이지로)
		}else {
			ra.addFlashAttribute("errorMsg", "비밀번호가 틀렸습니다. 다시 시도하세요!");
			return "redirect:/quiz";
		}
	}
	
//	정답페이지
	@GetMapping("/main")
	public String quizMainPage() {
		System.out.println("정답 페이지로 이동 완");
		return "quiz-view";
	}
}
