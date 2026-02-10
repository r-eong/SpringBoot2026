package replyBoard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import replyBoard.dto.ReplyBoardDTO;
import replyBoard.mapper.ReplyBoardMapper;

@Service
public class ReplyBoardServiceImpl implements ReplyBoardService {
	@Autowired
	ReplyBoardMapper replyboardmapper;

	@Override
	public void insertReplyBoard(ReplyBoardDTO rdto) {
		System.out.println("ReplyBoardService - insertReplyBoard 실행");
		
		replyboardmapper.insertReplyBoard(rdto);
	}

	@Override
	public List<ReplyBoardDTO> getAllReplyBoard() {
		System.out.println("ReplyBoardService - getAllReplyBoard 실행");
		
		return replyboardmapper.getAllReplyBoard();
	}

	@Override
	public ReplyBoardDTO getOneBoard(int num) {
		System.out.println("ReplyBoardService - getOneBoard 실행");
		
		return replyboardmapper.getOneBoard(num);
	}

	@Override
	public void reWriteInsert(ReplyBoardDTO rdto) {
		System.out.println("ReplyBoardService - reWriteInsert 실행");
		
		replyboardmapper.reWriteInsert(rdto);
	}

	@Override
	public void reSqUpdate(ReplyBoardDTO rdto) {
		System.out.println("ReplyBoardService - reSqUpdate 실행");
		
		replyboardmapper.reSqUpdate(rdto);
	}

	@Override
	public void replyProcess(ReplyBoardDTO rdto) {
		System.out.println("ReplyBoardService - replyProcess 실행");
		
//		반드시 update 메소드를 먼저 실행해햐 함.
		replyboardmapper.reSqUpdate(rdto);
//		insert 메소드
		replyboardmapper.reWriteInsert(rdto);
	}
}
