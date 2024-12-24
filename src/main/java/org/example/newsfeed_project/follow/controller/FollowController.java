package org.example.newsfeed_project.follow.controller;

import org.example.newsfeed_project.follow.dto.FollowersDto;
import org.example.newsfeed_project.follow.dto.FollowingsDto;
import org.example.newsfeed_project.follow.dto.MessageDto;
import org.example.newsfeed_project.follow.service.FollowService;
import org.example.newsfeed_project.user.session.SessionConst;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<MessageDto> follow(@PathVariable Long user_id, HttpServletRequest servletRequest) {
		// 세션 get. 새로 생성은 안 함.
		HttpSession httpSession = servletRequest.getSession(false);
		Long loginUserId = (Long)httpSession.getAttribute(SessionConst.LOGIN_USER_ID);
		return new ResponseEntity<>(followService.follow(user_id, loginUserId), HttpStatus.OK);
	}

	// 팔로우 취소
	@DeleteMapping("/{user_id}")
	public ResponseEntity<MessageDto> unFollow(@PathVariable Long user_id, HttpServletRequest servletRequest) {
		// 세션 get. 새로 생성은 안 함.
		HttpSession httpSession = servletRequest.getSession(false);
		Long loginUserId = (Long)httpSession.getAttribute(SessionConst.LOGIN_USER_ID);

		return new ResponseEntity<>(followService.unFollow(user_id, loginUserId), HttpStatus.OK);
	}

	// 팔로워 목록
	@GetMapping("/{user_id}/followers")
	public ResponseEntity<FollowersDto> getFollowers(@PathVariable Long user_id) {
		return ResponseEntity.ok(followService.getFollowers(user_id));
	}

	@GetMapping("/{user_id}/followings")
	public ResponseEntity<FollowingsDto> getFollowing(@PathVariable Long user_id) {
		return ResponseEntity.ok(followService.getFollowings(user_id));
	}
}
