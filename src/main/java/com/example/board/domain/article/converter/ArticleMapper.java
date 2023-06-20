package com.example.board.domain.article.converter;

import static org.mapstruct.ReportingPolicy.*;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.board.domain.article.dto.ArticleCreateRequest;
import com.example.board.domain.article.model.Article;
import com.example.board.domain.user.model.User;

@Mapper(componentModel = "spring", unmappedSourcePolicy = IGNORE)
public interface ArticleMapper {

	@Mapping(source = "createRequest.title", target = "title")
	@Mapping(source = "createRequest.content", target = "content")
	@Mapping(source = "user", target = "writer")
	Article toArticle(ArticleCreateRequest createRequest, User user);
}
