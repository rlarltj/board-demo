package com.example.board.global.codes.errorcode;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

	BUSINESS_EXCEPTION_ERROR(HttpStatus.BAD_REQUEST, "B999", "Business Exception Error"),

	//user
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "존재하지 않는 사용자입니다."),
	USER_DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "U002", "이미 사용중인 아이디입니다."),

	//article
	ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "A001", "존재하지 않는 게시글입니다."),

	//comment
	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "존재하지 않는 댓글입니다"),

	//global
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G001", "서버 에러입니다."),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "G002", "잘못된 입력 값입니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	ErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

}
