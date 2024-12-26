package org.example.newsfeed_project.comment.service;

import org.example.newsfeed_project.comment.dto.CommentDto;
import org.example.newsfeed_project.comment.repository.CommentRepository;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.exception.ValidateException;
import org.example.newsfeed_project.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	// 댓글 작성

	// 댓글 조회
	public Page<CommentDto> findcomment(Long postId, int pageNum) {

		//postId로 해당 포스트 조회
		Post findPost = postRepository.findById(postId)
			.orElseThrow(() -> new ValidateException("Post가 존재하지 않습니다.", HttpStatus.NOT_FOUND));

		//페이징, 정렬조건
		PageRequest pageRequest = PageRequest.of(pageNum - 1, 10, Sort.by(Sort.Direction.DESC, "like_count"));

		return commentRepository.findByPost_PostId(postId, pageRequest)
			//조회 된 엔티티를 DTO로 변환
			.map(comment ->
				new CommentDto(comment.getComments(),
					comment.getLike_count(),
					comment.getUser().getUserName(),
					comment.getUpdatedAt()));
	}

	// 댓글 수정

	// 댓글 삭제
}
