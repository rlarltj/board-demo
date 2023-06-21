package com.example.board.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.domain.user.exception.DuplicateUsernameException;
import com.example.board.domain.user.model.User;
import com.example.board.domain.user.repository.UserRepository;
import com.example.board.global.dto.IdResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public IdResponse join(String username, String password) {
		userRepository.findByUsername(username)
			.ifPresent(user -> {
				throw new DuplicateUsernameException("이미 사용중인 ID입니다: " + user.getUsername());
			});

		User user = new User(username, password);
		userRepository.save(user);

		return new IdResponse(user.getId());
	}
}
