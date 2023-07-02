package com.example.board.domain.comment.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record CommentCreateRequest(
        @NotNull Long userId,
        @NotNull Long articleId,
        @Size(min = 1, max = 300) String content
) {
}
