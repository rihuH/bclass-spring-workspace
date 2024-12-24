package com.kh.hyper.ajax.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.hyper.board.model.service.BoardService;
import com.kh.hyper.board.model.vo.Board;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AjaxController {
	
	@Autowired
	private BoardService service;
	
	/*
	 * 스프링 배우기 전에 원래
	 * 1. HttpServletResponse 객체로 응답했었음.
	 *  
	 *  
	 *  
	 *  구닥다리 방식
	 * 
	@GetMapping("ajax1.do")
	public void ajaxMethod1(HttpServletResponse response, String userId) throws IOException {
//		log.info(userId);
		// 요청처리 다 했다고 침
		// 요청할 페이지에 데이터를 반환해줘야함!
		// 있어요!
		 * 
		 * 꼭 해줘야 하는 것!!!!
		 * 응 답 형 식 지 정
		response.setContentType("text/html; charset=UTF-8");
		// 출력
		response.getWriter().print("있어요!");
		
	}
	*/
	
	
	/*
	 * 2. 응답할 데이터를 문자열로 반환
	 * 
	 * => HttpServletResponse를 사용하지 않는 방식
	 * => 반환타입을 String
	 * => ModelAndView의 viewName필드에 들어감
	 * => DispatcherServlet으로 들어가서
	 * => ViewResolver를 찾음. 그런게 없으니까 404로 뜸.
	 * 
	 * 우리는 jsp로 돌리는게 아니고 String타입의 값이 뷰의 정보가 아닌 응답데이터라고 해줘야함
	 * MessageConverter로 이동하게 해야함!!
	 * 
	 * @ResponseBody 애노테이션을 써야함
	 * 
	 * 있어요!! 라는 한글도 인식할 수 있또록 produces를 적어줘야함
	 * 
	 */
	@ResponseBody
	@GetMapping(value="ajax1.do", produces="text/html; charset=UTF-8")
	public String ajaxMethod1(String userId) {
		return userId + " 있어요!";
	}
	
	@ResponseBody
	@GetMapping(value="ajax2.do", produces="application/json; charset=UTF-8")
	public Board ajaxMethod2(Long no) {
		Map<String, Object> response = service.selectById(no);
		log.info("조회된 게시글 정보 : {}", response.get("board"));
		return (Board)response.get("board");
	}
	
}
