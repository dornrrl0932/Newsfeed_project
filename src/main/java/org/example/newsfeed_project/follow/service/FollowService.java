package org.example.newsfeed_project.follow.service;

import java.util.List;

import org.example.newsfeed_project.entity.Follow;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.exception.ValidateException;
import org.example.newsfeed_project.follow.dto.FollowUserInfoDto;
import org.example.newsfeed_project.follow.dto.FollowersDto;
import org.example.newsfeed_project.follow.dto.MessageDto;
import org.example.newsfeed_project.follow.repository.FollowRepository;
import org.example.newsfeed_project.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
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
	public MessageDto follow(Long user_id, Long loginUserId) {
		// 팔로우 한 유저 조회, 없을 시 404 Not found 반환
		User loginUser = userRepository.findById(loginUserId)
			.orElseThrow(() -> new ValidateException("존재하지 않은 회원입니다.", HttpStatus.NOT_FOUND));
		// 팔로우 당한 유저 조회, 없을 시 404 Not found 반환
		User followingUser = userRepository.findById(user_id)
			.orElseThrow(() -> new ValidateException("존재하지 않은 회원입니다.", HttpStatus.NOT_FOUND));

		// 본인이 본인 팔로우 하는지 확인 (userId 일치한지 확인)
		if (loginUser.getUserId().equals(followingUser.getUserId())) {
			throw new ValidateException("본인을 팔로우 할 수 없습니다.", HttpStatus.CONFLICT);
		}

		// 이미 팔로우 중인지 확인
		if (followRepository.existsByFollowerAndFollowing(loginUser, followingUser)) {
			throw new ValidateException((followingUser.getUserName() + "님은 이미 팔로우 중입니다."), HttpStatus.CONFLICT);
		}

		// (로그인 유저가) 팔로우 한 유저 -> 로그인 유저 팔로잉 했는지 확인 (들어온 요청이 있는지 확인)
		boolean isExitstingFollow = followRepository.existsByFollowerAndFollowing(followingUser, loginUser);

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

	// 언팔로우(팔로우 취소)
	public MessageDto unFollow(Long user_id, Long loginUserId) {
		// 팔로우 한 유저 조회, 없을 시 404 Not found 반환
		User loginUser = userRepository.findById(loginUserId)
			.orElseThrow(() -> new ValidateException("존재하지 않은 회원입니다.", HttpStatus.NOT_FOUND));
		// 팔로우 당한 유저 조회, 없을 시 404 Not found 반환
		User followingUser = userRepository.findById(user_id)
			.orElseThrow(() -> new ValidateException("존재하지 않은 회원입니다.", HttpStatus.NOT_FOUND));

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
			.orElseThrow(() -> new ValidateException("팔로잉 되어 있지 않은 회원입니다.", HttpStatus.BAD_REQUEST));
	}

	// user_id의 팔로워 목록 조회
	public FollowersDto getFollowers(Long user_id) {
		// 유저 조회
		User user = userRepository.findById(user_id)
			.orElseThrow(() -> new ValidateException("존재하지 않은 회원입니다.", HttpStatus.NOT_FOUND));

		// 유저의 팔로워 목록 -> 팔로잉Id가 유저인 것들 select
		List<FollowUserInfoDto> followers = followRepository.findFollowersInfoByUser(user);

		return new FollowersDto(user.getUserName(), followers);
	}
}
