package org.example.newsfeed_project.Follow.repository;

import org.example.newsfeed_project.entity.Follow;
import org.example.newsfeed_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // 팔로우 관계 없을 경우를 대비해 Optional<> 사용 ( .orElseThrow() )
    Optional<Follow> findByFollowingAndFollower(User followingUser, User loginUser);
}
