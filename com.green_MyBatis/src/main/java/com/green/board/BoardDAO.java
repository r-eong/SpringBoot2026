package com.green.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	@Autowired
	private DataSource datasource;
//	┖> application.properties 설정된 환경의 MySQL 과 연결한다하는 의미
	
//	DAO에서 쿼리문 작성시 keypoint
//	1. 한가지에 관련된 자료를 insert 하거나 select
//		┖> 데이터 타입은 DTO
//	2. 전체에 관련된 여러개에 해당하는 자료
//		┖> List<E> list = new ArrayList<E> 로 업캐스팅
//	3. 예를들어 필드명 하나 select
//		┖> String, int boolean
//	4. 메소드 작성시 void 는 return 이 필요없음
//	5. 메소드 작성시 데이터 타입(=void 자리)이 존재하면 반환값(=return)이 필요하다
//	6. try(){}catch(){} 구문 사용
	
//	하나의 게시문을 작성하여 DB에 추가하는 쿼리문
	public void insertBoard(BoardDTO bdto) {
		System.out.println("2. BoardDAO - insertBoard 실행");
		
//		추가하는 쿼리문 insert into 테이블명() values()
		String sql = "INSERT INTO board(writer, subject, writerPw, content) VALUES(?, ?, ?, ?)";
		
		try(Connection conn = datasource.getConnection();
//			┖> datasource 에 주입한 원본의 자료 연결해라.
//			conn = (url, username, userPassWord)
//			conn = (localhost:3306, "root", "12345678")
//			위를 통해서 PreparedStatement에 넣음!
			PreparedStatement pstmt = conn.prepareStatement(sql);
				){
//			1. 물음표 대응 - 무조건 String sql 에서 작성한 순서대로 1부터 작성
//			예) input -> writer : "길동" 입력 -> pstmt.setString(1, "길동") ... input 박스 전체 입력
//				->  INSERT INTO board(writer, subject, writerPw, content) VALUES("길동", "제목", 비밀번호, "글내용")
			pstmt.setString(1, bdto.getWriter());
			pstmt.setString(2, bdto.getSubject());
			pstmt.setString(3, bdto.getWriterPw());
			pstmt.setString(4, bdto.getContent());
			
//			sql문 실행 
//			insert, delete, updata : excuteUpdate
//			select : executeQuery
//			select 문은 반드시 Result 객체에 담아서 출력
			pstmt.executeUpdate();

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	전체 게시글 목록 출력
	public List<BoardDTO> getAllBoard(){
		System.out.println("2. BoardDAO - getAllBoard 실행");
		
//		List<> 인스턴스화
		List<BoardDTO> boardList = new ArrayList<BoardDTO>();
		
		String sql = "SELECT * FROM board ORDER BY num DESC";
		
		try(Connection conn = datasource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
				){
//			물음표 대응할게 없음!
//			select 문은 ResultSet에 담음
			ResultSet rs = pstmt.executeQuery();
			
//			rs.next() 다음 행이 존재하면 true, 아니면 false
//			rs.next() 를 안 쓰면 무조건 빈 DTO가 생성됨
			while(rs.next()) {
				BoardDTO bdto = new BoardDTO();
				
				bdto.setNum(rs.getInt("num"));
				bdto.setWriter(rs.getString("writer"));
				bdto.setSubject(rs.getString("subject"));
				bdto.setWriterPw(rs.getString("writerPw"));
				bdto.setReg_date(rs.getString("reg_date"));
				bdto.setReadcount(rs.getInt("readcount"));
				bdto.setContent(rs.getString("content"));
				
				boardList.add(bdto);
			}
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		
		return boardList;
	}
	
//	하나의 게시글 상세 페이지
//	readcount 누적도 함께
	public BoardDTO getOneBoard(int num) {
		System.out.println("2. BoardDAO - getOneBoard 실행");
		
		BoardDTO bdto = new BoardDTO();
		
//		readcount(조회수) 클랙할 때마다 1씩 누적되는 sql문 작성
		String sql = "UPDATE board SET readcount = readcount + 1 WHERE num=?";
		String sql2 = "SELECT * FROM board WHERE num=?";
		
//		Connection 연결용
//		하나의 게시글 정보 가져오는용 - sql2
		try(Connection conn = datasource.getConnection()){
//		조회수 증가용 - sql
			try(PreparedStatement pstmt = conn.prepareStatement(sql)){
//				물음표 대응
				pstmt.setInt(1, num);
				pstmt.executeUpdate();
			}
			
//			하나의 게시글 정보 가져오기
			try(PreparedStatement pstmt = conn.prepareStatement(sql2)){
//				물음표 대응
				pstmt.setInt(1, num);
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) {
					bdto.setNum(rs.getInt("num"));
					bdto.setWriter(rs.getString("writer"));
					bdto.setSubject(rs.getString("subject"));
					bdto.setWriterPw(rs.getString("writerPw"));
					bdto.setReg_date(rs.getString("reg_date"));
					bdto.setReadcount(rs.getInt("readcount"));
					bdto.setContent(rs.getString("content"));
				}
			}
		}catch (Exception e) {
			 e.printStackTrace();
		}
		
		return bdto;
	}
	
//	하나의 게시글을 수정하는 메소드
	public int updateBoard(BoardDTO bdto) {
		System.out.println("2. BoardDAO - updateBoard 실행");
		
		int result = 0;  // 수정되면 1, 아니면 0
		
//		수정할 때 반드시 번호와 비밀번호가 일치해야 수정가능
		String sql = "UPDATE board SET subject=?, content=? WHERE num=? AND writerPw=?";
		
		try(Connection conn = datasource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
				){
//			물음표대응
			pstmt.setString(1, bdto.getSubject());
			pstmt.setString(2, bdto.getContent());
			pstmt.setInt(3, bdto.getNum());
			pstmt.setString(4, bdto.getWriterPw());
			
			result = pstmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
//	---------------------------------- 2026-01-29 ----------------------------------
	
//	게시글 작성시 비밀번호 입력 - 삭제시 비밀번호 일치하는지 체크
	public int deleteBoard(int num, String writerPw) {
		System.out.println("2. BoardDAO - deleteBoard 실행");
		
		int result = 0;
		
		String sql = "DELETE FROM board WHERE num=? AND writerPw=?";
		
		try(Connection conn = datasource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);){
//			물음표 대응
			pstmt.setInt(1, num);
			pstmt.setString(2, writerPw);
			
			result = pstmt.executeUpdate();
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		
		return result;
	}
	
//	내용 또는 제목으로 검색하는 메소드 
//	검색 메소드 반드시 searchType, shearchKeyword 매개변수 필요
	public List<BoardDTO> getShearchBoard(String searchKeyWord, String searchType) {
		System.out.println("2. BoardDAO - shearchBoard 실행");
		
		List<BoardDTO> bList = new ArrayList<BoardDTO>(); 
		
		String sql = "";
		
//		제목 검색
		if("subject".equals(searchType)) {
//			입력한 문자를 포함하는 검색 명령어
//			select 필드명 from 테이블명 where 검색 필드명 like %키워드%
			sql = "SELECT * FROM board WHERE subject LIKE ? ORDER BY num DESC";
			
//		내용 검색
		}else {
			sql = "SELECT * FROM board WHERE content LIKE ? ORDER BY num DESC";
		}
		
		try(Connection conn = datasource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);){
//			물음표 대응
			pstmt.setString(1, "%" + searchKeyWord + "%");
//			┖> select - 제목 
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO bdto = new BoardDTO();
				
				bdto.setNum(rs.getInt("num"));
				bdto.setWriter(rs.getString("writer"));
				bdto.setSubject(rs.getString("subject"));
				bdto.setWriterPw(rs.getString("writerPw"));
				bdto.setReg_date(rs.getString("reg_date"));
				bdto.setReadcount(rs.getInt("readcount"));
				bdto.setContent(rs.getString("content"));
				
//				List에 추가
				bList.add(bdto);
			}
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		
		return bList;
	}
}
