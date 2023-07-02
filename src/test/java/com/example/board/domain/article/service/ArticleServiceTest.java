package com.example.board.domain.article.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.board.domain.article.dto.ArticleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.domain.article.dto.ArticleCreateRequest;
import com.example.board.domain.article.dto.ArticleUpdateRequest;
import com.example.board.domain.article.model.Article;
import com.example.board.domain.article.repository.ArticleRepository;
import com.example.board.domain.user.model.User;
import com.example.board.domain.user.repository.UserRepository;
import com.example.board.global.dto.IdResponse;
import com.example.board.utils.ArticleObjectProvider;
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
	private Article savedArticle;

	@BeforeEach
	void setup() {
		user = UserObjectProvider.createUser();
		userRepository.save(user);

		savedArticle = ArticleObjectProvider.createArticle();
		articleRepository.save(savedArticle);
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

	@DisplayName("[성공] 게시글 수정에 성공한다.")
	@Test
	void 게시글_수정_성공() {
		//given
		String title = "제목 변경";
		String content = "내용 변경";
		ArticleUpdateRequest updateRequest = new ArticleUpdateRequest(title, content, user.getId());

		//when
		articleService.update(savedArticle.getId(), updateRequest);

		//then
		Article result = articleRepository.findById(savedArticle.getId()).get();
		assertEquals(title, result.getTitle());
		assertEquals(content, result.getContent());
	}

	@DisplayName("[성공] 게시글 단건조회에 성공한다.")
	@Test
	void 게시글_단건조회_성공() {
		//given
		String title = "제목";
		String content = "내용";
		ArticleCreateRequest createRequest = new ArticleCreateRequest(title, content, user.getId());

		//when
		IdResponse result = articleService.register(createRequest);
		ArticleResponse findOne = articleService.findOne(result.id());

		//then
		assertEquals(title, findOne.title());
		assertEquals(content, findOne.content());
		assertEquals(user.getId(), findOne.writerId());
		assertEquals(user.getUsername(), findOne.writerName());
	}
}