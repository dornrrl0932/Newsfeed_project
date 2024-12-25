package org.example.newsfeed_project.comment.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.newsfeed_project.comment.dto.CommentDto;
import org.example.newsfeed_project.comment.dto.CommentRequestDto;
import org.example.newsfeed_project.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts/{post_id}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/")
    public CommentDto saveComment(@PathVariable("post_id") Long postId,
                                  @RequestBody CommentRequestDto requestDto,
                                  HttpServletRequest request) {

        Long userId = (Long) request.getSession().getAttribute("loginUserId");
		CommentDto commentDto = commentService.saveComment(postId, userId, requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(commentDto).getBody();
    }
    // 댓글 조회

	// 댓글 수정

	// 댓글 삭제

	// 댓글 좋아요 상태 변경

}
