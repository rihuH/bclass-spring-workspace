package com.kh.hyper.member.model.service;

import javax.servlet.http.HttpSession;

import com.kh.hyper.member.model.vo.Member;

public interface MemberService {
	
	// 로그인
	Member login(Member member);
	/*
	// 회원가입
	int join(Member member);
	
	// 회원정보수정
	int updateMember(Member member);
	
	// 회원 탈퇴
	// 원본 int delete(Member member); 버전1
	int delete(String userPwd, HttpSession session); // 버전2
	버전1 주석처리*/
	
	void join(Member member);
	void updateMember(Member member, HttpSession session);
	void delete(Member member);

	String checkId(String userId);
	

	
	//------1절
	
	
	// ----스프링에서 ajax
	
	
	// 아이디 중복체크
	
	
	
	//-----2절
	
	
	// 이메일 인증
	
	
	//--3절

}
