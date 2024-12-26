package org.example.newsfeed_project.comment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.newsfeed_project.comment.service.CommentService;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.user.session.SessionConst;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts/{post_id}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성

	// 댓글 조회

	// 댓글 수정

	// 댓글 삭제
    @DeleteMapping("/{comment_id}")
    public ResponseEntity<String> deleteComment(HttpServletRequest request, @PathVariable(name = "post_id") Long postId, @PathVariable(name = "comment_id") Long commentId) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(SessionConst.LOGIN_USER_ID);
        commentService.deleteComment(userId, postId, commentId);
        return ResponseEntity.ok("댓글이 삭제 되었습니다.");
    }
	// 댓글 좋아요 상태 변경

}
