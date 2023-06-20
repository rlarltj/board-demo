package com.example.board.domain.article.model;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static org.springframework.util.Assert.*;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.example.board.domain.comment.Comment;
import com.example.board.domain.user.model.User;
import com.example.board.global.domain.BaseEntity;

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

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "writer_id")
	private User writer;

	public Article(String title, String content, User writer) {
		hasText(title, "제목은 필수 입력사항입니다.");
		hasText(content, "본문은 필수 입력사항입니다.");
		notNull(writer, "작성자 정보는 필수 입력사항입니다.");

		this.title = title;
		this.content = content;
		this.writer = writer;
	}

}
