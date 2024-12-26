package org.example.newsfeed_project.post.service;

import java.util.List;
import java.util.Optional;

import org.example.newsfeed_project.common.exception.ResponseCode;
import org.example.newsfeed_project.common.exception.ValidateException;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.entity.PostLike;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.post.dto.CreatedPostRequestDto;
import org.example.newsfeed_project.post.dto.CreatedPostResponseDto;
import org.example.newsfeed_project.post.dto.PostFindByDateRangeRequestDto;
import org.example.newsfeed_project.post.dto.PostFindByPageRequestDto;
import org.example.newsfeed_project.post.dto.PostPageDto;
import org.example.newsfeed_project.post.dto.UpdatedPostRequestDto;
import org.example.newsfeed_project.post.dto.UpdatedPostResponseDto;
import org.example.newsfeed_project.post.repository.PostLikeRepository;
import org.example.newsfeed_project.post.repository.PostRepository;
import org.example.newsfeed_project.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
	public final PostRepository postRepository;
	public final PostLikeRepository postLikeRepository;
	public final UserRepository userRepository;

	//created
	public CreatedPostResponseDto createdPost(Long userId, CreatedPostRequestDto createdRequest) {
		User user = userRepository.findUserByUserIdOrElseThrow(userId);
		Post post = new Post(user, createdRequest.getTitle(), createdRequest.getContents());
		Post savePost = postRepository.save(post);
		return new CreatedPostResponseDto(
			user.getUserName(),
			savePost.getTitle(),
			savePost.getContents(),
			savePost.getUpdatedAt());
	}

	public List<PostPageDto> findPostByDateRange(int requestPage, int pageSize,
		PostFindByDateRangeRequestDto requestDto) {

		Pageable pageable = PageRequest.of(requestPage, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt"));

		Page<Post> postPage = postRepository.findByUpdatedAtBetween(requestDto.startDate, requestDto.endDate, pageable);

		return PostPageDto.convertFrom(postPage);
	}

	public List<PostPageDto> findPostByPage(int requestPage, int pageSize, PostFindByPageRequestDto requestDto) {
		Pageable pageable;
		switch (requestDto.getOrder()) {
			case "like":
				pageable = PageRequest.of(requestPage, pageSize, Sort.by(Sort.Direction.DESC, "like_count"));
				break;
			case "update":
				pageable = PageRequest.of(requestPage, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt"));
				break;
			default:
				throw new ResponseStatusException(ResponseCode.ORDER_NOT_FOUND.getStatus());
		}
		Page<Post> postPage = postRepository.findAll(pageable);

		return PostPageDto.convertFrom(postPage);
	}

	//게시물 id로 게시물 조회
	public Post findPostByPostId(Long postId) {
		Post findPost = postRepository.findPostByPostIdOrElseThrow(postId);
		return findPost;
	}

	//좋아요 상태 토글
	@Transactional
	public Post toggleLikeStatus(Long postId, Long userId) {
		Post findPost = postRepository.findPostByPostIdOrElseThrow(postId);
		User findUser = userRepository.findUserByUserIdOrElseThrow(userId); //게시물에 좋아요를 누르려는 회원 id

		//좋아요를 누르려는 사람=게시를 작성자 본인인 경우
		if (findPost.getUser().getUserId().equals(userId)) {
			throw new ResponseStatusException(ResponseCode.CANNOT_LIKE_SELF_POST.getStatus(),
				ResponseCode.CANNOT_LIKE_SELF_POST.getMessage());
		}

		//좋아요 이력 조회
		Optional<PostLike> optionalPostLike = postLikeRepository.findByPostIdAndUserId(postId, userId);

		//좋아요를 누른 기록이 없는 경우, 새로운 PostLike 객체 생성 및 DB에 저장
		if (optionalPostLike.isEmpty()) {
			PostLike newPostLike = PostLike.builder()
				.post(findPost)
				.user(findUser)
				.build();
			postLikeRepository.save(newPostLike);
			findPost.setLikeCount(findPost.getLikeCount() + 1);
		} else {//이미 좋아요를 누른 경우, 상태 토글
			PostLike postLike = optionalPostLike.get();
			if (postLike.getLikeStatus()) {
				postLike.setLikeStatus(false);
				findPost.setLikeCount(findPost.getLikeCount() - 1);
			} else {
				postLike.setLikeStatus(true);
				findPost.setLikeCount(findPost.getLikeCount() + 1);
			}
			postLikeRepository.save(postLike);
		}

		return postRepository.save(findPost);
	}

	//update
	@Transactional
	public UpdatedPostResponseDto updatePost(Long userId, Long postId, UpdatedPostRequestDto updatePostRequest) {
		User user = userRepository.findUserByUserIdOrElseThrow(userId);
		Post post = postRepository.findPostByPostIdOrElseThrow(postId);
		if (userId != post.getUser().getUserId()) {
			throw new ValidateException(ResponseCode.ID_MISMATCH.getMessage(), ResponseCode.ID_MISMATCH.getStatus());
		}
		post.updatedPost(updatePostRequest.getTitle(), updatePostRequest.getContents());
		return new UpdatedPostResponseDto(post.getTitle(), post.getContents(), post.getUpdatedAt());
	}

	@Transactional
	public void deletePost(Long userId, Long postId) {
		User user = userRepository.findUserByUserIdOrElseThrow(userId);
		Post post = postRepository.findPostByPostIdOrElseThrow(postId);
		if (userId != post.getUser().getUserId()) {
			throw new ValidateException(ResponseCode.ID_MISMATCH.getMessage(), ResponseCode.ID_MISMATCH.getStatus());
		}
		postRepository.deleteById(postId);
	}
}
