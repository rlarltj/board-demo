package com.example.board.domain.article.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.domain.article.dto.ArticleCreateRequest;
import com.example.board.domain.article.model.Article;
import com.example.board.domain.article.repository.ArticleRepository;
import com.example.board.domain.user.model.User;
import com.example.board.domain.user.repository.UserRepository;
import com.example.board.global.dto.IdResponse;
import com.example.board.utils.UserObjectProvider;

@SpringBootTest
@Transactional
class ArticleServiceTest {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ArticleRepository articleRepository;

	private User user;

	@BeforeEach
	void setup() {
		user = UserObjectProvider.createUser();
		userRepository.save(user);
	}

	@DisplayName("[성공] 게시글 등록에 성공한다.")
	@Test
	void 게시글_등록_성공() {
		//given
		String title = "제목";
		String content = "내용";
		ArticleCreateRequest createRequest = new ArticleCreateRequest(title, content, user.getId());

		//when
		IdResponse idResponse = articleService.register(createRequest);
		Article findOne = articleRepository.findById(idResponse.id()).get();

		//then
		assertEquals(idResponse.id(), findOne.getId());
		assertEquals(title, findOne.getTitle());
		assertEquals(content, findOne.getContent());
	}

}