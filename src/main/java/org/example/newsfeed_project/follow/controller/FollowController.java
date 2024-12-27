package org.example.newsfeed_project.follow.controller;

import org.example.newsfeed_project.common.session.SessionConst;
import org.example.newsfeed_project.dto.ApiResponse;
import org.example.newsfeed_project.dto.MessageDto;
import org.example.newsfeed_project.follow.dto.FollowersDto;
import org.example.newsfeed_project.follow.dto.FollowingsDto;
import org.example.newsfeed_project.follow.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users/follow")
@RequiredArgsConstructor
public class FollowController {
	private final FollowService followService;

	//팔로우하기
	@PostMapping("/{user_id}")
	public ResponseEntity<ApiResponse<MessageDto>> follow(@PathVariable(name = "user_id") Long userId,
		HttpServletRequest servletRequest) {
		// 세션 get. 새로 생성은 안 함.
		HttpSession httpSession = servletRequest.getSession(false);
		Long loginUserId = (Long)httpSession.getAttribute(SessionConst.LOGIN_USER_ID);
		return ResponseEntity.ok(ApiResponse.success(200, "팔로우 성공", followService.follow(userId, loginUserId)));
	}

	// 팔로우 취소
	@DeleteMapping("/{user_id}")
	public ResponseEntity<ApiResponse<MessageDto>> unFollow(@PathVariable(name = "user_id") Long userId,
		HttpServletRequest servletRequest) {
		// 세션 get. 새로 생성은 안 함.
		HttpSession httpSession = servletRequest.getSession(false);
		Long loginUserId = (Long)httpSession.getAttribute(SessionConst.LOGIN_USER_ID);

		return ResponseEntity.ok(ApiResponse.success(200, "팔로우 취소 성공", followService.unFollow(userId, loginUserId)));
	}

	// 팔로워 목록
	@GetMapping("/{user_id}/followers")
	public ResponseEntity<ApiResponse<FollowersDto>> getFollowers(@PathVariable(name = "user_id") Long userId) {
		return ResponseEntity.ok(ApiResponse.success(200, "팔로워 목록 조회 성공", followService.getFollowers(userId)));
	}

	// 팔로잉 목록
	@GetMapping("/{user_id}/followings")
	public ResponseEntity<ApiResponse<FollowingsDto>> getFollowing(@PathVariable(name = "user_id") Long userId) {
		return ResponseEntity.ok(ApiResponse.success(200, "팔로잉 목록 조회 성공", followService.getFollowings(userId)));
	}
}
