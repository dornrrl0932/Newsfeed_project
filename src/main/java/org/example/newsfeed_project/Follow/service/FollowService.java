package org.example.newsfeed_project.Follow.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.Follow.dto.FollowDto;
import org.example.newsfeed_project.Follow.repository.FollowRepository;
import org.example.newsfeed_project.User.repository.UserRepository;
import org.example.newsfeed_project.entity.Follow;
import org.example.newsfeed_project.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    // 팔로우하기
    public FollowDto follow(Long user_id, Long loginUserId){
        // 팔로우 한 유저 조회, 없을 시 404 Not found 반환
        User loginUser = userRepository.findById(loginUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않은 회원입니다."));
        // 팔로우 당한 유저 조회, 없을 시 404 Not found 반환
        User followingUser = userRepository.findById(user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않은 회원입니다."));

        Follow follow = new Follow(followingUser, loginUser);
        followRepository.save(follow);

        return new FollowDto(followingUser.getUserName() + "님을 팔로우 했습니다.");
    }
}
