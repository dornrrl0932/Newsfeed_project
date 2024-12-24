package org.example.newsfeed_project.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.dto.NewFeedRequestDto;
import org.example.newsfeed_project.dto.NewFeedResponseDto;
import org.example.newsfeed_project.dto.PostCreateRequestDto;
import org.example.newsfeed_project.dto.PostCreateResponseDto;
import org.example.newsfeed_project.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final Long PAGESIZE = 10L;
    private final PostService postService;

    @GetMapping("/page/{page}")
    public List<NewFeedResponseDto> newFeedResponseDtos(@PathVariable Long page, @RequestBody NewFeedRequestDto requestDto) {
        List<NewFeedResponseDto> find = postService.findPostAll(page, PAGESIZE, requestDto).get(page);
        System.out.println(page);
        return find;
    }

    @PostMapping("/")
    public PostCreateResponseDto postCreate(@RequestBody PostCreateRequestDto requestDto, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("loginUserId");
        return postService.createPost(requestDto, 1L);
    }


}
