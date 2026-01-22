package com.green.book.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.green.book.dto.BookDTO;

@Repository
public class BookDAO {

//	가짜 DB
	private Map<String, BookDTO> bookDb = new HashMap<>();
	
//	책 목록 배열
	ArrayList<String> books = new ArrayList<String>();
	
	public BookDAO() {
		bookList();
	}
	
//	콘솔 출력 메소드
	public void printInfo() {
		for(String key : bookDb.keySet()) {
			BookDTO bdto = bookDb.get(key);
			System.out.println("도서명 : " + bdto.getBookName());
			System.out.println("저자 : " + bdto.getWriter());
			System.out.println("ISBN : " + bdto.getIsbn());
			System.out.println("대여자명 : " + bdto.getUserName());
			System.out.println("대여일 : " + bdto.getRentDate());
			System.out.println("반납일 : " + bdto.getRentDate());
			System.out.println("-------------------------------");
		}
	}
	
//	input 입력값 가짜DB에 추가 + 추가된 목록 콘솔에 출력
	public void chkBook(BookDTO bdto) {
		bookDb.put(bdto.getBookName(), bdto);
		
		System.out.println("대여 목록에 추가하는 메소드");
		System.out.println("===============");
		printInfo();  // 콘솔에 출력
	}
	
//	input 비어있는지 확인
	public BookDTO checkBook(BookDTO bdto){
		BookDTO blankInput = bookDb.get(bdto.getBookName());
		
		System.out.println("책 정보 확인 메소드 실행");
		
		return blankInput;
	}

	
//	기존 책 목록
	public void bookList() {
		books.add("해리포터");
		books.add("퍼시잭슨과 번개도둑");
		books.add("트와일라잇");
	}
	
//	관리자 책 추가 메소드
	public void addBooks(String addBook) {
		books.add(addBook);
	}
	
//	도서 보유 목록 출력
	public void printBookList() {
		System.out.println("------------ 현재 보유 도서목록 ------------");
		
		for(int i = 0; i < books.size(); i++) {
			System.out.print(books.get(i) + "    ");
		}
		System.out.println();
		System.out.println("---------------------------------------");
	}
	
//	대여 도서 검색 메소드
	public void serchBooks(String serchBook) {
		BookDTO blankInput = bookDb.get(serchBook.get());
		
		if(bookDb.get(serchBook).equals(serchBook))
		
		System.out.println("검색 메소드 실행완료");
	}
}
