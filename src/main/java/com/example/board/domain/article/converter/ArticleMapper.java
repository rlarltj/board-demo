package com.example.board.domain.article.converter;

import com.example.board.domain.article.dto.ArticleCreateRequest;
import com.example.board.domain.article.dto.ArticleResponse;
import com.example.board.domain.article.model.Article;
import com.example.board.domain.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedSourcePolicy = IGNORE)
public interface ArticleMapper {

	@Mapping(source = "createRequest.title", target = "title")
	@Mapping(source = "createRequest.content", target = "content")
	@Mapping(source = "user", target = "writer")
	Article toArticle(ArticleCreateRequest createRequest, User user);


	@Mapping(source = "id", target = "id")
	@Mapping(source = "writer.id", target = "writerId")
	@Mapping(source = "writer.username", target = "writerName")
	@Mapping(source = "content", target = "content")
	@Mapping(source = "title", target = "title")
	ArticleResponse toArticleResponse(Article article);
}
