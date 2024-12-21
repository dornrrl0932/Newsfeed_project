package org.example.newsfeed_project.Follow.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.Follow.dto.FollowDto;
import org.example.newsfeed_project.Follow.repository.FollowRepository;
import org.example.newsfeed_project.User.repository.UserRepository;
import org.example.newsfeed_project.entity.Follow;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.exception.ValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    // 팔로우하기
    public FollowDto follow(Long user_id, Long loginUserId){
        // 팔로우 한 유저 조회, 없을 시 404 Not found 반환
        User loginUser = userRepository.findById(loginUserId)
                .orElseThrow(() -> new ValidateException("존재하지 않은 회원입니다.", HttpStatus.NOT_FOUND));
        // 팔로우 당한 유저 조회, 없을 시 404 Not found 반환
        User followingUser = userRepository.findById(user_id)
                .orElseThrow(() -> new ValidateException("존재하지 않은 회원입니다.", HttpStatus.NOT_FOUND));

        Follow follow = new Follow(followingUser, loginUser);
        followRepository.save(follow);

        return new FollowDto(followingUser.getUserName() + "님을 팔로우 했습니다.");
    }

    // 언팔로우(팔로우 취소)
    public FollowDto unFollow(Long user_id, Long loginUserId){
        // 팔로우 한 유저 조회, 없을 시 404 Not found 반환
        User loginUser = userRepository.findById(loginUserId)
                .orElseThrow(() -> new ValidateException("존재하지 않은 회원입니다.", HttpStatus.NOT_FOUND));
        // 팔로우 당한 유저 조회, 없을 시 404 Not found 반환
        User followingUser = userRepository.findById(user_id)
                .orElseThrow(() -> new ValidateException("존재하지 않은 회원입니다.", HttpStatus.NOT_FOUND));

        // 팔로우 되어 있는지 확인
        Follow follow = followRepository.findByFollowingAndFollower(followingUser, loginUser)
                .orElseThrow(() -> new ValidateException("팔로잉 되어 있지 않은 회원입니다.", HttpStatus.BAD_REQUEST));

        // 팔로우 관계 삭제
        followRepository.delete(follow);

        return new FollowDto(followingUser.getUserName() + "님을 팔로우 취소했습니다.");
    }
}
