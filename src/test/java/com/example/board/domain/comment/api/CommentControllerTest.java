package com.example.board.domain.comment.api;

import com.example.board.domain.comment.dto.CommentCreateRequest;
import com.example.board.domain.comment.dto.CommentUpdateRequest;
import com.example.board.domain.comment.service.CommentService;
import com.example.board.global.dto.IdResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("[성공] 댓글 등록에 성공한다.")
    void 댓글_등록_성공() throws Exception {
        //given
        String content = "댓글 본문";
        Long userId = 1L;
        Long articleId = 1L;
        CommentCreateRequest createRequest = new CommentCreateRequest(userId, articleId, content);
        IdResponse idResponse = new IdResponse(1L);

        //when
        when(commentService.register(userId, createRequest)).thenReturn(idResponse);

        ResultActions result = mockMvc.perform(post("/api/v1/comments")
                .contentType(APPLICATION_JSON)
                .with(csrf())
                .content(objectMapper.writeValueAsBytes(createRequest))
        ).andDo(print());

        //then
        result.andExpect(status().isCreated());
        verify(commentService).register(userId, createRequest);
    }

    @Test
    @DisplayName("[성공] 댓글 수정에 성공한다.")
    void 댓글_수정_성공() throws Exception {
        //given
        String content = "변경할 본문";
        Long commentId = 1L;
        CommentUpdateRequest updateRequest = new CommentUpdateRequest(content);

        //when
        doNothing().when(commentService).update(commentId, updateRequest);

        ResultActions result = mockMvc.perform(put("/api/v1/comments/{id}", commentId)
                .contentType(APPLICATION_JSON)
                .with(csrf())
                .content(objectMapper.writeValueAsBytes(updateRequest))
        ).andDo(print());

        //then
        result.andExpect(status().isOk());
        verify(commentService).update(commentId, updateRequest);
    }

    @Test
    @DisplayName("[성공] 댓글 삭제에 성공한다.")
    void 댓글_삭제_성공() throws Exception {
        //given
        Long commentId = 1L;

        //when
        doNothing().when(commentService).delete(commentId);

        ResultActions result = mockMvc.perform(delete("/api/v1/comments/{id}", commentId)
                .contentType(APPLICATION_JSON)
                .with(csrf())
        ).andDo(print());

        //then
        result.andExpect(status().isOk());
        verify(commentService).delete(commentId);
    }
}
