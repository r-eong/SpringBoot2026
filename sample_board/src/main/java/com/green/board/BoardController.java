package com.green.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
	@Autowired
	BoardService boardservice;
	
	@GetMapping("/board/home")
	public String home() {
		System.out.println("HomeController - home 메소드 - 홈화면 - 전체 게시물 출력 실행");
		
		return"home";
	}
	
	@GetMapping("/board/form")
	public String boardForm() {
		System.out.println("HomeController - boardForm 메소드 - 회원가입폼 실행");
		
		String nextPage = "board/boardForm";
		
		return nextPage;
	}
}
