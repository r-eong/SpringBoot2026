package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
	@Autowired
	BoardDAO boarddao;
	
	public final static int form_title_success = 0;  // 게시 성공
	public final static int form_title_fail = 1;  // 게시 실패
	public final static int form_title_exit = -1;  // 게시 실패
	
//	게시글 저장 목록
	public int boardAddForm(BoardDTO bdto) {
		System.out.println("BoardService - boardAddForm 메소드 - 게시판 입력 실행");
		
//		제목 중복체크
		boolean isTitle = boarddao.isTitle(bdto.getTitle());
		
//		중복체크
		if(isTitle == false) {
			int result = boarddao.insertBoard(bdto);
			
			if(result > 0) {  // 성공
				return form_title_success;  // 0
			}else {  // 실패
				return form_title_fail;  // 1
			}
		}else {  // 중복된 제목 존재
			return form_title_exit;  // -1
		}
	}
	
//	게시판 전체출력 메소드
	public List<BoardDTO> printBoard() {
		System.out.println("BoardService - printBoard 메소드 - 게시판 전체출력 실행");
		
//		int result = boarddao.insertBoard(bdto);
		return boarddao.allSelectBoard();
	}
	
//	게시물 상세 페이지
	public BoardDTO oneBoardDe(int id) {
		System.out.println("BoardService - oneBoardDe 메소드 - 게시판 전체출력 실행");
		
		return boarddao.oneBoard(id);
	}
	
//	게시물 수정
	public boolean modBoard(BoardDTO bdto) {
		System.out.println("BoardService - modBoard 메소드 - 게시물 수정 실행");
		
		return boarddao.updateBoard(bdto) == 1;
	}
	
//	게시물 삭제
	public boolean delOneBoard(int id) {
		System.out.println("BoardService - delOneBoard 메소드 - 게시물 삭제 실행");
		
		return boarddao.delBoard(id) == 1;
	}
	
//	게시물 검색 메소드
	public List<BoardDTO> SearchBoard(String shearchKeyWord, String searchType) {
		System.out.println("BoardService - SearchBoard 메소드 - 게시물 검색 실행");
		System.out.println("SearchBoard - shearchKeyWord : " + shearchKeyWord);
		System.out.println("SearchBoard - searchType : " + searchType);
		
		return boarddao.getSearch(shearchKeyWord, searchType);
	}
}
