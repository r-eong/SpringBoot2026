package com.green.book.dto;

public class BookDTO {
//	멤버변수
	private String bookName;  // 도서명
	private String writer;  // 저자
	private int isbn;  // isbn
	private String userName;  // 대여자명
	private String rentDate;  // 대여일
	
//	관리자 책 추가
	private String addBook;
	
	public String getAddBook() {
		return addBook;
	}
	public void setAddBook(String addBook) {
		this.addBook = addBook;
	}
	public String getRentDate() {
		return rentDate;
	}
	public void setRentDate(String rentDate) {
		this.rentDate = rentDate;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public int getIsbn() {
		return isbn;
	}
	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
