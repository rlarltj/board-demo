package com.example.board.domain.user;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE users set deleted = true where id=?")
public class User {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(length = 20, nullable = false)
	private String username;

	@Column(length = 20, nullable = false)
	private String password;
}
