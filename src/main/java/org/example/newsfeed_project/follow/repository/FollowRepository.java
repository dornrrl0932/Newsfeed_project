package org.example.newsfeed_project.follow.repository;

import java.util.Optional;

import org.example.newsfeed_project.entity.Follow;
import org.example.newsfeed_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
	// 팔로우 관계 없을 경우를 대비해 Optional<> 사용 ( .orElseThrow() )
	Optional<Follow> findByFollowerAndFollowing(User followerUser, User followingUser);

	// 팔로잉 수
	Long countByFollowing(User user);

	// 팔로워 수
	Long countByFollower(User user);

	// 팔로우 관계가 존재하는지 확인
	Boolean existsByFollowerAndFollowing(User followerUser, User followingUser);
}
