package com.example.board.domain.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.domain.article.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
