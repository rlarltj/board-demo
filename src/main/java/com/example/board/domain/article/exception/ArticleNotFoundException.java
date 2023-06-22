package com.example.board.domain.article.exception;

import com.example.board.global.codes.errorcode.ErrorCode;
import com.example.board.global.exception.BusinessException;

public class ArticleNotFoundException extends BusinessException {
	private static final ErrorCode ERROR_CODE = ErrorCode.ARTICLE_NOT_FOUND;

	public ArticleNotFoundException(Object[] params) {
		super(ERROR_CODE, params);
	}
}
