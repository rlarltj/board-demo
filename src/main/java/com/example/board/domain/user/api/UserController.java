package com.example.board.domain.user.api;

import static org.springframework.http.MediaType.*;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.domain.user.dto.UserCreateRequest;
import com.example.board.domain.user.service.UserService;
import com.example.board.global.dto.IdResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	@PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public IdResponse join(@RequestBody @Valid UserCreateRequest userCreateRequest) {
		return userService.join(userCreateRequest.username(), userCreateRequest.password());
	}
}
