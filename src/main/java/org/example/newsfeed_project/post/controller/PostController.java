package org.example.newsfeed_project.post.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.post.dto.PostFindByPageRequestDto;
import org.example.newsfeed_project.post.dto.PostFindByPageResponseDto;
import org.example.newsfeed_project.post.dto.PostFindDetailByIdResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed_project.post.dto.CreatedPostRequestDto;
import org.example.newsfeed_project.post.dto.CreatedPostResponseDto;
import org.example.newsfeed_project.post.dto.UpdatedPostRequestDto;
import org.example.newsfeed_project.post.dto.UpdatedPostResponseDto;
import org.example.newsfeed_project.post.service.PostService;
import org.example.newsfeed_project.user.session.SessionConst;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/page/{page}")
    public List<PostFindByPageResponseDto> findPostByPage(@PathVariable Long page, @RequestBody PostFindByPageRequestDto requestDto) {
        Long pageSize = 10L;
        return postService.findPostByPage(page, pageSize, requestDto).get(page);
    }

    @GetMapping("/{post_id}")
    public PostFindDetailByIdResponseDto findPostById(@PathVariable Long post_id){
        return findPostById(post_id);
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