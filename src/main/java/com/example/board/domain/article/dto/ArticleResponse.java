package com.example.board.domain.article.dto;

public record ArticleResponse(
	Long articleId,
	String title,
	String content,
	Long userId
) {
}
