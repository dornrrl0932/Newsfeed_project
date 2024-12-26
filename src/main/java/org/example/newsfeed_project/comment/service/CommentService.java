package org.example.newsfeed_project.comment.service;

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

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

	private final UserRepository userRepository;
	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

	// 댓글 작성

	// 댓글 조회

	// 댓글 수정

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
