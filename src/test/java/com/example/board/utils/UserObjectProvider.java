package com.example.board.utils;

import com.example.board.domain.user.model.User;

public class UserObjectProvider {

	public static User createUser() {
		return User.of("홍길동", "1234");
	}
}
