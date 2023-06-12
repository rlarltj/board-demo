package com.example.board.domain;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static org.springframework.util.Assert.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE comment set deleted = true where id=?")
public class Comment extends BaseEntity{

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "article_id")
	private Article article;

	@Column(nullable = false, length = 600)
	private String content;

	private Comment(Article article, String content) {
		notNull(article, "게시글 정보가 없습니다.");
		hasText(content, "댓글은 필수 입력사항입니다.");

		this.article = article;
		this.content = content;
	}

	public static Comment of(Article article, String content) {
		return new Comment(article, content);
	}
}

