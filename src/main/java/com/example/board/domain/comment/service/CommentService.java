package com.example.board.domain.comment.service;

import com.example.board.domain.article.exception.ArticleNotFoundException;
import com.example.board.domain.article.model.Article;
import com.example.board.domain.article.repository.ArticleRepository;
import com.example.board.domain.comment.dto.CommentCreateRequest;
import com.example.board.domain.comment.dto.CommentUpdateRequest;
import com.example.board.domain.comment.exception.CommentNotFoundException;
import com.example.board.domain.comment.model.Comment;
import com.example.board.domain.comment.repository.CommentRepository;
import com.example.board.domain.user.exception.UserNotFoundException;
import com.example.board.domain.user.model.User;
import com.example.board.domain.user.repository.UserRepository;
import com.example.board.global.dto.IdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    //TODO Security 도입 후 수정
    @Transactional
    public IdResponse register(Long userId, CommentCreateRequest createRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(new Object[] {userId}));

        Article article = articleRepository.findById(createRequest.articleId())
                .orElseThrow(() -> new ArticleNotFoundException(new Object[]{createRequest.articleId()}));

        Comment comment = new Comment(article, createRequest.content(), user);

        commentRepository.save(comment);

        return new IdResponse(comment.getId());
    }

    @Transactional
    public void update(Long commentId, CommentUpdateRequest updateRequest) {
        Comment comment = getComment(commentId);

        comment.update(updateRequest.content());
    }

    @Transactional
    public void delete(Long commentId) {
        Comment comment = getComment(commentId);

        commentRepository.delete(comment);
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(new Object[]{commentId}));
    }
}
