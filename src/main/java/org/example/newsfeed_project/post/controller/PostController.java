package org.example.newsfeed_project.post.controller;

import org.example.newsfeed_project.common.session.SessionConst;
import org.example.newsfeed_project.dto.ApiResponse;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.post.dto.CreatedPostRequestDto;
import org.example.newsfeed_project.post.dto.CreatedPostResponseDto;
import org.example.newsfeed_project.post.dto.LikeNumResponseDto;
import org.example.newsfeed_project.post.dto.PostFindByDateRangeRequestDto;
import org.example.newsfeed_project.post.dto.PostFindByPageRequestDto;
import org.example.newsfeed_project.post.dto.PostFindDetailByIdResponseDto;
import org.example.newsfeed_project.post.dto.PostListDto;
import org.example.newsfeed_project.post.dto.UpdatedPostRequestDto;
import org.example.newsfeed_project.post.dto.UpdatedPostResponseDto;
import org.example.newsfeed_project.post.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	//게시물 생성
	@PostMapping
	public ResponseEntity<ApiResponse<CreatedPostResponseDto>> createdPost(HttpServletRequest request,
		@RequestBody CreatedPostRequestDto createdPostRequest) {
		HttpSession session = request.getSession();
		Long userId = (Long)session.getAttribute(SessionConst.LOGIN_USER_ID);
		CreatedPostResponseDto createdPostResponse = postService.createdPost(userId, createdPostRequest);
		return ResponseEntity.ok(ApiResponse.success(201, "게시물 작성 성공", createdPostResponse));
	}

	// 기간별 조회
	@GetMapping("/dateRange/{page}")
	public ResponseEntity<ApiResponse<PostListDto>> findPostsByDateRange(@RequestParam(defaultValue = "1") int page,
		@RequestBody PostFindByDateRangeRequestDto requestDto) {
		int pageSize = 10;

		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt"));

		return ResponseEntity.ok(
			ApiResponse.success(200, "게시물 기간 별 작성 성공", postService.findPostByDateRange(pageable, requestDto)));
	}

	// 게시물 전체 조회
	@GetMapping("/page/{page}")
	public ResponseEntity<ApiResponse<PostListDto>> findPostByPage(@RequestParam(defaultValue = "1") int page,
		@RequestBody PostFindByPageRequestDto requestDto) {
		int pageSize = 10;

		return ResponseEntity.ok(
			ApiResponse.success(200, "전체 피드 조회 성공", postService.findPostByPage(page, pageSize, requestDto)));
	}

	//게시물 단건 조회
	@GetMapping("/{post_id}")
	public ResponseEntity<ApiResponse<PostFindDetailByIdResponseDto>> findPostByPostId(
		@PathVariable(name = "post_id") Long postId) {

		return ResponseEntity.ok(
			ApiResponse.success(200, "게시물 단건 조회 성공", postService.findPostByPostId(postId)));
	}

	//게시글 좋아요 상태 토글
	@PutMapping("/{post_id}/{user_id}/like")
	public ResponseEntity<ApiResponse<LikeNumResponseDto>> toggleLikeStatus(@PathVariable(name = "post_id") Long postId,
		@PathVariable(name = "user_id") Long userId) {

		Post post = postService.toggleLikeStatus(postId, userId);

		return ResponseEntity.ok(
			ApiResponse.success(200, "게시글 좋아요 상태 토글 실행 성공", new LikeNumResponseDto(post.getLikeCount())));
	}

	//게시물 수정
	@PatchMapping("/{post_id}")
	public ResponseEntity<ApiResponse<UpdatedPostResponseDto>> updatedPost(HttpServletRequest request,
		@PathVariable(name = "post_id") Long postId,
		@RequestBody UpdatedPostRequestDto updatedPostRequest) {
		HttpSession session = request.getSession();
		Long userId = (Long)session.getAttribute(SessionConst.LOGIN_USER_ID);
		UpdatedPostResponseDto updatedPostResponse = postService.updatePost(userId, postId, updatedPostRequest);
		return ResponseEntity.ok(
			ApiResponse.success(200, "게시글 수정 성공", updatedPostResponse));
	}

	//게시물 삭제
	@DeleteMapping("/{post_id}")
	public ResponseEntity<ApiResponse<Void>> deletedPost(HttpServletRequest request,
		@PathVariable(name = "post_id") Long postId) {
		HttpSession session = request.getSession();
		Long userId = (Long)session.getAttribute(SessionConst.LOGIN_USER_ID);
		postService.deletePost(userId, postId);
		return ResponseEntity.ok(
			ApiResponse.success(200, "게시글 삭제 성공", null));
	}
}
