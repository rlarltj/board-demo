package com.example.board.domain.article.dto;

public record ArticleResponse(
        Long id,
        String title,
        String content,
        Long writerId,
        String writerName
) {
}
