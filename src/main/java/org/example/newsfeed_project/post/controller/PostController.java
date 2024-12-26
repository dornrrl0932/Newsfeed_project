package org.example.newsfeed_project.post.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.post.dto.*;

import org.example.newsfeed_project.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

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

    @GetMapping("/{post_id}")
    public PostFindDetailByIdResponseDto findPostById(@PathVariable Long post_id) {
        return findPostById(post_id);
    }
}
