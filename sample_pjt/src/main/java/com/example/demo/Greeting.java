package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//Greeting 클래스는 Controller 의 역할을 하게 지정
@Controller  // Controller 라는 Bean 객체가 생성되도록 어노테이션 처리
public class Greeting {
//	메소드
	@GetMapping("/")  // http://localhost:8090/
	public String greet() {
		System.out.println("greet()");
		return "home";  // home.html <- 페이지 이름 반환
//		┖> spring.thymeleaf.suffix=.html 
//		return 으로 반환하는 문자 값에 꼬리말 부분에 .html 붙여서 반환
//		고로, 페이지 home.html 이 반환됨
		
//		spring.thymeleaf.prefix=classpath:/templates/
//		반환된 home.html 의 경로는 classpath:/templates/ 안에 존재해야한다
	}
}
