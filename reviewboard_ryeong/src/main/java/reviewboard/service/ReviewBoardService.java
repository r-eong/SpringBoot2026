package reviewboard.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import reviewboard.dto.ReviewBoardDTO;

public interface ReviewBoardService {
//	게시글 작성하여 추가하기
	public void insertReviewBoard(ReviewBoardDTO rdto);
	
//	게시글 전체목록 검색 메소드
	public List<ReviewBoardDTO> getAllReviewBoard();
	
//	하나의 게시글을 리턴받는 메소드
	public ReviewBoardDTO getOneBoard(int num);
	
//	별점 - 평균 구하는 메소드
	public int getAverageStars();
	
//	조회수 누적 메소드
	public int updateReviewBoardCount(int num);
	
//	하나의 게시글을 수정하는 메소드
	public int updateReviewBoard(ReviewBoardDTO rdto);
	
//	게시글 작성시 비밀번호 입력 - 삭제시 비밀번호 일치하는지 체크
	public int deleteReviewBoard(@Param("num") int num, @Param("password") String password);
}
