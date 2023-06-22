package com.example.board.domain.user.exception;

import com.example.board.global.codes.errorcode.ErrorCode;
import com.example.board.global.exception.BusinessException;

public class DuplicateUsernameException extends BusinessException {

	private static final ErrorCode ERROR_CODE = ErrorCode.USER_DUPLICATE_USERNAME;

	public DuplicateUsernameException(Object[] params) {
		super(ERROR_CODE, params);
	}
}
