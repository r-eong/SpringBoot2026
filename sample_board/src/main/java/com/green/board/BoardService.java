package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
	@Autowired
	BoardDAO boarddao;
	
//	게시판 전체출력 메소드
	public List<BoardDTO> boardinsert() {
		System.out.println("BoardService - boardinsert 메소드 - 게시판 전체출력 실행");
		
//		int result = boarddao.insertBoard(bdto);
		return boarddao.insertBoard();
	}
}
