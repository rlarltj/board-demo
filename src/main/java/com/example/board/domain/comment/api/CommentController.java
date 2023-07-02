package com.example.board.domain.comment.api;

import com.example.board.domain.comment.dto.CommentCreateRequest;
import com.example.board.domain.comment.dto.CommentUpdateRequest;
import com.example.board.domain.comment.service.CommentService;
import com.example.board.global.dto.IdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    /**
     * 댓글 등록
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<IdResponse> create(@RequestBody @Valid CommentCreateRequest commentCreateRequest) {
        IdResponse idResponse = commentService.register(commentCreateRequest.userId(), commentCreateRequest);
        URI location = UriComponentsBuilder.fromUriString("/api/v1/comments/" + idResponse.id()).build().toUri();

        return ResponseEntity.created(location).body(idResponse);
    }

    /**
     * 댓글 수정
     */
    @PutMapping(value = "{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> modify(@PathVariable(name = "id") Long commentId, @RequestBody @Valid CommentUpdateRequest updateRequest) {
        commentService.update(commentId, updateRequest);
        return ResponseEntity.ok().build();
    }
    /**
     * 댓글 삭제(soft delete)
     */
    @DeleteMapping(value = "{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.ok().build();
    }
}
