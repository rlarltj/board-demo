package com.example.board.domain.comment.dto;

import javax.validation.constraints.Size;

public record CommentUpdateRequest(
        @Size(min = 1, max = 300) String content
) {
}
