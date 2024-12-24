package org.example.newsfeed_project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed_project.dto.CreatedPostRequestDto;
import org.example.newsfeed_project.dto.CreatedPostResponseDto;
import org.example.newsfeed_project.dto.UpdatedPostRequestDto;
import org.example.newsfeed_project.dto.UpdatedPostResponseDto;
import org.example.newsfeed_project.repository.PostRepository;
import org.example.newsfeed_project.service.PostService;
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
    public ResponseEntity<CreatedPostResponseDto> createdPost(@RequestBody CreatedPostRequestDto createdPostRequest) {
        CreatedPostResponseDto createdPostResponse = postService.createdPost(createdPostRequest);
        return new ResponseEntity<>(createdPostResponse, HttpStatus.OK);
    }
    //게시물 수정
    @PatchMapping("/{post_id}")
    public ResponseEntity<UpdatedPostResponseDto> updatedPost(@PathVariable("post_id") Long post_id,
                            @RequestBody UpdatedPostRequestDto updatedPostRequest) {
        UpdatedPostResponseDto updatedPostResponse = postService.updatePost(post_id, updatedPostRequest);
        return new ResponseEntity<>(updatedPostResponse, HttpStatus.OK);
    }
    //게시물 삭제
//    @DeleteMapping("/{post_id}")
//    public ResponseEntity<String> deletedPost(@PathVariable Long post_id) {
//        postRepository.deleteById(post_id);
//        return ResponseEntity.ok("게시글이 삭제 되었습니다.");
//    }
}





