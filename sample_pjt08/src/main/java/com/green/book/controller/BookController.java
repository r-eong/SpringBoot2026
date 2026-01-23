package com.green.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.green.book.dao.BookDAO;
import com.green.book.dto.BookDTO;
import com.green.book.service.BookService;

@Controller
public class BookController {
//	DI 의존성 객체
	@Autowired
	BookService booksevice;
	
//	대여 폼 작성 페이지
	@GetMapping("/book/rent")
	public String bookRentForm() {
		System.out.println("\n★ 대여 폼 작성 페이지 이동완료");
		booksevice.bookList();
		return "bookRent";
	}
	
//	대여 완료 페이지
	@GetMapping("/book/rentCheck")
	public String bookrentChk(BookDTO bdto, Model model) {
		System.out.println("\n★ 대여확인 페이지 이동완료");
		
//		bdto 에 담기
		model.addAttribute("bookName", bdto.getBookName());
		model.addAttribute("writer", bdto.getWriter());
		model.addAttribute("isbn", bdto.getIsbn());
		model.addAttribute("userName", bdto.getUserName());
		model.addAttribute("rentDate", bdto.getRentDate());
		
		booksevice.bookCheck(bdto);
		
		return "bookList";
	}
	
//	관리자 도서 추가 페이지
	@GetMapping("/book/addBooks")
	public ModelAndView addBooksForm(BookDTO bdto) {
		System.out.println("\n★ 관리자 도서 추가 페이지 이동완료");
		booksevice.bookList();  //콘솔 출력 메소드 호출
		
//		도서 추가
		ModelAndView modelNview = new ModelAndView();
		modelNview.setViewName("bookAdd");
		
		modelNview.addObject("addBook", bdto.getAddBook());
		return modelNview;
	}
	
//	검색 페이지
	@GetMapping("/book/serchBooks")
	public String bookSerch(BookDTO bdto, Model model) {
		System.out.println("\n★ 도서 검색 페이지 이동완료");
		model.addAttribute("list", booksevice.배열);
		
		return "bookSerch";
	}
	
//	대여 목록
	@GetMapping("/book/serchBooks-list")
	public String serchBookList(BookDTO bdto) {
		booksevice.배열.add(bdto);
		
		return "redirect:/serchBooks";
	}
}
