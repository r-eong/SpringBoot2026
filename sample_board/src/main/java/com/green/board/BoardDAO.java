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
	private DataSource dataSource;
	
	public int insertBoard(BoardDTO bdto) {
		System.out.println("BoardDAO - insertBoard 메소드 실행");
		
		String sql = "INSERT INTO board_table(title, content, writer) VALUES(?, ?, ?)";
		int result = 0;
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
//			pstmt.setInt(1, bdto.getId());
			pstmt.setString(1, bdto.getTitle());
			pstmt.setString(2, bdto.getContent());
			pstmt.setString(3, bdto.getWriter());
			
			result = pstmt.executeUpdate();
			
			System.out.println("insertBoard 메소드 - result : " + result);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<BoardDTO> allSelectBoard() {
		System.out.println("BoardDAO - insertBoard 메소드 실행");
		
		String sql = "SELECT * FROM board_table";
		
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				){
			while(rs.next()) {
				BoardDTO bdto = new BoardDTO();
				
				bdto.setId(rs.getInt("id"));
				bdto.setTitle(rs.getString("title"));
				bdto.setContent(rs.getString("content"));
				bdto.setWriter(rs.getString("writer"));
				bdto.setCreatedAt(rs.getString("createdAt"));
				
				list.add(bdto);
			}
			rs.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public boolean isTitle(String title) {
		System.out.println("BoardDAO - isTitle 메소드 실행");
		return false;
	}
	
	public BoardDTO oneBoard(int id) {
		System.out.println("BoardDAO - oneBoard 메소드 - 게시물 상세 페이지 실행");
		
		BoardDTO bdto = new BoardDTO();
		
		String sql = "SELECT * FROM board_table WHERE id=?";
		
		try(Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bdto.setId(rs.getInt("id"));
				bdto.setTitle(rs.getString("title"));
				bdto.setContent(rs.getString("content"));
				bdto.setWriter(rs.getString("writer"));
				bdto.setCreatedAt(rs.getString("createdAt"));
			}
		}catch (Exception e) {
			 e.printStackTrace();
		}
		
		return bdto;
	}
	
//	상세페이지 - 수정하기
	public int updateBoard(BoardDTO bdto) {
		System.out.println("BoardDAO - updateBoard 메소드 - 게시물 수정 실행");
		
		int result = 0;
		String sql = "UPDATE board_table SET title=?, content=? WHERE id=?";
		
		try(Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			pstmt.setString(1, bdto.getTitle());
			pstmt.setString(2, bdto.getContent());
			pstmt.setInt(3, bdto.getId());
			
			result = pstmt.executeUpdate();
			
			System.out.println("updateBoard : " + result);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
//	상세 삭제하기
	public int delBoard(int id) {
		System.out.println("BoardDAO - delBoard 메소드 - 게시물 삭제 실행");
		
		int result = 0;
		String sql = "DELETE FROM board_table WHERE id=?";
		
		try(Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			pstmt.setInt(1, id);
			
			result = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
//	내용, 제목 으로 검색하는 메소드
	public List<BoardDTO> getSearch(String shearchKeyWord, String searchType){
		System.out.println("BoardDAO - getSearch 메소드 - 게시물 검색 실행");
		
		List<BoardDTO> boardList = new ArrayList<BoardDTO>();
		
		String sql = "";
		
		if("title".equals(searchType)) {  // 제목으로 검색 sql
			sql = "SELECT * FROM board_table WHERE title LIKE ? ORDER BY id DESC";
		}else {  // 내용으로 검색  sql
			sql = "SELECT * FROM board_table WHERE content LIKE ? ORDER BY id DESC";
		}
		
		try(Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);){
//			물음표 대응
			pstmt.setString(1, "%" + shearchKeyWord + "%");
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO bdto = new BoardDTO();
				
				bdto.setId(rs.getInt("id"));
				bdto.setTitle(rs.getString("title"));
				bdto.setContent(rs.getString("content"));
				bdto.setWriter(rs.getString("writer"));
				bdto.setCreatedAt(rs.getString("createdAt"));
				
				boardList.add(bdto);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return boardList;
	}
}
