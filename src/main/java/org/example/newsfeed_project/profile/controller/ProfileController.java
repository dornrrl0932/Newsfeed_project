package org.example.newsfeed_project.profile.controller;

import java.util.Objects;

import org.example.newsfeed_project.common.exception.ResponseCode;
import org.example.newsfeed_project.profile.dto.ProfileDto;
import org.example.newsfeed_project.profile.dto.ProfileUpdateRequestDto;
import org.example.newsfeed_project.profile.dto.ProfileUpdateResponseDto;
import org.example.newsfeed_project.profile.service.ProfileService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users/profile")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;

	// 프로필 조회
	@GetMapping("/{user_id}")
	public ResponseEntity<ProfileDto> getProfile(@PathVariable(name = "user_id") Long userId,
		@RequestParam(defaultValue = "1") int pageNum) {
		// 기본 1페이지(인덱스와 같은 개념이라 요청 들어온 페이지 숫자에 -1 처리), 게시글 10개 씩
		Pageable pageable = PageRequest.of(pageNum - 1, 10);
		return ResponseEntity.ok(profileService.getProfile(userId, pageable));
	}

	@PutMapping("/{user_id}")
	public ProfileUpdateResponseDto updateProfile(@PathVariable(name = "user_id") Long userId,
		@RequestBody ProfileUpdateRequestDto requestDtd, HttpServletRequest request) {
		Long sessionId = (Long)request.getSession().getAttribute("loginUserId");
		if (!Objects.equals(sessionId, userId)) {
			throw new ResponseStatusException(ResponseCode.ID_MISMATCH.getStatus());
		}
		return profileService.updateProfile(userId, requestDtd);
	}

}
