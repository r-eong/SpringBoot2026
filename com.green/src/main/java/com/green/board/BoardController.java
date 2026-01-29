package com.green.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {
	
	@Autowired
	BoardService boardservice;
	
//	1. 게시글 작성 폼화면으로 이동
	@GetMapping("/board/write")
	public String boardWriteForm() {
		System.out.println("1. BoardController - boardWriteForm 메소드 실행");
		
		String nextPage = "board/boardWrite_form";
		return nextPage;
	}
	
//	2. 폼에서 입력한 데이터를 DB에 영구저장하는걸 처리하는 컨트롤러
	@PostMapping("/board/writePro")
	public String boardWritePro(BoardDTO bdto) {
		System.out.println("1. BoardController - boardWritePro 메소드 실행");
		
//		service 의 insertBoard 메소드 호출 후 DB에 저장
		boardservice.insertBoard(bdto);
		
//		저장 후 게시판 목록으로 페이지 이동
		return "redirect:/board/list";
	}
	
//	3. DB에서 전체 게시글 select 로 검색되어 전체 추출 - model 객체에 담음 - 화면이동
//	@GetMapping("/board/list")  // 검색기능 전 코드
//	public String boardList(Model model) {
//		System.out.println("1. BoardController - boardList 메소드 실행");
//		
//		List<BoardDTO> boardList = boardservice.allBoard();
////		┖> 호출할 메소드가 이거여서 public List<BoardDTO> allBoard !!
//		
//		model.addAttribute("list", boardList);
//		
//		String nextPage = "board/boardList";
//		return nextPage;
//	}
	@GetMapping("/board/list")  // 검색기능 추가 코드
	public String boardList(Model model,
			@RequestParam(required = false, value="searchKeyWord") String searchKeyWord, 
			@RequestParam(required = false, value="searchType") String searchType) {
		System.out.println("1. BoardController - boardList 메소드 실행");
		
		List<BoardDTO> boardList;
		
//		검색 - 검색내용 list에 출력
//		JAVA는 널포인트인셉션? 이라는걸 갖고있어서 null 처리를 꼭 해줘야함
		if(searchType != null && !searchType.trim().isEmpty()) {
//								  ┖isEmpty() = 공백이냐?
//			boardservice에서 shearchBoard 호출
			boardList = boardservice.shearchBoard(searchKeyWord, searchType);
			
//		검색 초기화 - list 전체출력
		}else {
			boardList = boardservice.allBoard();
		}
		
//		검색 초기화 - list 전체출력
		model.addAttribute("list", boardList);
		
		String nextPage = "board/boardList";
		return nextPage;
	}
	
//	4. 하나의 게시글 상세 페이지 보는 핸들러
//	num 을 받음 - 해당 게시글 DB에서 조회 - 상세정보를 boardInfo에 전달
	@GetMapping("/board/boardInfo")
	public String boardInfo(@RequestParam("num") int num, Model model) {
		System.out.println("1. BoardController - boardInfo 메소드 실행" + num);
		
		BoardDTO oneBoardInfo = boardservice.getOneBoard(num);
		
		model.addAttribute("oneBoard", oneBoardInfo);
		
		String nextPage = "board/boardInfo";
		return nextPage;
	}
	
//	5. 게시글 수정 폼으로 이동하는 컨트롤러
	@GetMapping("/board/update")
	public String boardUpdateForm(Model model,
			@RequestParam("num") int num) {
		System.out.println("1. BoardController - boardUpdateForm 메소드 실행");
		
//		기존에 있던 하나의 게시글을 불러오는 쿼리를 이용하여 수정
		BoardDTO onboardInfo = boardservice.getOneBoard(num);
		
		model.addAttribute("oneBoard", onboardInfo);
		
		String nextPage = "board/boardUpdate_form";
		return nextPage;
	}
	
//	6. 게시글 수정을 처리하는 컨트롤러
	@PostMapping("/board/updatePro")
	public String boardUpdatePro(BoardDTO bdto, Model model) {
		System.out.println("1. BoardController - boardUpdatePro 메소드 실행");
		
		boolean isSuccess = boardservice.modifyBoard(bdto);
//		┖> public boolean modifyBoard(BoardDTO bdto)
		
//		수정완료면 true, 아니면 false
		if(isSuccess) {
			return "redirect:/board/list";  // 수정 완료시 목록으로 이동
		}else {
			return "redirect:/board/update?num=" + bdto.getNum();  // 수정 실패시 이동없음
		}
	}
	
//	---------------------------------- 2026-01-29 ----------------------------------
	
//	7. 하나의 게시글을 삭제하는 컨트롤러
//	현재 boardInfo.html 의 삭제하기 버튼 클릭하면
//	┖> 삭제 - 목록으로 화면 이동 / 실패 - 화면 이동 없음
	@GetMapping("/board/deletePro")
	public String boardDeletePro(@RequestParam("num") int num,
			@RequestParam("writerPw") String writerPw) {
		System.out.println("1. BoardController - boardDeletePro 메소드 실행");
		
		boolean isSuccess = boardservice.removeBoard(num, writerPw);
//		┖> public boolean removeBoard(int num, String writerPw)
		
		if(isSuccess) {  // 성공 - 화면이동
			return "redirect:/board/list";
		}else {  // 실패 - 화면이동없음
			return "redirect:/board/boardInfo?num=" + num;
		}
	}
}
