package org.example.newsfeed_project.post.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed_project.post.dto.CreatedPostRequestDto;
import org.example.newsfeed_project.post.dto.CreatedPostResponseDto;
import org.example.newsfeed_project.post.dto.UpdatedPostRequestDto;
import org.example.newsfeed_project.post.dto.UpdatedPostResponseDto;
import org.example.newsfeed_project.post.service.PostService;
import org.example.newsfeed_project.user.session.SessionConst;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
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
    //게시물 수정
    @PatchMapping("/{post_id}")
    public ResponseEntity<UpdatedPostResponseDto> updatedPost(HttpServletRequest request, @PathVariable("post_id") Long post_id,
                                                              @RequestBody UpdatedPostRequestDto updatedPostRequest) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(SessionConst.LOGIN_USER_ID);
        UpdatedPostResponseDto updatedPostResponse = postService.updatePost(userId, post_id, updatedPostRequest);
        return ResponseEntity.ok(updatedPostResponse);
    }
    //게시물 삭제
    @DeleteMapping("/{post_id}")
    public ResponseEntity<String> deletedPost(HttpServletRequest request, @PathVariable Long post_id) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(SessionConst.LOGIN_USER_ID);
        postService.deletePost(userId, post_id);
        return ResponseEntity.ok("게시글이 삭제 되었습니다.");
    }
}





