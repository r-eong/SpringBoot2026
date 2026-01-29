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
	
//	게시판 글 작성 페이지
	@GetMapping("/board/form")
	public String boardForm() {
		System.out.println("BoardController - boardForm 메소드 - 회원가입폼 실행");
		
		String nextPage = "board/boardForm";
		
		return nextPage;
	}
	
//	게시판 작석 확인
	@PostMapping("/board/form_confirm")
	public String formConfirm(BoardDTO bdto, Model model) {
		System.out.println("BoardController - formConfirm 메소드 실행");
		
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
	
////	게시판 전체출력
//	@GetMapping("/board/alllist")
//	public String boardAllList(Model model) {
//		System.out.println("BoardController - boardAllList 메소드 - 게시판 전체출력 실행");
//		
//		List<BoardDTO> boardList = boardservice.printBoard();
//		
//		model.addAttribute("allList", boardList);
//		
//		String nextPage = "board/boardAll";
//		
//		return nextPage;
//	}
	
//	상세페이지
	@GetMapping("/board/board_detail")
	public String boardDetail(Model model, @RequestParam("id") int id) {
		System.out.println("BoardController - boardDetail 메소드 - 게시판 상세페이지 실행" + id);
		
		BoardDTO oneBoard = boardservice.oneBoardDe(id);
		model.addAttribute("detail", oneBoard);
		
		String nextPage = "board/board_detail";
		return nextPage;
	}
	
//	게시물 수정 페이지로 이동
	@GetMapping("/board/modify")
	public String modifyBoard(Model model, BoardDTO bdto) {
		System.out.println("BoardController - modifyBoard 메소드 - 게시물 수정 실행");
		
		BoardDTO updateDetail = boardservice.oneBoardDe(bdto.getId());
		model.addAttribute("board", updateDetail);
		
		String nextPage = "board/board_modify";
		return nextPage;
	}
	
//	게시무 수정 처리하는
	@PostMapping("/board/modify")
	public String modifySubmit(BoardDTO bdto, RedirectAttributes ra) {
		System.out.println("BoardController - modifySubmit 메소드 - 게시물 수정 처리 실행");
		
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
		System.out.println("BoardController - delBoardOne 메소드 - 게시물 삭제 실행");
		
		boolean result = boardservice.delOneBoard(id);
		
		if(result) {
			ra.addFlashAttribute("msg", "게시물이 삭제되었습니다.");
			return "redirect:/board/alllist";
		}else {
			ra.addFlashAttribute("msg", "삭제실패");
			return "redirect:/board/board_detail?=id" + id;
		}
	}
	
//	게시물 검색
	@GetMapping("/board/alllist")
	public String boardSearch(Model model, 
			@RequestParam(required = false, value="shearchKeyWord") String shearchKeyWord, 
			@RequestParam(required = false, value="searchType") String searchType) {
		System.out.println("BoardController - boardSearch 메소드 - 게시물 검색 실행");
		
		List<BoardDTO> boardList;
		
		if(searchType != null && !searchType.trim().isEmpty()) {  // 검색
			boardList = boardservice.SearchBoard(shearchKeyWord, searchType);
		}else {  // 검색 취소/초기화 - 목록 전체출력
			boardList = boardservice.printBoard();
		}
		
		model.addAttribute("allList", boardList);
		
		String nextPage = "board/boardAll";
		return nextPage;
	}
}
