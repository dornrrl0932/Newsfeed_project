package org.example.newsfeed_project.Follow.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.Follow.dto.FollowDto;
import org.example.newsfeed_project.Follow.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    //팔로우하기
    @PostMapping("/{user_id}")
    public ResponseEntity<FollowDto> follow(@PathVariable Long user_id){
        Long loginUserId = 1L; // 로그인 기능 구현 후 수정 필요
        return new ResponseEntity<>(followService.follow(user_id, loginUserId), HttpStatus.OK);
    }
}
