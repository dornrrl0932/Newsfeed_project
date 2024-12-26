package org.example.newsfeed_project.post.controller;

import org.example.newsfeed_project.commen.session.SessionConst;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.post.service.PostService;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    //게시물 생성
    @PostMapping
    public ResponseEntity<CreatedPostResponseDto> createdPost(HttpServletRequest request,
                                                              @RequestBody CreatedPostRequestDto createdPostRequest) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(SessionConst.LOGIN_USER_ID);
        CreatedPostResponseDto createdPostResponse = postService.createdPost(userId, createdPostRequest);
        return ResponseEntity.ok(createdPostResponse);
    }

    @GetMapping("/dateRange/{page}")
    public List<PostPageDto> findPostsByDateRange(@RequestParam(defaultValue = "1") int page, @RequestBody PostFindByDateRangeRequestDto requestDto) {
        int pageSize = 10;
      return postService.findPostByDateRange(page, pageSize, requestDto);
    }

    @GetMapping("/page/{page}")
    public List<PostPageDto> findPostByPage(@RequestParam(defaultValue = "1") int page, @RequestBody PostFindByPageRequestDto requestDto) {
        int pageSize = 10;
        return postService.findPostByPage(page, pageSize, requestDto);
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

    //게시물 수정
    @PatchMapping("/{post_id}")
    public ResponseEntity<UpdatedPostResponseDto> updatedPost(HttpServletRequest request, @PathVariable(name = "post_id") Long postId,
                                                              @RequestBody UpdatedPostRequestDto updatedPostRequest) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(SessionConst.LOGIN_USER_ID);
        UpdatedPostResponseDto updatedPostResponse = postService.updatePost(userId, postId, updatedPostRequest);
        return ResponseEntity.ok(updatedPostResponse);
    }
    //게시물 삭제
    @DeleteMapping("/{post_id}")
    public ResponseEntity<String> deletedPost(HttpServletRequest request, @PathVariable(name = "post_id") Long postId) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(SessionConst.LOGIN_USER_ID);
        postService.deletePost(userId, postId);
        return ResponseEntity.ok("게시글이 삭제 되었습니다.");
    }
}
