package com.example.board.domain.user.model;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static org.springframework.util.Assert.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.example.board.global.domain.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE users set deleted = true where id=?")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(length = 20, nullable = false)
	private String username;

	@Column(length = 20, nullable = false)
	private String password;

	@Column(name = "role")
	@Enumerated(STRING)
	private UserRole role = UserRole.USER;

	private User(String username, String password) {
		hasText(username, "아이디는 필수 입력사항입니다.");
		hasText(password, "비밀번호는 필수 입력사항입니다.");

		this.username = username;
		this.password = password;
	}

	public static User of(String username, String password) {
		return new User(username, password);
	}
}
