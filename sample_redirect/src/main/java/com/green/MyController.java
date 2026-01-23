package com.green;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MyController {
//	@GetMapping("/old-url")
//	public String oldPage() {
////		new-url 주소로 redirect 하라는 의미
////		새로운 주소로 이동하라는 뜻!
//		return "redirect:/new-url";
//	}
	
	@GetMapping("/old-url")
	public String oldPage(RedirectAttributes ra) {
//		리다이렉트 시점에서 딱 한 번 사용할 데이터를 임시로 보관
		ra.addFlashAttribute("msg", "이전 주소에서 자동으로 이동되었습니다.");
		return "redirect:/new-url";
	}
	
	@GetMapping("/new-url")
	public String newPage() {
//		new-url-view.html
		return "new-page-view";
	}
}
