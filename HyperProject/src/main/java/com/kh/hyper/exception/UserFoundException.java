package com.kh.hyper.exception;

public class UserFoundException extends RuntimeException { // 실행 중 발생한 예외이므로 런타임익셉션 상속
	
	public UserFoundException(String message) {
		super(message);
	}

}
