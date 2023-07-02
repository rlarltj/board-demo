package com.example.board.utils;

import com.example.board.domain.article.model.Article;
import com.example.board.domain.comment.model.Comment;
import com.example.board.domain.user.model.User;

public class CommentObjectProvider {

    public static Comment createComment() {
        User user = UserObjectProvider.createUser();
        Article article = ArticleObjectProvider.createArticle();

        String content = "댓글입니다!!";

        return new Comment(article, content, user);
    }
}
