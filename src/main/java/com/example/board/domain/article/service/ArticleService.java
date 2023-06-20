package com.example.board.domain.article.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.domain.article.converter.ArticleMapper;
import com.example.board.domain.article.dto.ArticleCreateRequest;
import com.example.board.domain.article.model.Article;
import com.example.board.domain.article.repository.ArticleRepository;
import com.example.board.domain.user.exception.UserNotFoundException;
import com.example.board.domain.user.model.User;
import com.example.board.domain.user.repository.UserRepository;
import com.example.board.global.dto.IdResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {
	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;
	private final ArticleMapper articleMapper;

	/**
	 * 등록, 수정, 삭제, 조회
	 * 조회 - 단 건 조회, 전체 조회(페이징), 작성자 조회, 제목 조회, 내용 조회, 제목 + 내용 조회
	 */

	/**
	 * 등록
	 */

	public IdResponse register(ArticleCreateRequest createRequest) {
		User user = userRepository.findById(createRequest.userId())
			.orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

		Article article = articleMapper.toArticle(createRequest, user);

		articleRepository.save(article);

		return new IdResponse(article.getId());
	}
}
