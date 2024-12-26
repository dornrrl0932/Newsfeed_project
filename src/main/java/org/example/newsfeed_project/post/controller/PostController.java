package org.example.newsfeed_project.post.controller;

import org.example.newsfeed_project.common.session.SessionConst;
import org.example.newsfeed_project.dto.ApiResponse;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.post.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed_project.post.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	//친구 게시물 보기
	@GetMapping("/pageFriend/{page}")
	public List<PostPageDto> getPostsBySessionUser(@PathVariable int page, HttpServletRequest request, @RequestBody PostFindByPageRequestDto requestDto) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute(SessionConst.LOGIN_USER_ID);
		Pageable pageable;
		if (userId == null) {
			throw new IllegalStateException("User is not logged in");
		}
		switch (requestDto.getOrder()){
			case "update" : pageable  = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "updatedAt"));
				break;
			case "like" :  pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "likeCount"));
				break;
			default: throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
		}


		return postService.getPostsByFriend(userId,pageable);
	}

	// 게시물 전체 조회
    @GetMapping("/dateRange/{page}")
    public List<PostPageDto> findPostsByDateRange(@PathVariable int page, @RequestBody PostFindByDateRangeRequestDto requestDto) {
        int pageSize = 10;
        Pageable pageable;
        switch (requestDto.getOrder()) {
            case "like":
                pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "likeCount"));
                break;
            case "update":
                pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt"));
                break;
            default: throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
      return postService.findPostByDateRange(pageable, requestDto);
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
