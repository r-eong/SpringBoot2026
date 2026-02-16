package reviewboard.controller;

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

import reviewboard.dto.ReviewBoardDTO;
import reviewboard.service.ReviewBoardService;

@Controller
public class ReviewBoardController {
	@Autowired
	ReviewBoardService reviewboardservice;
	
//	게시글 목록으로 이동
	@GetMapping("/review/list")
	public String boardList(Model model) {
		System.out.println("ReviewBoardController - boardList 실행");
		
		List<ReviewBoardDTO> reviewList = reviewboardservice.getAllReviewBoard();
		
		model.addAttribute("reviewList", reviewList);
		
		return "/reviewBoard/reviewboardList";
	}
	
//	글쓰기 폼으로 이동
	@GetMapping("/review/writer")
	public String boardWriter() {
		System.out.println("ReviewBoardController - boardWriter 실행");
		
		return "/reviewBoard/reviewboardWrite_Form";
	}
	
//	글쓰기를 처리
	@PostMapping("/review/writerPro")
	public String boardWriterPro(ReviewBoardDTO rdto, 
			@RequestParam("file1") MultipartFile upload1, 
			@RequestParam("file2") MultipartFile upload2) throws IllegalStateException, IOException {
		System.out.println("ReviewBoardController - boardWriterPro 실행");

		String savePath = "c:/upload/";
		
		File saveDir = new File(savePath);
//		존재하지 않으면
		if(!saveDir.exists()) {
			saveDir.mkdirs();
		}
		
//		첫 번째 이미지 업로드
		if(!upload1.isEmpty()) {
			String originalName1 = upload1.getOriginalFilename();
			
			File file1 = new File(savePath + originalName1);
			
			upload1.transferTo(file1);
			
			rdto.setUpload1(originalName1);
		}
		if(!upload2.isEmpty()) {
			String originalName2 = upload2.getOriginalFilename();
			File file2 = new File(savePath + originalName2);
			upload2.transferTo(file2);
			rdto.setUpload2(originalName2);
		}
		
		reviewboardservice.insertReviewBoard(rdto);
		return "redirect:/review/list";
	}
	
//	리뷰 상세페이지로 이동 + 조회수 증가
	@GetMapping("/review/detail")
	public String boardDetail(@RequestParam("num") int num, Model model) {
		System.out.println("ReviewBoardController - boardDetail 실행");
		
		reviewboardservice.updateReviewBoardCount(num);
		
	    ReviewBoardDTO onelist = reviewboardservice.getOneBoard(num);
	    
//	    평균 구하는거 호출
	    int averageStars = reviewboardservice.getAverageStars();

	    model.addAttribute("onelist", onelist);
	    model.addAttribute("averageStars", averageStars);  // 평균 별점
	    
	    return "/reviewBoard/reviewboardDetail";
	}
	
//	리뷰 수정
	@PostMapping("/review/reWrite")
	public String reviewBoardUpdate(Model model, @RequestParam("num") int num) {
		System.out.println("ReviewBoardController - reviewBoardUpdate 실행");
		
		ReviewBoardDTO onelist = reviewboardservice.getOneBoard(num);
		model.addAttribute("onelist", onelist);
		
		return "/reviewBoard/reviewboardReWrite_Form";
	}
	
//	리뷰 수정 처리
	@PostMapping("/review/reWritePro")
	public String reviewBoardUpdatePro(ReviewBoardDTO rdto) {
		System.out.println("ReviewBoardController - reviewBoardUpdatePro 실행");
		
		int result = reviewboardservice.updateReviewBoard(rdto);
		
//		성공 시 목록으로
		if(result > 0) {
			return "redirect:/review/list";
		
//		실패 시 다시 상세페이지로
		}else {
			return "redirect:/review/detail?num=" + rdto.getNum();
		}
	}
	
//	리뷰 삭제 + 비밀번호 확인
	@PostMapping("/review/deletePro")
	public String boardDeletePro(@RequestParam("num") int num, 
			@RequestParam("password") String password) {
		System.out.println("ReviewBoardController - boardDeletePro 실행");
		
		int result = reviewboardservice.deleteReviewBoard(num, password);
		
//		삭제 성공
		if(result > 0) {
			System.out.println("비밀번호 확인");
			return "redirect:/review/list";
			
//		비밀번호 틀리거나 삭제 실패 시 상세페이지 유지
		}else {
			System.out.println("비밀번호 확인 실패");
			return "redirect:/review/detail?num=" + num;
		}
	}
}
