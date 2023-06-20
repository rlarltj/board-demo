package com.example.board.domain.article.api;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.board.domain.article.dto.ArticleCreateRequest;
import com.example.board.domain.article.service.ArticleService;
import com.example.board.domain.user.model.User;
import com.example.board.global.dto.IdResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ArticleController.class)
@AutoConfigureMockMvc
class ArticleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ArticleService articleService;

	private User user;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() {

	}

	@DisplayName("[성공] 게시글 등록에 성공한다.")
	@WithMockUser
	@Test
	void 게시글_등록_성공() throws Exception {
		String title = "제목";
		String content = "내용";
		Long userId = 1L;
		ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest(title, content, userId);
		IdResponse idResponse = new IdResponse(1L);

		when(articleService.register(articleCreateRequest)).thenReturn(idResponse);

		mockMvc.perform(post("/api/v1/articles")
				.with(csrf())
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(articleCreateRequest)
				)
			)
			.andDo(print())
			.andExpect(status().isCreated());

		verify(articleService).register(articleCreateRequest);
	}
}