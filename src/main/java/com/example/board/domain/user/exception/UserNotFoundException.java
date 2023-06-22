package com.example.board.domain.user.exception;

import com.example.board.global.codes.errorcode.ErrorCode;
import com.example.board.global.exception.BusinessException;

public class UserNotFoundException extends BusinessException {

	private static final ErrorCode ERROR_CODE = ErrorCode.USER_NOT_FOUND;

	public UserNotFoundException(Object[] params) {
		super(ERROR_CODE, params);
	}
}
