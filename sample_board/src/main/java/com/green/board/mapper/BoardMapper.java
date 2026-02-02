package com.green.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.green.board.BoardDTO;

@Mapper
public interface BoardMapper {
//	게시물 추가 쿼리
	public int insertBoard(BoardDTO bdto);
	
//	전체 게시글 출력
	public List<BoardDTO> allSelectBoard();
	
//	public boolean isTitle(String title);
	
//	게시물 상세 페이지
	public BoardDTO oneBoard(int id);
	
//	상세페이지 - 수정하기
	public int updateBoard(BoardDTO bdto);
	
//	상세 삭제하기
	public int delBoard(int id);
	
//	내용, 제목 으로 검색하는 메소드
	public List<BoardDTO> getSearch(@Param("shearchKeyWord") String shearchKeyWord, 
			@Param("searchType") String searchType);
}
