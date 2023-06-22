package com.example.board.global.exception;

import static org.springframework.http.HttpStatus.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.board.global.codes.errorcode.ErrorCode;
import com.example.board.global.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final String EXCEPTION_FORMAT = "[EXCEPTION]                   -----> ";
	private static final String EXCEPTION_MESSAGE_FORMAT = "[EXCEPTION] EXCEPTION_MESSAGE -----> [{}]";
	private static final String EXCEPTION_TYPE_FORMAT = "[EXCEPTION] EXCEPTION_TYPE    -----> [{}]";
	private static final String EXCEPTION_REQUEST_URI = "[EXCEPTION] REQUEST_URI       -----> [{}]";
	private static final String EXCEPTION_HTTP_METHOD_TYPE = "[EXCEPTION] HTTP_METHOD_TYPE  -----> [{}]";

	@ExceptionHandler(BusinessException.class) // custom 에러
	public ResponseEntity<ErrorResponse> handleServiceException(HttpServletRequest request, BusinessException e) {
		ErrorCode errorCode = e.getErrorCode();
		logService(request, e, errorCode);

		return ResponseEntity
			.status(errorCode.getStatus())
			.body(ErrorResponse.of(errorCode));
	}

	@ResponseStatus(BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class) // Bean Validation(@Valid)
	public ErrorResponse handleMethodArgumentNotValidException(HttpServletRequest request, BindException e) {
		logDebug(request, e);
		log.debug("[EXCEPTION] FIELD_ERROR       -----> [{}]", e.getFieldError());
		return ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
	}

	@ResponseStatus(INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponse handleException(HttpServletRequest request, Exception e) {
		logError(e);
		return ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
	}

	private void logService(HttpServletRequest request, BusinessException e, ErrorCode errorCode) {
		log.debug(EXCEPTION_REQUEST_URI, request.getRequestURI());
		log.debug(EXCEPTION_HTTP_METHOD_TYPE, request.getMethod());
		log.info(errorCode.getCode());
		log.warn(EXCEPTION_TYPE_FORMAT, e.getClass().getSimpleName());
		log.warn(EXCEPTION_MESSAGE_FORMAT, e.getMessage());
	}

	private void logDebug(HttpServletRequest request, Exception e) {
		log.debug(EXCEPTION_REQUEST_URI, request.getRequestURI());
		log.debug(EXCEPTION_HTTP_METHOD_TYPE, request.getMethod());
		log.debug(EXCEPTION_TYPE_FORMAT, e.getClass().getSimpleName());
		log.debug(EXCEPTION_MESSAGE_FORMAT, e.getMessage());
	}

	private void logError(Exception e) {
		log.error(EXCEPTION_TYPE_FORMAT, e.getClass().getSimpleName());
		log.error(EXCEPTION_MESSAGE_FORMAT, e.getMessage());
		log.error(EXCEPTION_FORMAT, e);
	}
}
