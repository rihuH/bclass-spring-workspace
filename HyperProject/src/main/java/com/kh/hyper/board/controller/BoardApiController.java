package com.kh.hyper.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.hyper.board.model.service.BoardService;
import com.kh.hyper.board.model.vo.Reply;
import com.kh.hyper.common.model.vo.ResponseData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// AJAX 시즌2
@RestController // 컨트롤러인데 전부 값만 돌려줄 것. ajax요청을 담당할 컨트롤러.
// Rest는 representation state??? transfer????
@RequestMapping("reply")
@RequiredArgsConstructor
@Slf4j
public class BoardApiController {
	
	private final BoardService boardService;
	
	@PostMapping
	public ResponseEntity<ResponseData> ajaxInsertReply(Reply reply) {
		
//		ResponseEntity responseData; 응답용 객체를 만들어서 돌려줄 것
		int result = boardService.insertReply(reply);
		ResponseData response = ResponseData.builder().message("댓글 등록에 성공했습니다!").status(HttpStatus.OK.toString()).data(result).build();
		return new ResponseEntity<ResponseData>(response, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<ResponseData> ajaxSelectReply(Long boardNo){
		List<Reply> replies = boardService.selectReplyList(boardNo);
		ResponseData response = ResponseData.builder().message("댓글 조회에 성공했습니다!").status(HttpStatus.OK.toString()).data(replies).build();
		return new ResponseEntity<ResponseData>(response, HttpStatus.OK);
	}
	
	// 앱, 핸드폰, 태블릿, pc, 게임 모두에서 사용할 수 있어야해서 적합한게 json방식으로 보내주기 최근의 트렌드
	// 자바스크립트는 다 쓸 수 있으니까
	
	

}
