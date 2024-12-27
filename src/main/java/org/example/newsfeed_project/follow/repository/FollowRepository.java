package org.example.newsfeed_project.follow.repository;

import java.util.List;
import java.util.Optional;

import org.example.newsfeed_project.entity.Follow;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.follow.dto.FollowUserInfoDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
	// 팔로우 관계 없을 경우를 대비해 Optional<> 사용 ( .orElseThrow() )
	Optional<Follow> findByFollowerAndFollowing(User followerUser, User followingUser);

	// 팔로잉 수
	Long countByFollowing(User user);

	// 팔로워 수
	Long countByFollower(User user);

	// 팔로우 관계가 존재하는지 확인
	Boolean existsByFollowerAndFollowing(User followerUser, User followingUser);

	// 팔로워 정보 조회 (following_id가 user_id인 row 조회)
	@Query("SELECT new org.example.newsfeed_project.follow.dto.FollowUserInfoDto(" + // dto 생성
		"u.userName, u.introduction, " +
		"CASE WHEN f.isApprove = true THEN '맞팔로우' ELSE '승인 전' END) " +
		"FROM Follow f " +
		"JOIN f.follower u " +  // follower_id와 연관된 유저 정보 가져옴
		"WHERE f.following = :user" // 팔로잉 유저 파라미터 user와 같은 것들만
	)
	List<FollowUserInfoDto> findFollowersInfoByUser(@Param("user") User user);

	// 팔로잉 정보 조회 (follower_id가 user_id인 row 조회)
	@Query("SELECT new org.example.newsfeed_project.follow.dto.FollowUserInfoDto(" + // dto 생성
		"u.userName, u.introduction, " +
		"CASE WHEN f.isApprove = true THEN '맞팔로우' ELSE '요청 중' END) " +
		"FROM Follow f " +
		"JOIN f.following u " +  // follower_id와 연관된 유저 정보 가져옴
		"WHERE f.follower = :user" // 팔로잉 유저 파라미터 user와 같은 것들만
	)
	List<FollowUserInfoDto> findFollowingsInfoByUser(@Param("user") User user);

	Optional<Object> findByFollowerUserId(Long userId);

	@Transactional
	@Modifying
	@Query("DELETE " +
		"FROM Follow f " +
		"WHERE f.follower.userId = :userId " +
		"OR f.following.userId = :userId")
	void deleteByFollowerOrFollowing(Long userId);  // 유저와 연관된 팔로우 관계 삭제
}
