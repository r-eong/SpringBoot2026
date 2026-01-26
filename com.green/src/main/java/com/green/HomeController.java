package com.green;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
//	localhost:8090 또는 localhost:8090/ 을 함께 사용 가능하게
	@GetMapping({"", "/"})
	public String home() {
		System.out.println("HomeController 실행");
		return "home";
	}
}
