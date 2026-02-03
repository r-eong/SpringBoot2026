package com.green.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.member.MemberDTO;

@Mapper
public interface MemberMapper {
//	회원가입 - 회원 추가 메소드
	public int addMember(MemberDTO mdto);
	
//	회원가입 - 아이디 중복체크
	public boolean isMember(String id);
	
//	관리자 - 회원 전체목록
	public List<MemberDTO> allMember();
	
//	유저 1명의 상세 정보
	public MemberDTO oneMember(String id);
	
//	유저 1명의 정보 수정
	public int updateMember(MemberDTO mdto);
	
//	유저 1명의 비밀번호 리턴
	public String getPw(String id);
	
//	유저 1명 삭제
	public int delMember(String id);
}
