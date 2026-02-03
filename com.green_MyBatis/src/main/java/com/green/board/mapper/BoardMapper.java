package com.green.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.green.board.BoardDTO;

@Mapper
public interface BoardMapper {
//	하나의 게시문을 작성하여 DB에 추가하는 쿼리문
	public void insertBoard(BoardDTO bdto);
	
//	전체 게시글 목록 출력
	public List<BoardDTO> getAllBoard();
	
//	하나의 게시글 상세 페이지
//	readcount 누적도 함께
//	public BoardDTO getOneBoard(int num);
	public BoardDTO getOneBoard(int num);
//	조회수 누적 메소드
	public int updateBoardCount(int num);
	
//	하나의 게시글을 수정하는 메소드
	public int updateBoard(BoardDTO bdto);
	
//	게시글 작성시 비밀번호 입력 - 삭제시 비밀번호 일치하는지 체크
//	매개변수가 2개 이상인 경우 @Param 사용 - @Param("변수") 데이터타입 필드명
//	@Param 을 사용하는 경우 mapper.xml 에서 parameterType 을 적지 않아도 됨!
	public int deleteBoard(@Param("num") int num, @Param("writerPw") String writerPw);
	
//	내용 또는 제목으로 검색하는 메소드 
//	검색 메소드 반드시 searchType, shearchKeyword 매개변수 필요
	public List<BoardDTO> getShearchBoard(@Param("searchKeyWord") String searchKeyWord, 
			@Param("searchType") String searchType);
	
//	전체 게시글의 개수를 구하는 메소드
	public int getAllCount();
	
//	전체 게시글의 시작 startRow, 몇 개의 행인지 pageSize 만큼 보는/출력하는 메소드
	public List<BoardDTO> getPageList(@Param("startRow") int startRow, @Param("pageSize") int pageSize);
}
