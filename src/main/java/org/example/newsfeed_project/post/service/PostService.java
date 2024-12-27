package org.example.newsfeed_project.post.service;

import java.util.Optional;

import org.example.newsfeed_project.common.exception.ResponseCode;
import org.example.newsfeed_project.common.exception.ValidateException;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.entity.PostLike;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.follow.repository.FollowRepository;
import org.example.newsfeed_project.post.dto.CreatedPostRequestDto;
import org.example.newsfeed_project.post.dto.CreatedPostResponseDto;
import org.example.newsfeed_project.post.dto.PostFindByDateRangeRequestDto;
import org.example.newsfeed_project.post.dto.PostFindDetailByIdResponseDto;
import org.example.newsfeed_project.post.dto.PostListDto;
import org.example.newsfeed_project.post.dto.PostPageDto;
import org.example.newsfeed_project.post.dto.UpdatedPostRequestDto;
import org.example.newsfeed_project.post.dto.UpdatedPostResponseDto;
import org.example.newsfeed_project.post.repository.PostLikeRepository;
import org.example.newsfeed_project.post.repository.PostRepository;
import org.example.newsfeed_project.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
	public final FollowRepository followRepository;

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

	// 팔로워 피드 조회
	public PostListDto getPostsByFriend(Long userId, Pageable pageable) {
		Page<Post> postPage = postRepository.findPostsBySessionUser(userId, pageable);
		return PostListDto.convertFrom(PostPageDto.convertFrom(postPage));
	}

	// 기간 별 조회
	public PostListDto findPostByDateRange(Pageable pageable,
		PostFindByDateRangeRequestDto requestDto) {

		Page<Post> postPage = postRepository.findByUpdatedAtBetween(requestDto.startDate, requestDto.endDate, pageable);

		return PostListDto.convertFrom(PostPageDto.convertFrom(postPage));
	}

	// 조건 별 조회
	public PostListDto findPostByPage(Pageable pageable) {

		Page<Post> postPage = postRepository.findAll(pageable);

		if (postPage.getTotalElements() == 0) {
			System.out.println("No posts found between the specified dates.");
		} else {
			System.out.println(postPage.getTotalElements());
		}  // - ?

		return PostListDto.convertFrom(PostPageDto.convertFrom(postPage));
	}

	//게시물 id로 게시물 조회
	public PostFindDetailByIdResponseDto findPostByPostId(Long postId) {
		Post findPost = postRepository.findPostByPostIdOrElseThrow(postId);
		return PostFindDetailByIdResponseDto.ConvertFromPostFineDetailDto(findPost);
	}

	// update
	@Transactional
	public UpdatedPostResponseDto updatePost(Long userId, Long postId, UpdatedPostRequestDto updatePostRequest) {
		User user = userRepository.findUserByUserIdOrElseThrow(userId);
		Post post = postRepository.findPostByPostIdOrElseThrow(postId);
		if (userId != post.getUser().getUserId()) {
			throw new ValidateException(ResponseCode.ID_MISMATCH);
		}
		post.updatedPost(updatePostRequest.getTitle(), updatePostRequest.getContents());
		return new UpdatedPostResponseDto(post.getTitle(), post.getContents(), post.getUpdatedAt());
	}

	// 게시글 삭제
	@Transactional
	public void deletePost(Long userId, Long postId) {
		User user = userRepository.findUserByUserIdOrElseThrow(userId);
		Post post = postRepository.findPostByPostIdOrElseThrow(postId);
		if (userId != post.getUser().getUserId()) {
			throw new ValidateException(ResponseCode.ID_MISMATCH);
		}
		postRepository.deleteById(postId);
	}

	//좋아요 상태 토글
	@Transactional
	public Post toggleLikeStatus(Long postId, Long userId) {
		Post findPost = postRepository.findPostByPostIdOrElseThrow(postId);
		User findUser = userRepository.findUserByUserIdOrElseThrow(userId); //게시물에 좋아요를 누르려는 회원 id

		//좋아요를 누르려는 사람=게시를 작성자 본인인 경우
		if (findPost.getUser().getUserId().equals(userId)) {
			throw new ValidateException(ResponseCode.CANNOT_LIKE_SELF_POST);
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
}
