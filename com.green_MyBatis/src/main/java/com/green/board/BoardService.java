package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.board.mapper.BoardMapper;

@Service
public class BoardService {
//	@Autowired
//	BoardDAO boarddao;
	
	@Autowired
	private BoardMapper boardmapper;
	
//	하나의 게시글이 추가되는 메소드를 BoardDAO에서 접근하여 사용
	public void insertBoard(BoardDTO bdto) {
		System.out.println("3. BoardService - insertBoard 실행");
		
		boardmapper.insertBoard(bdto);
	}
	
//	게시글 전체 목록 출력 메소드
	public List<BoardDTO> allBoard() {
		System.out.println("3. BoardService - allBoard 실행");
		
		return boardmapper.getAllBoard();
	}
	
//	하나의 게시글 상세 페이지 출력 메소드
	public BoardDTO getOneBoard(int num) {
		System.out.println("3. BoardService - getOneBoard 실행");
		
//		조회수 증가 메소드 호출
		boardmapper.updateBoardCount(num);
//		게시글 상세 페이지
		return boardmapper.getOneBoard(num);
	}
	
//	하나의 게시글을 수정하는 메소드
	public boolean modifyBoard(BoardDTO bdto) {
		System.out.println("3. BoardService - modifyBoard 실행");
		
		int result = boardmapper.updateBoard(bdto);
		
		if(result > 0) {
			System.out.println("게시글 수정 성공");
			return true;
		}else {
			System.out.println("게시글 수정 실패");
			return false;
		}
	}
	
//	---------------------------------- 2026-01-29 ----------------------------------
	
//	게시글 하나를 삭제하는 메소드
	public boolean removeBoard(int num, String writerPw) {
		System.out.println("3. BoardService - removeBoard 실행");
		
//		DAO에서 받아오는 deleteBoard 는 삭제도면 1, 아니면 0
		int result = boardmapper.deleteBoard(num, writerPw);
		
		if(result > 0) {
			System.out.println("삭제 성공");
			return true;
		}else {
			System.out.println("삭제 실패 - 비밀번호 불일치");
			return false;
		}
	}
	
//	게시물 검색하는 메소드
	public List<BoardDTO> shearchBoard(String searchKeyWord, String searchType) {
		System.out.println("3. BoardService - shearchBoard 실행");
		System.out.println("3. shearchBoard - searchType : " + searchType);
		System.out.println("3. shearchBoard - searchKeyWord : " + searchKeyWord);
		
		return boardmapper.getShearchBoard(searchKeyWord, searchType);
	}
}
