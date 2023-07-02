package com.example.board.domain.comment.service;

import com.example.board.domain.article.model.Article;
import com.example.board.domain.article.repository.ArticleRepository;
import com.example.board.domain.comment.dto.CommentCreateRequest;
import com.example.board.domain.comment.dto.CommentUpdateRequest;
import com.example.board.domain.comment.exception.CommentNotFoundException;
import com.example.board.domain.comment.model.Comment;
import com.example.board.domain.comment.repository.CommentRepository;
import com.example.board.domain.user.model.User;
import com.example.board.domain.user.repository.UserRepository;
import com.example.board.global.dto.IdResponse;
import com.example.board.utils.ArticleObjectProvider;
import com.example.board.utils.UserObjectProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    private User user;
    private Article savedArticle;

    private Comment savedComment;
    @BeforeEach
    void setup() {
        user = UserObjectProvider.createUser();
        userRepository.save(user);

        savedArticle = ArticleObjectProvider.createArticle();
        articleRepository.save(savedArticle);

        String content = "댓글입니다.";
        CommentCreateRequest createRequest = new CommentCreateRequest(user.getId(), savedArticle.getId(), content);

        IdResponse idResponse = commentService.register(user.getId(), createRequest);
        savedComment = commentRepository.findById(idResponse.id()).get();

        assertEquals(idResponse.id(), savedComment.getId());
        assertEquals(content, savedComment.getContent());
    }

    @Test
    @DisplayName("[성공] 특정 댓글을 수정할 수 있다.")
    void 댓글_수정_성공() throws Exception {
        //given
        String content = "내용 변경";
        CommentUpdateRequest updateRequest = new CommentUpdateRequest(content);

        //when
        commentService.update(savedComment.getId(), updateRequest);

        //then
        Comment result = commentRepository.findById(savedComment.getId()).get();
        assertEquals(content, result.getContent());
    }

    @Test
    @DisplayName("[성공] 특정 댓글을 삭제할 수 있다.")
    void 댓글_삭제_성공() throws Exception {
        //when
        commentService.delete(savedComment.getId());
        Optional<Comment> result = commentRepository.findById(savedComment.getId());

        //then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("[실패] 존재하지 않는 댓글은 수정할 수 없다.")
    void 댓글_수정_실패 () throws Exception {
        //given
        Long unknownId = 100L;
        String content = "내용 변경";
        CommentUpdateRequest updateRequest = new CommentUpdateRequest(content);

        //when & then
        assertThrows(CommentNotFoundException.class, () -> commentService.update(unknownId, updateRequest));
    }

    @Test
    @DisplayName("[실패] 존재하지 않는 댓글은 삭제할 수 없다.")
    void 댓글_삭제_실패 () throws Exception {
        //given
        Long unknownId = 100L;

        //when & then
        assertThrows(CommentNotFoundException.class, () -> commentService.delete(unknownId));
    }
}