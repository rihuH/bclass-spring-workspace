package com.kh.od.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpenDataController {

	@GetMapping("air.do")
	public String airPage() {
		return "air/air";
	}
	
	@GetMapping("horse.do")
	public String horsePage() {
		return "horse/horse";
	}
	
	@GetMapping("sample")
	public String sample() {
		return "sample/sample";
	}
	
	@GetMapping("kin.do")
	public String kinPage() {
		return "naver/kin";
	}
	
	@GetMapping("kakao.do")
	public String mapPage() {
		return "kakao/map";
	}
	
	@GetMapping("busan.do")
	public String busanPage() {
		return "busan/busan";
	}
	
	
	// 공공데이터, 서울시 공공데이터, 네이버 개발자센터-- 네이버는 종료를 잘 시킴
	// 어느 사이트에 가든 먼저 API 소개글 읽고
	
}
