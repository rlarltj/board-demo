package com.example.board.domain.article.api;

import static org.springframework.http.MediaType.*;

import java.net.URI;

import javax.validation.Valid;

import com.example.board.domain.article.dto.ArticleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.board.domain.article.dto.ArticleCreateRequest;
import com.example.board.domain.article.dto.ArticleUpdateRequest;
import com.example.board.domain.article.service.ArticleService;
import com.example.board.global.dto.IdResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ArticleController {

	private final ArticleService articleService;

	//TODO 시큐리티 도입 후 파라미터 변경
	@PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<IdResponse> create(@RequestBody @Valid ArticleCreateRequest createRequest) {

		IdResponse idResponse = articleService.register(createRequest);
		URI location = UriComponentsBuilder.fromUriString("/api/v1/articles/" + idResponse.id()).build().toUri();

		return ResponseEntity.created(location).body(idResponse);
	}

	@PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> modify(@PathVariable(value = "id") Long id,
									   @RequestBody @Valid ArticleUpdateRequest updateRequest) {

		articleService.update(id, updateRequest);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {

		articleService.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<ArticleResponse> getOne(@PathVariable(value = "id") Long id) {
		ArticleResponse result = articleService.findOne(id);

		return ResponseEntity.ok().body(result);
	}
}
