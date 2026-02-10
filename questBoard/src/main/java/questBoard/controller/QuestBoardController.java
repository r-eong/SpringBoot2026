package questBoard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import questBoard.dto.QuestBoardDTO;
import questBoard.service.QuestBoardService;

@Controller
public class QuestBoardController {
	@Autowired
	QuestBoardService questboardservice;
	
//	게시글 목록
	@GetMapping("/quest/list")
	public String questList(Model model) {
		System.out.println("QuestBoardController - questList 실행");
		
		List<QuestBoardDTO> queList = questboardservice.getAllQuestBoard();
		
		model.addAttribute("queList", queList);
		
		return "/questBoard/questBoardList";
	}
}
