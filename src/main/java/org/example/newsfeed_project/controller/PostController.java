package org.example.newsfeed_project.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed_project.dto.PostRequestDto;
import org.example.newsfeed_project.dto.PostResponseDto;
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

    //게시글 생성
    @PostMapping
    public ResponseEntity<PostResponseDto> createdPost(@RequestBody PostRequestDto createdPostRequest) {
        PostResponseDto createdPostResponse = postService.createdPost(createdPostRequest);
        return new ResponseEntity<>(createdPostResponse, HttpStatus.OK);
    }
}





