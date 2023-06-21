package com.example.board.domain.article.exception;

public class ArticleNotFoundException extends RuntimeException {
	public ArticleNotFoundException() {
		super();
	}

	public ArticleNotFoundException(String message) {
		super(message);
	}
}
