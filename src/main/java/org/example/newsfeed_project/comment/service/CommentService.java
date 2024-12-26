package org.example.newsfeed_project.comment.service;

import java.util.List;
import java.util.Optional;

import org.example.newsfeed_project.comment.dto.CommentDto;
import org.example.newsfeed_project.comment.dto.CommentListDto;
import org.example.newsfeed_project.comment.dto.CommentRequestDto;
import org.example.newsfeed_project.comment.repository.CommentRepository;
import org.example.newsfeed_project.comment.repository.CommetLikeRepository;
import org.example.newsfeed_project.common.exception.ResponseCode;
import org.example.newsfeed_project.common.exception.ValidateException;
import org.example.newsfeed_project.entity.Comment;
import org.example.newsfeed_project.entity.CommentLike;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.post.repository.PostRepository;
import org.example.newsfeed_project.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final CommetLikeRepository commetLikeRepository;

	// 댓글 작성
	public CommentDto saveComment(Long postId, Long userId, CommentRequestDto requestDto) {
		Post findPost = postRepository.findPostByPostIdOrElseThrow(postId);
		User findUser = userRepository.findUserByUserIdOrElseThrow(userId);

		Comment comment = new Comment(findPost, findUser, requestDto.getComments(), 0L);
		comment = commentRepository.save(comment);
		return CommentDto.convertDto(comment);
	}

	// 댓글 조회
	public CommentListDto findcomment(Long postId, PageRequest pageRequest) {

		//postId로 해당 포스트 조회
		Post findPost = postRepository.findPostByPostIdOrElseThrow(postId);

		List<CommentDto> commentDtos = commentRepository.findByPost_PostId(postId, pageRequest)
			//조회 된 엔티티를 DTO로 변환
			.map(CommentDto::convertDto)
			.getContent();

		return new CommentListDto(commentDtos);
	}

	// 댓글 수정
	public CommentDto modifyComment(Long loginUserId, Long postId, Long commetId, CommentRequestDto requestDto) {
		// 게시물이 존재하는지 확인
		Post post = postRepository.findPostByPostIdOrElseThrow(postId);
		// 해당 게시물에 댓글이 존재하는지 확인
		Comment comment = commentRepository.findByCommentIdAndPostIdOrElseThrwo(commetId, postId);

		// 본인 확인 (댓글 작성자 = 로그인 유저?)
		if (!comment.getUser().getUserId().equals(loginUserId)) {
			throw new ValidateException(ResponseCode.ID_MISMATCH);
		}

		// 수정
		commentRepository.updateComment(commetId, requestDto.getComments());

		return CommentDto.convertDto(comment);
	}

	// 댓글 삭제
	@Transactional
	public void deleteComment(Long userId, Long postId, Long commentId) {
		User user = userRepository.findUserByUserIdOrElseThrow(userId);
		log.info("::: 게시물 조회 서비스가 동작하였습니다.");
		Post post = postRepository.findPostByPostIdOrElseThrow(postId);
		log.info("::: 댓글 조회 서비스가 동작하였습니다.");
		Comment comment = commentRepository.findByCommentIdOrElseThrow(commentId);
		if (userId != comment.getUser().getUserId()) {
			throw new ValidateException(ResponseCode.ID_MISMATCH);
		}
		commentRepository.deleteById(commentId);
	}

	// 댓글 좋아요 상태 토글
	@Transactional
	public Comment toggleCommentLikeSatus(Long commentId, Long userId) {
		Comment findComment = commentRepository.findByCommentIdOrElseThrow(commentId);
		User findUser = userRepository.findUserByUserIdOrElseThrow(userId); //게시물에 좋아요를 누르려는 회원 객체

		// 좋아요를 누르려는 사람=댓글 작성자 본인인 경우
		if (findComment.getUser().getUserId().equals(userId)) {
			throw new ResponseStatusException(ResponseCode.CANNOT_LIKE_SELF_COMMENT.getStatus(),
				ResponseCode.CANNOT_LIKE_SELF_COMMENT.getMessage());
		}

		// 좋아요 이력 조회
		Optional<CommentLike> optionalCommentLike = commetLikeRepository.findByCommentIdAndUserId(commentId, userId);

		//좋아요를 누른 기록이 없는 경우, 새로운 PostLike 객체 생성 및 DB에 저장
		if (optionalCommentLike.isEmpty()) {
			CommentLike newCommentLike = CommentLike.builder()
				.comment(findComment)
				.user(findUser)
				.build();
			commetLikeRepository.save(newCommentLike);
			findComment.setLikeCount(findComment.getLikeCount() + 1);
		} else {
			CommentLike commentLike = optionalCommentLike.get();
			if (commentLike.getLikeStatus()) {
				commentLike.setLikeStatus(false);
				findComment.setLikeCount(findComment.getLikeCount() - 1);
			} else {
				commentLike.setLikeStatus(true);
				findComment.setLikeCount(findComment.getLikeCount() + 1);
			}
			commetLikeRepository.save(commentLike);
		}
		return commentRepository.save(findComment);
	}
}
