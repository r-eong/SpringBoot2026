package questBoard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import questBoard.dto.QuestBoardDTO;

@Mapper
public interface QuestBoardMapper {
//	게시글 작성, 추가
	public void insertQuestBoard(QuestBoardDTO qdto);
	
//	전체 게시글
	public List<QuestBoardDTO> getAllQuestBoard();
	
//	하나의 게시글 리턴
	public QuestBoardDTO getOneQuestBoard(int num);
	
//	답글 작성, 추가
	public void reWriterInsert(QuestBoardDTO qdto);
	
//	re_level 증가
//	public void reSqUpdate(QuestBoardDTO qdto);
}
