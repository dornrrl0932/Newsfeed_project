package org.example.newsfeed_project.post.controller;

import org.example.newsfeed_project.common.session.SessionConst;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.post.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed_project.post.dto.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/page/{page}")
    public List<PostPageDto> findPostByPage(@PathVariable int page, @RequestBody PostFindByPageRequestDto requestDto) {
        int pageSize = 10;
        List<PostPageDto> test =  postService.findPostByPage(page, pageSize, requestDto);
        System.out.println("실행");
        for(PostPageDto postPageDto : test){
            System.out.println(postPageDto.getUpdateAt());
        }
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
