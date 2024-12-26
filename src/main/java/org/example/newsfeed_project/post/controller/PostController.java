package org.example.newsfeed_project.post.controller;

import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.post.dto.LikeNumResponseDto;
import org.example.newsfeed_project.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

import org.example.newsfeed_project.post.dto.PostFindByPageRequestDto;
import org.example.newsfeed_project.post.dto.PostFindByPageResponseDto;
import org.example.newsfeed_project.post.dto.PostFindDetailByIdResponseDto;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	//게시물 전체 조회
	@GetMapping("/page/{page}")
	public List<PostFindByPageResponseDto> findPostByPage(@PathVariable Long page,
		@RequestBody PostFindByPageRequestDto requestDto) {
		Long pageSize = 10L;
		return postService.findPostByPage(page, pageSize, requestDto).get(page);
	}

	//게시물 단건 조회
	@GetMapping("/{post_id}")
	public PostFindDetailByIdResponseDto findPostByPostId(@PathVariable Long post_id) {
		Post findPost = postService.findPostByPostId(post_id);
		return new PostFindDetailByIdResponseDto(findPost.getTitle(), findPost.getContents(),
			findPost.getUser().getUserName(), findPost.getUpdatedAt());
	}

	//게시글 좋아요 상태 토글
	@PutMapping("/{post_id}/{user_id}/like")
	public ResponseEntity<LikeNumResponseDto> toggleLikeStatus(@PathVariable Long post_id, @PathVariable Long user_id) {

		Post post = postService.toggleLikeStatus(post_id, user_id);

		return new ResponseEntity<>(new LikeNumResponseDto(post.getLikeCount()), HttpStatus.OK);
	}
}
