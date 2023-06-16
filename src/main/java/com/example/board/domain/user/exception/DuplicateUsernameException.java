package com.example.board.domain.user.exception;


public class DuplicateUsernameException extends RuntimeException {

	public DuplicateUsernameException() {
		super();
	}

	public DuplicateUsernameException(String message) {
		super(message);
	}
}
