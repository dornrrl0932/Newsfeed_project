package org.example.newsfeed_project.profile.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.follow.repository.FollowRepository;
import org.example.newsfeed_project.user.repository.UserRepository;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.exception.ValidateException;
import org.example.newsfeed_project.post.dto.PostPageDto;
import org.example.newsfeed_project.post.repository.PostRepository;
import org.example.newsfeed_project.profile.dto.ProfileDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProfileService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    // 프로필 조회
    public ProfileDto getProfile(Long user_id, Pageable pageable) {
        // 해당 유저 조회
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new ValidateException("존재하지 않은 회원입니다.", HttpStatus.NOT_FOUND));

        // 게시물 페이지로 갖고오고 List<PostPageDto>로 변환
        List<PostPageDto> posts = PostPageDto.convertFrom(postRepository.findByUserOrderByUpdatedDateDesc(user, pageable));

        // (user_id를) 팔로잉 한 유저의 수 -> 팔로우 한 유저를 알아야 함
        Long followingNum = followRepository.countByFollower(user);
        // (user_id가) 팔로우 한 유저의 수 -> 팔로잉 한 유저를 알아야 함
        Long followerNum = followRepository.countByFollowing(user);

        return ProfileDto.convertFrom(user, followingNum, followerNum, posts);
    }
}
