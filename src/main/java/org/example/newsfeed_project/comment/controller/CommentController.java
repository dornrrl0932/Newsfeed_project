package org.example.newsfeed_project.comment.controller;

import org.example.newsfeed_project.comment.dto.CommentDto;
import org.example.newsfeed_project.comment.dto.CommentRequestDto;
import org.example.newsfeed_project.comment.service.CommentService;
import org.example.newsfeed_project.user.session.SessionConst;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts/{post_id}/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	// 댓글 작성
	@PostMapping("/")
	public CommentDto saveComment(@PathVariable("post_id") Long postId,
		@Valid @RequestBody CommentRequestDto requestDto,
		HttpServletRequest request) {

		Long userId = (Long)request.getSession().getAttribute("loginUserId");
		CommentDto commentDto = commentService.saveComment(postId, userId, requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(commentDto).getBody();
	}

	// 댓글 조회
	@GetMapping("/")
	public ResponseEntity<Page<CommentDto>> findcomment(@PathVariable Long postId,
		@RequestParam(defaultValue = "1") int pageNum) {

		Page<CommentDto> commentPage = commentService.findcomment(postId, pageNum);

		return ResponseEntity.ok(commentPage);
	}

	// 댓글 수정
	@PatchMapping("/{comment_id}")
	public ResponseEntity<CommentDto> modifyComment(@PathVariable(name = "post_id") Long postId,
		@PathVariable(name = "comment_id") Long commentId,
		@Valid @RequestBody CommentRequestDto dto, HttpServletRequest servletRequest) {

		HttpSession httpSession = servletRequest.getSession(false);
		Long loginUserId = (Long)httpSession.getAttribute(SessionConst.LOGIN_USER_ID);

		return ResponseEntity.ok(commentService.modifyComment(loginUserId, postId, commentId, dto));
	}

	// 댓글 삭제
	@DeleteMapping("/{comment_id}")
	public ResponseEntity<String> deleteComment(HttpServletRequest request, @PathVariable(name = "post_id") Long postId,
		@PathVariable(name = "comment_id") Long commentId) {
		HttpSession session = request.getSession();
		Long userId = (Long)session.getAttribute(SessionConst.LOGIN_USER_ID);
		commentService.deleteComment(userId, postId, commentId);
		return ResponseEntity.ok("댓글이 삭제 되었습니다.");
	}
	// 댓글 좋아요 상태 변경

}
