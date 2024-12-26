package org.example.newsfeed_project.comment.controller;

import org.example.newsfeed_project.comment.dto.CommentDto;
import org.example.newsfeed_project.comment.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts/{post_id}/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	// 댓글 작성

	// 댓글 조회
	@GetMapping("/")
	public ResponseEntity<Page<CommentDto>> findcomment(@PathVariable Long postId,
		@RequestParam(defaultValue = "1") int pageNum) {

		Page<CommentDto> commentPage = commentService.findcomment(postId, pageNum);

		return ResponseEntity.ok(commentPage);
	}

	// 댓글 수정

	// 댓글 삭제

	// 댓글 좋아요 상태 변경

}
