package questBoard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import questBoard.dto.QuestBoardDTO;
import questBoard.mapper.QuestBoardMapper;

@Service
public class QuestBoardServiceImpl implements QuestBoardService {
	@Autowired
	QuestBoardMapper questboardmapper;

	@Override
	public void insertQuestBoard(QuestBoardDTO qdto) {
		System.out.println("QuestBoardServiceImpl - insertQuestBoard 실행");
		
		questboardmapper.insertQuestBoard(qdto);
	}

	@Override
	public List<QuestBoardDTO> getAllQuestBoard() {
		System.out.println("QuestBoardServiceImpl - getAllQuestBoard 실행");
		
		return questboardmapper.getAllQuestBoard();
	}

	@Override
	public QuestBoardDTO getOneQuestBoard(int num) {
		System.out.println("QuestBoardServiceImpl - getAllQuestBoard 실행");
		
		return questboardmapper.getOneQuestBoard(num);
	}

	@Override
	public void reWriterInsert(QuestBoardDTO qdto) {
		System.out.println("QuestBoardServiceImpl - reWriterInsert 실행");
		
		questboardmapper.reWriterInsert(qdto);
	}

//	@Override
//	public void reSqUpdate(QuestBoardDTO qdto) {
//		System.out.println("QuestBoardServiceImpl - reSqUpdate 실행");
//		
//		questboardmapper.reSqUpdate(qdto);
//	}
}
