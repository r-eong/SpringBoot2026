package com.green.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.member.MemberDTO;

import jakarta.servlet.http.HttpSession;

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
	public String boardWritePro(BoardDTO bdto, HttpSession session) {
		System.out.println("1. BoardController - boardWritePro 메소드 실행");
		
//		session.setAttribute("loginMember") 로 저장한 데이터를 꺼내와야한다
//		세션에서 값 꺼내오는 메소드 : sessoin.getAttribute("loginMember")
//		로그인 id : admin9876 의 정보 한 행이 모두 MemberDTO 타입으로 loginedMember 에 저장된다.
		MemberDTO loginedMember = (MemberDTO)session.getAttribute("loginMember");
//		┖> Session 은 자바의 Object 최상위 객체 다운캐스팅해야한다.
		
//		로그인 정보가 존재하는지 체크하는 코드 필요
//		로그인 input 창이 비어있을 경우 입력이 불가능 하도록 방지!
		if(loginedMember != null) {
//			현재 로그인된 id 는 loginedMember.getId 에 담겨있음
			bdto.setId(loginedMember.getId());
			System.out.println("DB에 저장될 아이디 확인 : " + loginedMember.getId());
		}else {  // 로그인 실패
			return "redirect:/member/login";
		}
		
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
//	@GetMapping("/board/list")  // 검색기능 추가 코드
//	public String boardList(Model model,
//			@RequestParam(required = false, value="searchKeyWord") String searchKeyWord, 
//			@RequestParam(required = false, value="searchType") String searchType) {
//		System.out.println("1. BoardController - boardList 메소드 실행");
//		
//		List<BoardDTO> boardList;
//		
////		검색 - 검색내용 list에 출력
////		JAVA는 널포인트인셉션? 이라는걸 갖고있어서 null 처리를 꼭 해줘야함
//		if(searchType != null && !searchType.trim().isEmpty()) {
////								  ┖isEmpty() = 공백이냐?
////			boardservice에서 shearchBoard 호출
//			boardList = boardservice.shearchBoard(searchKeyWord, searchType);
//			
////		검색 초기화 - list 전체출력
//		}else {
//			boardList = boardservice.allBoard();
//		}
//		
////		검색 초기화 - list 전체출력
//		model.addAttribute("list", boardList);
//		
//		String nextPage = "board/boardList";
//		return nextPage;
//	}
//	@GetMapping("/board/list")  // 검색기능 추가 --- 2026-02-03 페이지 번호 코드 추가 ---
//	public String boardList(Model model,
//			@RequestParam(required = false, value="searchKeyWord") String searchKeyWord, 
//			@RequestParam(required = false, value="searchType") String searchType,
////			1. 페이지 번호 - 1부터 시작이므로 초기값 1로 정의
//			@RequestParam(value="page", defaultValue = "1") int page,
////			2. 페이지 사이즈 - 한 화면에 보여지는 게시글 개수를 5로 초기화
//			@RequestParam(value = "pageSize", defaultValue = "5") int pageSize
//			) {
//		System.out.println("1. BoardController - boardList 메소드 실행");
//		
////		3. totalCnt 메소드 호출
//		int totalCnt = boardservice.getAllCount();
//		
////		4. PageHandler 클래스 접근하기 위해 인스턴스화
//		PageHandler ph = new PageHandler(totalCnt, page, pageSize);
//		
//		List<BoardDTO> boardList;
//		
////		검색 - 검색내용 list에 출력
////		JAVA는 널포인트인셉션? 이라는걸 갖고있어서 null 처리를 꼭 해줘야함
//		if(searchType != null && !searchType.trim().isEmpty()) {
////								  ┖isEmpty() = 공백이냐?
////			boardservice에서 shearchBoard 호출
//			boardList = boardservice.shearchBoard(searchKeyWord, searchType);
//			
////		검색 초기화 - list 전체출력
//		}else {
////			boardList = boardservice.allBoard();
////			┖> 을 사용하지 못 하는 이유 : 페이징이 안 된 전체 페이지 출력 메소드라서
////			public List<BoardDTO> getPageList(int startRow, int pageSize)
//			boardList = boardservice.getPageList(ph.getStartRow(), pageSize);
//		}
//		
////		검색 초기화 - list 전체출력
//		model.addAttribute("list", boardList);
//		
////		pageHandler 클래스 전체 model 객체에 담아서 html 로 보내야 UI 화면에 그릴 수 있다.
//		model.addAttribute("ph", ph);
//		
//		String nextPage = "board/boardList";
//		return nextPage;
//	}
	@GetMapping("/board/list")  // 검색기능 추가 페이지 번호 코드 추가  --- 2026-02-04 검색 페이지 번호 코드 추가 ---
	public String boardList(Model model,
			@RequestParam(required = false, value="searchKeyWord") String searchKeyWord, 
			@RequestParam(required = false, value="searchType") String searchType,
//			1. 페이지 번호 - 1부터 시작이므로 초기값 1로 정의
			@RequestParam(value="page", defaultValue = "1") int page,
//			2. 페이지 사이즈 - 한 화면에 보여지는 게시글 개수를 5로 초기화
			@RequestParam(value = "pageSize", defaultValue = "5") int pageSize
			) {
		System.out.println("1. BoardController - boardList 메소드 실행");
		
//		3. totalCnt 메소드 호출
		int totalCnt;
		
//		totalCnt 를 조건에 만족하는 값으로 저장되도록 지정하는 부분
		if(searchType != null && !searchType.trim().isEmpty()) {
//			검색 성공 - 검색한 결과에 해당하는 개수 반환
			totalCnt = boardservice.getSearchCount(searchKeyWord, searchType);
		}else {
//			검색 안함 - 전체 게시글의 개수 반환
			totalCnt = boardservice.getAllCount();
		}
		
//		4. PageHandler 클래스 접근하기 위해 인스턴스화
//		예) 검색한 결과 totalCnt = 1 일 때
//											1		1		5
		PageHandler ph = new PageHandler(totalCnt, page, pageSize);
		
		List<BoardDTO> boardList;
		
//		검색 - 검색내용 list에 출력
//		JAVA는 널포인트인셉션? 이라는걸 갖고있어서 null 처리를 꼭 해줘야함
		if(searchType != null && !searchType.trim().isEmpty()) {
//								  ┖isEmpty() = 공백이냐?
//			boardservice에서 shearchBoard 호출
//			검색된 리스트를 반환하는 메소드
			boardList = boardservice.getSearchPageList(searchKeyWord, searchType, ph.getStartRow(), pageSize);
			
//		검색 초기화 - list 전체출력
		}else {
//			boardList = boardservice.allBoard();
//			┖> 을 사용하지 못 하는 이유 : 페이징이 안 된 전체 페이지 출력 메소드라서
//			public List<BoardDTO> getPageList(int startRow, int pageSize)
			boardList = boardservice.getPageList(ph.getStartRow(), pageSize);
		}
		
//		검색 초기화 - list 전체출력
		model.addAttribute("list", boardList);
		
//		pageHandler 클래스 전체 model 객체에 담아서 html 로 보내야 UI 화면에 그릴 수 있다.
		model.addAttribute("ph", ph);
		
//		searchType, searchKeyWord 를 계속 들고다녀야함!
//		검색하는 타입과 항목을 UI에 넘겨주지 않으면 오류발생. 
//		반드시 searchType, searchKeyWord 를 model 에 담아서 html 에 넘겨준다.
//		┖> public List<BoardDTO> getSearchPageList(String searchKeyWord, 
//		String searchType, int startRow, int pageSize) 에서 4개를 모두 받아야 하기 때문!
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyWord", searchKeyWord);
		
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
	
//	---------------------------------- 2026-02-04 ----------------------------------
	
//	로그인된 나의 게시글 목록을 검색/출력 하는 핸들러
	@GetMapping("/board/myPage")
	public String myBoardList(Model model, HttpSession session, 
			@RequestParam(value="page", defaultValue = "1") int page) {
		System.out.println("1. BoardController - myBoardList 메소드 실행");
		
//		세션을 이용해서 loginMember 로 가져오기 (MemberController.java / loginPro() 에 있음!)
//		세션 키 값 가져오는 메소드 : getAttribute("loginMember")
//		예) id = "auser01" 해당하는 행 전체
//		session.getAttribute("loginMember") 을 MemberDTO로 다운 캐스팅
//		logId 에 MemberDTO의 멤버변수 모두 저장됨.
		MemberDTO logId = (MemberDTO)session.getAttribute("loginMember");
		
//		로그인 실패/안함 상태 - member/login 으로 이동
		if(logId == null) {
			System.out.println("로그인 정보없음 - 로그인 페이지로 이동");
			return "redirect:/member/login";
		}
		
		int pageSize = 5;
		
//		로그인된 유저의 게시글 개수 조회
		int totalCnt = boardservice.getMyBoardCount(logId.getId());
		
//		인스턴스화
		PageHandler ph = new PageHandler(totalCnt, page, pageSize);
		
//		로그인된 유저의 게시글 목록 가져오기
		List<BoardDTO> myList = boardservice.getMyBoardList(logId.getId(), ph.getStartRow(), pageSize);
		
		model.addAttribute("list", myList);
		model.addAttribute("ph", ph);
		
		String nextPage = "board/myPage";
		return nextPage;
	}
}
