package org.example.newsfeed_project.comment.service;

import org.example.newsfeed_project.comment.dto.CommentDto;
import org.example.newsfeed_project.comment.dto.CommentRequestDto;
import org.example.newsfeed_project.comment.repository.CommentRepository;
import org.example.newsfeed_project.entity.Comment;
import org.example.newsfeed_project.exception.ValidateException;
import org.example.newsfeed_project.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;

	// 댓글 작성

	// 댓글 조회

	// 댓글 수정
	public CommentDto modifyComment(Long loginUserId, Long postId, Long commetId, CommentRequestDto requestDto) {

		// 해당 게시물에 댓글이 존재하는지 확인
		Comment comment = commentRepository.findByCommentIdAndPostId(commetId, postId)
			.orElseThrow(() -> new ValidateException("존재하지 않은 댓글입니다.", HttpStatus.NOT_FOUND));

		// 본인 확인 (댓글 작성자 = 로그인 유저?)
		if (!comment.getUser().getUserId().equals(loginUserId)) {
			throw new ValidateException("본인만 수정 가능합니다.", HttpStatus.UNAUTHORIZED);
		}

		// 수정
		int updatedRow = commentRepository.updateComment(commetId, requestDto.getComment());
		if (updatedRow == 0) {
			throw new ValidateException("댓글 수정에 실패했습니다.", HttpStatus.BAD_REQUEST);
		}

		return CommentDto.convertDto(comment);
	}

	// 댓글 삭제
}
