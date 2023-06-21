package com.example.board.utils;

import com.example.board.domain.article.model.Article;
import com.example.board.domain.user.model.User;

public class ArticleObjectProvider {
	public static Article createArticle() {
		String title = "제목!";
		String content = "본문!";
		User user = UserObjectProvider.createUser();

		return new Article(title, content, user);
	}
}
