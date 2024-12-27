package org.example.newsfeed_project.follow.service;

import java.util.List;

import org.example.newsfeed_project.common.exception.ResponseCode;
import org.example.newsfeed_project.common.exception.ValidateException;
import org.example.newsfeed_project.dto.MessageDto;
import org.example.newsfeed_project.entity.Follow;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.follow.dto.FollowUserInfoDto;
import org.example.newsfeed_project.follow.dto.FollowersDto;
import org.example.newsfeed_project.follow.dto.FollowingsDto;
import org.example.newsfeed_project.follow.repository.FollowRepository;
import org.example.newsfeed_project.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {
	private final FollowRepository followRepository;
	private final UserRepository userRepository;

	// 팔로우하기
	@Transactional(rollbackFor = Exception.class)
	public MessageDto follow(Long userId, Long loginUserId) {
		// 팔로우 한 유저 조회, 없을 시 404 Not found 반환
		User loginUser = userRepository.findUserByUserIdOrElseThrow(loginUserId);
		// 팔로우 당한 유저 조회, 없을 시 404 Not found 반환
		User followingUser = userRepository.findUserByUserIdOrElseThrow(userId);

		// (로그인 유저가) 팔로우 한 유저 -> 로그인 유저 팔로잉 했는지 확인 (들어온 요청이 있는지 확인)
		boolean isExitstingFollow = verifyFollowRequst(followingUser, loginUser);

		// 팔로우 관계 생성
		Follow follow = Follow.builder()
			.follower(loginUser)
			.following(followingUser)
			.isApprove(isExitstingFollow) // 존재하면 true(승인), 아니면 false(요청)
			.build();
		followRepository.save(follow);

		// 존재하면 요청에 대한 승인
		if (isExitstingFollow) {
			// (로그인 유저가) 팔로잉 한 유저 -> 로그인 한 유저 관계
			Follow followingUserFollow = findByFollowRelation(followingUser, loginUser);
			followingUserFollow.setIsApprove(true); // 요청이 승인 되었으므로 true
			followRepository.save(followingUserFollow); // 저장
		}

		return new MessageDto(followingUser.getUserName() + "님을 팔로우 했습니다.");
	}

	// 팔로우 요청에 대한 검증
	private boolean verifyFollowRequst(User followingUser, User loginUser) {
		// 본인이 본인 팔로우 하는지 확인 (userId 일치한지 확인)
		if (loginUser.getUserId().equals(followingUser.getUserId())) {
			throw new ValidateException(ResponseCode.CANNOT_SELF_FOLLOW);
		}

		// 이미 팔로우 중인지 확인
		if (followRepository.existsByFollowerAndFollowing(loginUser, followingUser)) {
			throw new ValidateException(ResponseCode.USER_ALREADY_FOLLOW);
		}
		return followRepository.existsByFollowerAndFollowing(followingUser, loginUser);
	}

	// 언팔로우(팔로우 취소)
	public MessageDto unFollow(Long userId, Long loginUserId) {
		// 팔로우 한 유저 조회, 없을 시 404 Not found 반환
		User loginUser = userRepository.findUserByUserIdOrElseThrow(loginUserId);
		// 팔로우 당한 유저 조회, 없을 시 404 Not found 반환
		User followingUser = userRepository.findUserByUserIdOrElseThrow(userId);
		// 팔로우 되어 있는지 확인
		Follow follow = findByFollowRelation(followingUser, loginUser);

		// 팔로우 관계 삭제
		followRepository.delete(follow);

		return new MessageDto(followingUser.getUserName() + "님을 팔로우 취소했습니다.");
	}

	// 팔로우 관계 조회
	public Follow findByFollowRelation(User loginUser, User followingUser) {
		// 팔로우 되어 있는지 확인
		return followRepository.findByFollowerAndFollowing(loginUser, followingUser)
			.orElseThrow(() -> new ValidateException(ResponseCode.NOT_FOLLOW_RELATION));
	}

	// user_id의 팔로워 목록 조회
	public FollowersDto getFollowers(Long userId) {
		// 유저 조회
		User user = userRepository.findUserByUserIdOrElseThrow(userId);

		// 유저의 팔로워 목록 -> 팔로잉Id가 유저인 것들 select
		List<FollowUserInfoDto> followers = followRepository.findFollowersInfoByUser(user);

		return new FollowersDto(user.getUserName(), followers);
	}

	// user_id의 팔로잉 목록 조회
	public FollowingsDto getFollowings(Long userId) {
		// 유저 조회
		User user = userRepository.findUserByUserIdOrElseThrow(userId);

		// 유저의 팔로워 목록 -> 팔로잉Id가 유저인 것들 select
		List<FollowUserInfoDto> followings = followRepository.findFollowingsInfoByUser(user);

		return new FollowingsDto(user.getUserName(), followings);
	}
}
