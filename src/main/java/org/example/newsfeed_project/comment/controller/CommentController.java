package org.example.newsfeed_project.comment.controller;

import org.example.newsfeed_project.comment.dto.CommentDto;
import org.example.newsfeed_project.comment.service.CommentService;
import org.example.newsfeed_project.user.session.SessionConst;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts/{post_id}/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	// 댓글 작성

	// 댓글 조회

	// 댓글 수정
	@PatchMapping("/{comment_id}")
	public ResponseEntity<CommentDto> modifyComment(@PathVariable(name = "post_id") Long postId,
		@PathVariable(name = "comment_id") Long commentId,
		@RequestBody CommentRequestDto dto, HttpServletRequest servletRequest) {

		HttpSession httpSession = servletRequest.getSession(false);
		Long loginUserId = (Long)servletRequest.getAttribute(SessionConst.LOGIN_USER_ID);

		return ResponseEntity.ok(commentService.modifyComment(loginUserId, postId, commentId, dto));
	}

	// 댓글 삭제

	// 댓글 좋아요 상태 변경

}
