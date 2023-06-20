package com.example.board.domain.article.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.board.domain.article.converter.ArticleMapper;
import com.example.board.domain.article.dto.ArticleCreateRequest;
import com.example.board.domain.article.model.Article;
import com.example.board.domain.article.repository.ArticleRepository;
import com.example.board.domain.user.model.User;
import com.example.board.domain.user.repository.UserRepository;
import com.example.board.utils.UserObjectProvider;

@SpringBootTest
public class ArticleSliceTest {

	@Autowired
	private ArticleService articleService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private ArticleRepository articleRepository;

	@MockBean
	private ArticleMapper articleMapper;

	@DisplayName("[성공] 게시글 등록에 성공한다.")
	@Test
	void 게시글_등록_성공() {
		//given
		String title = "제목";
		String content = "내용";
		ArticleCreateRequest createRequest = new ArticleCreateRequest(title, content, 1L);
		User user = UserObjectProvider.createUser();
		Article article = new Article(title, content, user);

		//when
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(articleMapper.toArticle(createRequest, user)).thenReturn(article);
		when(articleRepository.save(article)).thenReturn(article);

		//then
		assertDoesNotThrow(() -> articleService.register(createRequest));

		verify(userRepository).findById(anyLong());
		verify(articleMapper).toArticle(any(), any());
		verify(articleRepository).save(any());
	}
}
