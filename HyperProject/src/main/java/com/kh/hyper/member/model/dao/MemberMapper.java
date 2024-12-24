package com.kh.hyper.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.hyper.member.model.vo.Member;

@Mapper
public interface MemberMapper {

	/*
	 * Mapper 애노테이션 구현
	 * 
	 * 
	 * 마이바티스에서 이 클래스를 찾을 수 있도록 rootcontext에서 작업해주ㅜ어야 함- 이동
	 */
	
	Member login(Member member);
	
	int join(Member member);
	
	int updateMember(Member member);
	
	void delete(Member member);
	
	int test();

	int checkId(String userId);
}
