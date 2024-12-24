package com.kh.hyper.board.model.dao;

public class BoardNotFoundException extends RuntimeException{

	public BoardNotFoundException(String message) {
		super(message);
	}
}
