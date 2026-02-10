package replyBoard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import replyBoard.dto.ReplyBoardDTO;

@Mapper
public interface ReplyBoardMapper {
//	각종 SQL 을 위한 메소드
//	인터페이스틑 추상메소드만 작성
	
//	게시글 작성하여 추가하기
	public void insertReplyBoard(ReplyBoardDTO rdto);
	
//	게시글 전체목록 검색 메소드
	public List<ReplyBoardDTO> getAllReplyBoard();
	
//	하나의 게시글을 리턴받는 메소드
	public ReplyBoardDTO getOneBoard(int num);
	
//	답글 작성하여 추가하는 메소드
	public void reWriteInsert(ReplyBoardDTO rdto);
	
//	답글 작성시 부모글의 re_level 보다 큰 값들을 모두 1씩 증가시키는 메소드
//	원글 : ref = 1, re_step = 1, re_level = 1
//	원글에 답글을 달 경우 ref = 1, re_step = 2, re_level = 2
	public void reSqUpdate(ReplyBoardDTO rdto);
//	Sq = 시퀀스
}
