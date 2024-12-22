package org.example.newsfeed_project.Follow.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.Follow.dto.MessageDto;
import org.example.newsfeed_project.Follow.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    //팔로우하기
    @PostMapping("/{user_id}")
    public ResponseEntity<MessageDto> follow(@PathVariable Long user_id){
        Long loginUserId = 1L; // 로그인 기능 구현 후 수정 필요
        return new ResponseEntity<>(followService.follow(user_id, loginUserId), HttpStatus.OK);
    }

    // 팔로우 취소
    @DeleteMapping("/{user_id}")
    public ResponseEntity<MessageDto> unFollow(@PathVariable Long user_id){
        Long loginUserId = 1L; // 로그인 기능 구현 후 수정 필요
        return new ResponseEntity<>(followService.unFollow(user_id, loginUserId), HttpStatus.OK);
    }
}
