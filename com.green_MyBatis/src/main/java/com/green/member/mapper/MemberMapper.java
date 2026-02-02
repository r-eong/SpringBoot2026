package com.green.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.member.MemberDTO;

//MemberMapper 는 Mapper(=연결 : SQL(.xml 파일)) 역할을 한다
@Mapper
public interface MemberMapper {
//	MemberDAO 의 메소드를 추상메소드로 작성한다
//	설정된 객체는 IoC 컨테이너에 탑재된다
	
//	회원가입 - 회원을 추가하는 추상메소드
	public int insertMember(MemberDTO mdto);
	
//	id 중복체크
//	중복된 아이디로 가입시도하면 회원가입 실패 출력
	public boolean isMember(String id);
	
//	회원 전체 목록 검색
	public List<MemberDTO> allSelectMember();
	
//	유저 1명의 정보
	public MemberDTO oneSelectMember(String id);
	
//	유저 1명의 정보를 수정하는 쿼리
	public int updateMember(MemberDTO mdto);
	
//	유저 1명의 비밀번호 리턴하는 쿼리
	public String getPass(String id);
	
//	유저 1명 삭제 메소드
	public int delMember(String id);
}
