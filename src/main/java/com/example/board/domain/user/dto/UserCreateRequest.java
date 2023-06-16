package com.example.board.domain.user.dto;

import javax.validation.constraints.Size;

public record UserCreateRequest(
	@Size(min = 4, max = 20) String username,
	@Size(min = 8, max = 20) String password) {
}
