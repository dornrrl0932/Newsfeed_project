package org.example.newsfeed_project.post.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.post.dto.PostFindByPageRequestDto;
import org.example.newsfeed_project.post.dto.PostFindByPageResponseDto;
import org.example.newsfeed_project.post.dto.PostFindDetailByIdResponseDto;

import org.example.newsfeed_project.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/page/{page}")
    public List<PostFindByPageResponseDto> findPostByPage(@PathVariable Long page, @RequestBody PostFindByPageRequestDto requestDto) {
        Long pageSize = 10L;
        return postService.findPostByPage(page, pageSize, requestDto).get(page);
    }

    @GetMapping("/{post_id}")
    public PostFindDetailByIdResponseDto findPostById(@PathVariable Long post_id){
        return findPostById(post_id);
    }
}
