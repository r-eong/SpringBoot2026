package replyBoard.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import replyBoard.dto.ReplyBoardDTO;
import replyBoard.service.ReplyBoardService;

@Controller
public class ReplyBoardController {
	@Autowired
	ReplyBoardService replyboardservice;
//	┖> 반드시 ReplyBoardService 인터페이스를 의존객체로 삽입해야함.
//	   ReplyBoardServiceImpl 를 DI 하면 안됨!
	
//	게시글 목록으로 이동하는 컨트롤러
	@GetMapping("/board/list")
	public String boardList(Model model) {
		System.out.println("ReplyBoardController - boardList 실행");
		
		List<ReplyBoardDTO> replyList = replyboardservice.getAllReplyBoard();
		
		model.addAttribute("replyList", replyList);
		
		return "/replyBoard/replyboardList";
	}
	
//	글쓰기 폼으로 이동하는 컨트롤러
	@GetMapping("/board/writer")
	public String boardWriter() {
		System.out.println("ReplyBoardController - boardWriter 실행");
		
		return "/replyBoard/replyboardWrite_Form";
	}
	
//	글쓰기를 처리하는 컨트롤러
//	@PostMapping("/board/writerPro")
//	public String boardWriterPro(ReplyBoardDTO rdto) {
//		System.out.println("ReplyBoardController - boardWriterPro 실행");
//		
//		replyboardservice.insertReplyBoard(rdto);
//		return "redirect:/board/list";
//	}
//	파일 업로드는 @PostMapping 만 가능
	@PostMapping("/board/writerPro")
	public String boardWriterPro(ReplyBoardDTO rdto, 
			@RequestParam("file1") MultipartFile upload1, 
			@RequestParam("file2") MultipartFile upload2) throws IllegalStateException, IOException {
		System.out.println("ReplyBoardController - boardWriterPro 실행");
		
//		1. 파일을 저장할 실제 하드디스크 위치 지정
//		   WebConfig 에서 설정한 file:///c:/upload/ 경로와 일치해야함.
		String savePath = "c:/upload/";
		
//		2. 안전장치 - 만약 c:/upload/ 폴더가 존재하지 않으면 자동생성되도록 작성
		File saveDir = new File(savePath);
//		존재하지 않으면
		if(!saveDir.exists()) {
//			mkdirs : 폴더가 없어도 한꺼면서 만들어주는 메소드입력
			saveDir.mkdirs();
		}
		
//		3. 첫 번째 이미지 업로드
//		예외처리 - 이미지가 비어있으면 추가되면 안됨 (DB에 아무것도 들어가지 않고 비어있어야한다는 뜻 같음!)
		if(!upload1.isEmpty()) {  // 사용자가 실제 파일은 보냈는지 확인
//			사용자가 올림 원래 파일명을 가져온다.
			String originalName1 = upload1.getOriginalFilename();
			
//			String saveName1 = originalName1;
//			┖> UUID 를 사용하지 않아서 굳이 이렇게 하지 않아도 됨!
			
			File file1 = new File(savePath + originalName1);
//			┖> 예) c:/upload/apple.jpg
			
//			transferTo : 이 명령어가 실행되는 순간 
//			서버 메모리에서 존재하던 파일이 실제 하드디스크 c:/upload 로 복사된다
			upload1.transferTo(file1);
			
//			DB에 저장할 파일명을 DTO에 세팅
			rdto.setUpload1(originalName1);
		}
		if(!upload2.isEmpty()) {
			String originalName2 = upload2.getOriginalFilename();
			File file2 = new File(savePath + originalName2);
			upload2.transferTo(file2);
			rdto.setUpload1(originalName2);
		}
		
		replyboardservice.insertReplyBoard(rdto);
		return "redirect:/board/list";
	}
	
//	게시글 1개의 상세페이지로 이동하는 컨트롤러
	@GetMapping("/board/detail")
	public String getOneBoard(Model model, @RequestParam("num") int num) {
		System.out.println("ReplyBoardController - getOneBoard 실행");
		
		ReplyBoardDTO onelist = replyboardservice.getOneBoard(num);
		model.addAttribute("onelist", onelist);
		
		return "/replyBoard/replyboardDetail";
	}
	
//	답글 작성하는 폼으로 이동하는 컨트롤러
	@GetMapping("/board/reply")
	public String reWriterForm(Model model, 
			@RequestParam("num") int num, 
			@RequestParam("ref") int ref, 
			@RequestParam("re_step") int re_step, 
			@RequestParam("re_level") int re_level) {
		System.out.println("ReplyBoardController - reWriteForm 실행");
		
		model.addAttribute("num", num);
		model.addAttribute("ref", ref);
		model.addAttribute("re_step", re_step);
		model.addAttribute("re_level", re_level);
		
		return "/replyBoard/replyboardReWrite_Form";
	}
	
//	답글 작성을 처리하는 컨트롤러
	@PostMapping("/board/reWritePro")
	public String reWriterPro(ReplyBoardDTO rdto) {
		System.out.println("ReplyBoardController - reWriterPro 실행");
		
		replyboardservice.replyProcess(rdto);
		
		return "redirect:/board/list";
	}
}
