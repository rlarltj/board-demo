package com.example.board.domain.comment.exception;

import com.example.board.global.codes.errorcode.ErrorCode;
import com.example.board.global.exception.BusinessException;

public class CommentNotFoundException extends BusinessException {
    public static final ErrorCode errorCode = ErrorCode.COMMENT_NOT_FOUND;
    public CommentNotFoundException(Object[] params) {
        super(errorCode, params);
    }
}
