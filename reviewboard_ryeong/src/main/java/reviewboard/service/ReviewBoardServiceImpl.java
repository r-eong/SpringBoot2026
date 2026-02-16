package reviewboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reviewboard.dto.ReviewBoardDTO;
import reviewboard.mapper.ReviewBoardMapper;

@Service
public class ReviewBoardServiceImpl implements ReviewBoardService {
	@Autowired
	ReviewBoardMapper reviewboardmapper;

	@Override
	public void insertReviewBoard(ReviewBoardDTO rdto) {
		System.out.println("ReviewBoardServiceImpl - insertReplyBoard 실행");
		
		reviewboardmapper.insertReviewBoard(rdto);
	}

	@Override
	public List<ReviewBoardDTO> getAllReviewBoard() {
		System.out.println("ReviewBoardServiceImpl - getAllReviewBoard 실행");
		
		return reviewboardmapper.getAllReviewBoard();
	}

	@Override
	public ReviewBoardDTO getOneBoard(int num) {
		System.out.println("ReviewBoardServiceImpl - getOneBoard 실행");
		
		return reviewboardmapper.getOneBoard(num);
	}

	@Override
	public int getAverageStars() {
		System.out.println("ReviewBoardServiceImpl - getAverageStars 실행");
		
		return reviewboardmapper.getAverageStars();
	}

	@Override
	public int updateReviewBoardCount(int num) {
		System.out.println("ReviewBoardServiceImpl - updateReviewBoardCount 실행");
		
		return reviewboardmapper.updateReviewBoardCount(num);
	}

	@Override
	public int updateReviewBoard(ReviewBoardDTO rdto) {
		System.out.println("ReviewBoardServiceImpl - updateReviewBoard 실행");
		
		return reviewboardmapper.updateReviewBoard(rdto);
	}

	@Override
	public int deleteReviewBoard(int num, String password) {
		System.out.println("ReviewBoardServiceImpl - deleteReviewBoard 실행");
		
		return reviewboardmapper.deleteReviewBoard(num, password);
	}
}
