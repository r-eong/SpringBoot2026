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
		
		String sql = "INSERT INTO board_table(id, title, content, writer, createdAt) VALUES(?, ?, ?, ?, ?)";
		int result = 0;
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			pstmt.setInt(1, bdto.getId());
			pstmt.setString(2, bdto.getTitle());
			pstmt.setString(3, bdto.getContent());
			pstmt.setString(4, bdto.getWriter());
			pstmt.setString(5, bdto.getCreatedAt());
			
			result = pstmt.executeUpdate();
			
			System.out.println("insertBoard 메소드 - result : " + result);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<BoardDTO> insertBoard() {
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
}
