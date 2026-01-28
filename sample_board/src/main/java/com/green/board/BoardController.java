package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BoardController {
	@Autowired
	BoardService boardservice;
	
//	메인 홈화면
	@GetMapping({"", "/"})
	public String home() {
		System.out.println("HomeController - home 메소드 - 홈화면 실행");
		
		return"home";
	}
	
//	게시판 글 작성 페이지
	@GetMapping("/board/form")
	public String boardForm() {
		System.out.println("HomeController - boardForm 메소드 - 회원가입폼 실행");
		
		String nextPage = "board/boardForm";
		
		return nextPage;
	}
	
//	게시판 작석 확인
	@PostMapping("/board/form_confirm")
	public String formConfirm(BoardDTO bdto, Model model) {
		System.out.println("HomeController - formConfirm 메소드 실행");
		
		String nextPage = "board/boardAll";
		
//		예외처리 호출
		int result = boardservice.boardAddForm(bdto);
		
//		게시 성공
		if(result == boardservice.form_title_success) {
			return "redirect:/board/alllist";
		
//		게시 실패
		}else {
			model.addAttribute("result", result);
			return nextPage;
		}
	}
	
//	게시판 전체출력
	@GetMapping("/board/alllist")
	public String boardAllList(Model model) {
		System.out.println("HomeController - boardAllList 메소드 - 게시판 전체출력 실행");
		
		List<BoardDTO> boardList = boardservice.printBoard();
		
		model.addAttribute("allList", boardList);
		
		String nextPage = "board/boardAll";
		
		return nextPage;
	}
	
//	상세페이지
	@GetMapping("/board/board_detail")
	public String boardDetail(Model model, @RequestParam("id") int id) {
		System.out.println("HomeController - boardDetail 메소드 - 게시판 상세페이지 실행" + id);
		
		BoardDTO oneBoard = boardservice.oneBoardDe(id);
		model.addAttribute("detail", oneBoard);
		
		String nextPage = "board/board_detail";
		return nextPage;
	}
	
//	게시물 수정 페이지로 이동
	@GetMapping("/board/modify")
	public String modifyBoard(Model model, BoardDTO bdto) {
		System.out.println("HomeController - modifyBoard 메소드 - 게시물 수정 실행");
		
		BoardDTO updateDetail = boardservice.oneBoardDe(bdto.getId());
		model.addAttribute("board", updateDetail);
		
		String nextPage = "board/board_modify";
		return nextPage;
	}
	
//	게시무 수정 처리하는
	@PostMapping("/board/modify")
	public String modifySubmit(BoardDTO bdto, RedirectAttributes ra) {
		System.out.println("HomeController - modifySubmit 메소드 - 게시물 수정 처리 실행");
		
		boolean result = boardservice.modBoard(bdto);
		
		if(result) {
			ra.addAttribute("msg", "게시물이 수정되었습니다.");
			return "redirect:/board/alllist";
		}else {
			System.out.println("실패!");
			return "redirect:/board/alllist";
		}
	}
	
//	게시물 삭제 
	@GetMapping("/board/delete")
	public String delBoardOne(@RequestParam("id") int id, RedirectAttributes ra) {
		System.out.println("HomeController - delBoardOne 메소드 - 게시물 삭제 실행");
		
		boolean result = boardservice.delOneBoard(id);
		
		if(result) {
			ra.addFlashAttribute("msg", "게시물이 삭제되었습니다.");
			
			return "redirect:/board/alllist";
		}else {
			return "redirect:/board/alllist";
		}
	}
}
