package com.green;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

//DAO : 쿼리문의 집합소. 데이터를 직접 처리하는 객체
//데이터 저장소라는 DAO 객체
@Repository
public class MemberDAO {

//	원래는 DB 커넥션이 존재해야 하지만 현재 실제 DB가 존재하지 않아서
//	HashMap 을 이용해 DB 처럼 사용함.
//	HashMap<String, MemberDTO> 의 keyset
	private Map<String, MemberDTO> memberDB = new HashMap<>();
//	┖> MemberDTO 는 id, pw, email 을 갖고있음!
	
	public void insertMember(MemberDTO mdto) {
		memberDB.put(mdto.getId(), mdto);
		
//		실제 DB가 없어서 HashMap, 프린트문으로 대체함!
//		원래는 select문이나 삭제문, 추가문 같은게 들어감!
		System.out.println("회원 가입을 추가하는 메소드");
		printMember();
	}
	
//	회원정보 출력 메소드
	public void printMember() {
		for(String key : memberDB.keySet()) {
			MemberDTO mdto = memberDB.get(key);
//			┖> id, pw 가 mdto 로 들어감
			System.out.println("id : " + mdto.getId());
			System.out.println("pw : " + mdto.getPw());
		}
	}
	
	public MemberDTO selectMember(MemberDTO mdto) {
		System.out.println("로그인 정보를 확인하는 메소드");
		
//		id 와 pw 를 비교해서 같으면 로그인 성공, 아니면 로그인 실패
		MemberDTO loginMember = memberDB.get(mdto.getId());
//		┖> 예) loginMember = {"apple1234", "111", "apple1234@gmail.com"}
		
		return loginMember;
	}
}
