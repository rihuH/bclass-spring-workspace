package com.kh.hyper.exception.controller;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hyper.board.model.dao.BoardNotFoundException;
import com.kh.hyper.exception.BoardNoValueException;
import com.kh.hyper.exception.ComparePasswordException;
import com.kh.hyper.exception.FailToFileUploadException;
import com.kh.hyper.exception.InvalidParameterException;
import com.kh.hyper.exception.TooLargeValueException;
import com.kh.hyper.exception.UserFoundException;
import com.kh.hyper.exception.UserIdNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice 
public class ExceptionHandlingController {
	
	private ModelAndView createErrorResponse(String errorMsg, Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", errorMsg);
		log.info("발생예외 : {}", e.getMessage(), e); // e는 콘솔에 나옴
		return mv; // 공통된 코드를 분리한 메소드
	}
	
	@ExceptionHandler(DuplicateKeyException.class)
	protected ModelAndView HandleTransactionError(DuplicateKeyException e) {
		// 한 번이라도 경험한 에러는 기록하느 것이 좋음
		/*ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", "잘못된 요청입니다").setViewName("common/error_page");
		return mv;*/
		return createErrorResponse("잘못된 요청입니다.", e);
	}
	
	@ExceptionHandler(UserIdNotFoundException.class) // 어떤 예외가 발생했을 때 일을 할 것인지 예외클래스명을 기술해줘야함
	protected ModelAndView NoSuchUserIdError(UserIdNotFoundException e) {
		/*ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", "아이디가 존재하지 않습니다").setViewName("common/error_page");
		log.info("발생한 에외 : {}", e.getMessage(), e);
		return mv;*/
		return createErrorResponse("아이디가 존재하지 않습니다.", e);
	}
	
	@ExceptionHandler(ComparePasswordException.class)
	protected ModelAndView NotMatchingPasswordError(ComparePasswordException e) {
		/*ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", "비밀번호가 일치하지 않습니다").setViewName("common/error_page");
		return mv;*/
		return createErrorResponse("비밀번호가 일치하지 않습니다", e);
	}
	
	@ExceptionHandler(UserFoundException.class)
	protected ModelAndView userExistError(UserFoundException e) {
		/*ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", "이미 존재하는 아이디입니다.").setViewName("common/error_page");
		return mv;*/
		return createErrorResponse("이미 존재하는 아이디입니다.", e);
	}
	
	@ExceptionHandler(TooLargeValueException.class)
	protected ModelAndView tooLargeValueException(TooLargeValueException e) {
		/*ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", "유효하지 않은 값을 입력하셨습니다.").setViewName("common/error_page");
		return mv;*/
		return createErrorResponse("유효하지 않은 값을 입력하셨습니다.", e);
	}
	
	@ExceptionHandler(BoardNotFoundException.class)
	protected ModelAndView NosearchBoardError(BoardNotFoundException e) {
		return createErrorResponse("게시글 없음.", e);
	}
	
	@ExceptionHandler(BoardNoValueException.class)
	protected ModelAndView noValueError(BoardNoValueException e) {
		return createErrorResponse("필수 입력사항을 모두 입력해주세요", e);
	}
	
	@ExceptionHandler(FailToFileUploadException.class)
	protected ModelAndView failToFileUpload(FailToFileUploadException e) {
		return createErrorResponse("파일 업로드에 실패하였습니다", e);
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	protected ModelAndView invalidParameterException(InvalidParameterException e) {
		return createErrorResponse("올바른 요청이 아님", e);
	}
	

}
