package com.green.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.book.dao.BookDAO;
import com.green.book.dto.BookDTO;

@Service
public class BookService {
	@Autowired
	BookDAO bookdao;
	
	public void bookCheck(BookDTO bdto) {
		bookdao.chkBook(bdto);
		
		BookDTO blankInput = bookdao.checkBook(bdto);
		System.out.println("대여 도서 정보 출력완료");
		
//		예외처리
		if(blankInput == null) {
			System.out.println("대여실패! 빈칸임!");  // 여기서 안 넘어가져야하는데 넘어가짐!
		}
	}
	
//	보유 도서 목록 콘솔에 출력
	public void bookList() {
		bookdao.printBookList();
	}
	
//	관리자 도서 추가 
	public void addBooks(String addBook) {
		bookdao.addBooks(addBook);
	}
}
