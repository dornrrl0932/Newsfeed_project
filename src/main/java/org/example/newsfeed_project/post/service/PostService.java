package org.example.newsfeed_project.post.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.example.newsfeed_project.entity.PostLike;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.post.dto.PostFindByPageRequestDto;
import org.example.newsfeed_project.post.dto.PostFindByPageResponseDto;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.post.repository.PostLikeRepository;
import org.example.newsfeed_project.post.repository.PostRepository;
import org.example.newsfeed_project.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {
	public final PostRepository postRepository;
	public final PostLikeRepository postLikeRepository;
	public final UserRepository userRepository;

	public Map<Long, List<PostFindByPageResponseDto>> findPostByPage(Long requestPage, Long pageSize,
		PostFindByPageRequestDto requestDto) {
		List<Post> findPostList = postRepository.findAll();
		return findPostByUpdateOrLikeDesc(findPostList, pageSize, requestPage, requestDto);
	}

	//게시물 id로 게시물 조회
	public Post findPostByPostId(Long id) {
		Optional<Post> optionalPost = postRepository.findById(id);
		if (optionalPost.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "찾으려는 게시물이 존재하지 않습니다. post_id: " + id);
		}
		return optionalPost.get();
	}

	private Map<Long, List<PostFindByPageResponseDto>> findPostByUpdateOrLikeDesc(List<Post> findPostList,
		Long pageSize, Long requestPage, PostFindByPageRequestDto requestDto) {
		Map<Long, List<PostFindByPageResponseDto>> result = new HashMap<>();
		List<PostFindByPageResponseDto> postFindByPageResponseDtoArrayList = new ArrayList<>();
		Long pageCount = 0L;
		Long count = 0L;

		switch (requestDto.getOrder()) {
			case "like":
				findPostList.sort(Comparator.comparing(Post::getLikeCount).reversed());
				break;
			case "update":
				findPostList.sort(Comparator.comparing(Post::getUpdatedAt).reversed());
				break;
			default:
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}

		for (Post post : findPostList) {
			if (count == pageSize) {
				postFindByPageResponseDtoArrayList.clear();
				count = 0L;
				pageCount++;
			}
			if (count == 0) {
				result.put(pageCount, new ArrayList<>(postFindByPageResponseDtoArrayList));
			}
			if (pageCount > requestPage) {
				break;
			}
			result.get(pageCount)
				.add(new PostFindByPageResponseDto(post.getTitle(), post.getContents(), post.getUser().getUserName(),
					post.getUpdatedAt()));
			count++;
		}
		return result;
	}

	//좋아요 상태 토글
	@Transactional
	public Post toggleLikeStatus(Long postId, Long userId) {
		Post findPost = findPostByPostId(postId);
		User findUser = userRepository.findUserByUserIdOrElseThrow(userId); //게시물에 좋아요를 누르려는 회원 id

		//좋아요를 누르려는 사람=게시를 작성자 본인인 경우
		if (findPost.getUser().getUserId().equals(userId)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "작성자 본인의 게시글에는 좋아요를 누를 수 없습니다.");
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
