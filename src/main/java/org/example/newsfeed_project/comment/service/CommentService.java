package org.example.newsfeed_project.comment.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.example.newsfeed_project.comment.dto.CommentDto;
import org.example.newsfeed_project.comment.dto.CommentRequestDto;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed_project.comment.repository.CommentRepository;
import org.example.newsfeed_project.entity.Comment;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.exception.ValidateException;
import org.example.newsfeed_project.post.repository.PostRepository;
import org.example.newsfeed_project.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	// 댓글 작성
	public CommentDto saveComment(Long postId, Long userId, CommentRequestDto requestDto) {
		Optional<Post> optionalPost = postRepository.findById(postId);
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalPost.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다.");
		}
		Post findPost = optionalPost.get();
		User findUser = optionalUser.get();

		Comment comment = new Comment(findPost, findUser, requestDto.getComments(), 0L, LocalDateTime.now());
		comment = commentRepository.save(comment);
		return new CommentDto(comment.getComments(), comment.getLike_count(), comment.getUser().getUserName(),
			comment.getUpdatedAt());
	}

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
		int updatedRow = commentRepository.updateComment(commetId, requestDto.getComments());
		if (updatedRow == 0) {
			throw new ValidateException("댓글 수정에 실패했습니다.", HttpStatus.BAD_REQUEST);
		}

		return CommentDto.convertDto(comment);
	}

	// 댓글 삭제
	@Transactional
	public void deleteComment(Long userId, Long postId, Long commentId) {
		User user = userRepository.findUserByUserIdOrElseThrow(userId);
		log.info("::: 게시물 조회 서비스가 동작하였습니다.");
		Post post = postRepository.findPostByPostIdOrElseThrow(postId);
		log.info("::: 댓글 조회 서비스가 동작하였습니다.");
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
		if(userId != comment.getUser().getUserId()) {
			throw new ValidateException("댓글 작성자가 아닙니다.", HttpStatus.UNAUTHORIZED);
		}
		commentRepository.deleteById(commentId);
	}
}
