package com.example.board.domain.article.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record ArticleUpdateRequest(
	@Size(min = 1, max = 100) String title,
	@Size(min = 1, max = 3000) String content,
	@NotNull Long userId
) {
}
