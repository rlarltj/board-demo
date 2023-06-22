package com.example.board.global.exception;

import com.example.board.global.codes.errorcode.ErrorCode;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

	private final ErrorCode errorCode;
	private final Object[] params;

	public BusinessException(ErrorCode errorCode, Object[] params) {
		this.errorCode = errorCode;
		this.params = params;
	}
}
