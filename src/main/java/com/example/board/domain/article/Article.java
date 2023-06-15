package com.example.board.domain.article;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static org.springframework.util.Assert.*;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.example.board.domain.BaseEntity;
import com.example.board.domain.comment.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE article set deleted = true where id=?")
public class Article extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false, length = 5000)
	private String content;

	@OneToMany(mappedBy = "article")
	private Set<Comment> comments = new LinkedHashSet<>();

	private Article(String title, String content) {
		hasText(title, "제목은 필수 입력사항입니다.");
		hasText(content, "본문은 필수 입력사항입니다.");

		this.title = title;
		this.content = content;
	}

	public static Article of(String title, String content) {
		return new Article(title, content);
	}
}
