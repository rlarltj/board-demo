package com.example.board.domain.article.api;

import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.board.domain.article.dto.ArticleCreateRequest;
import com.example.board.domain.article.dto.ArticleUpdateRequest;
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

	@DisplayName("[성공] 게시글 등록에 성공한다.")
	@WithMockUser
	@Test
	void 게시글_등록_성공() throws Exception {
		//given
		String title = "제목";
		String content = "내용";
		Long userId = 1L;
		ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest(title, content, userId);
		IdResponse idResponse = new IdResponse(1L);

		//when
		when(articleService.register(articleCreateRequest)).thenReturn(idResponse);

		ResultActions result = mockMvc.perform(post("/api/v1/articles")
				.with(csrf())
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(articleCreateRequest)
				)
			)
			.andDo(print());

		//then
		result.andExpect(status().isCreated());
		verify(articleService).register(articleCreateRequest);
	}

	@DisplayName("[성공] 게시글 수정에 성공한다.")
	@WithMockUser
	@Test
	void 게시글_수정_성공() throws Exception {
		//given
		String title = "제목";
		String content = "내용";
		Long userId = 1L;
		Long articleId = 1L;

		ArticleUpdateRequest updateRequest = new ArticleUpdateRequest(title, content, userId);

		//when
		doNothing().when(articleService).update(anyLong(), any());
		ResultActions result = mockMvc.perform(put("/api/v1/articles/{id}", articleId)
				.with(csrf())
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(updateRequest)
				)
			)
			.andDo(print());

		//then
		result.andExpect(status().isOk());
		verify(articleService).update(articleId, updateRequest);
	}

	@DisplayName("[성공] 게시글 삭제에 성공한다.")
	@WithMockUser
	@Test
	void 게시글_삭제_성공() throws Exception {
		//given
		Long articleId = 1L;

		//when
		doNothing().when(articleService).delete(anyLong());
		ResultActions result = mockMvc.perform(delete("/api/v1/articles/{id}", articleId)
				.with(csrf())
				.contentType(APPLICATION_JSON)
			)
			.andDo(print());

		//then
		result.andExpect(status().isOk());

		verify(articleService).delete(articleId);
	}
}