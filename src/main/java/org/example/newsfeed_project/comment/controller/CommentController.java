package org.example.newsfeed_project.comment.controller;

import org.example.newsfeed_project.common.session.SessionConst;
import org.example.newsfeed_project.comment.dto.CommentDto;
import org.example.newsfeed_project.comment.dto.CommentRequestDto;
import org.example.newsfeed_project.comment.service.CommentService;
import org.example.newsfeed_project.entity.Comment;
import org.example.newsfeed_project.post.dto.LikeNumResponseDto;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<Page<CommentDto>> findComment(@PathVariable("post_id") Long postId,
		@RequestParam(defaultValue = "1") int pageNum) {

		Page<CommentDto> commentPage = commentService.findComment(postId, pageNum);

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
    public ResponseEntity<String> deleteComment(HttpServletRequest request, @PathVariable(name = "post_id") Long postId, @PathVariable(name = "comment_id") Long commentId) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(SessionConst.LOGIN_USER_ID);
        commentService.deleteComment(userId, postId, commentId);
        return ResponseEntity.ok("댓글이 삭제 되었습니다.");
    }

	// 댓글 좋아요 상태 토글
	@PutMapping("/{comment_id}/{user_id}/like")
	public ResponseEntity<LikeNumResponseDto> toggleCommentLikeStatus(
		@PathVariable(name = "comment_id") Long commentId,
		@PathVariable(name = "user_id") Long userId
	) {

		Comment comment = commentService.toggleCommentLikeSatus(commentId, userId);

		return new ResponseEntity<>(new LikeNumResponseDto(comment.getLikeCount()), HttpStatus.OK);
	}

}
