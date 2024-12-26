package org.example.newsfeed_project.profile.controller;

import java.util.Objects;

import org.example.newsfeed_project.common.exception.ResponseCode;
import org.example.newsfeed_project.common.exception.ValidateException;
import org.example.newsfeed_project.dto.ApiResponse;
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

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users/profile")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;

	// 프로필 조회
	@GetMapping("/{user_id}")
	public ResponseEntity<ApiResponse<ProfileDto>> getProfile(@PathVariable(name = "user_id") Long userId,
		@RequestParam(defaultValue = "1") int pageNum) {
		// 기본 1페이지(인덱스와 같은 개념이라 요청 들어온 페이지 숫자에 -1 처리), 게시글 10개 씩
		Pageable pageable = PageRequest.of(pageNum - 1, 10);
		return ResponseEntity.ok(
			ApiResponse.success(200, "프로필 조회 성공", profileService.getProfile(userId, pageable)));
	}

	// 프로필 업데이트
	@PutMapping("/{user_id}")
	public ResponseEntity<ApiResponse<ProfileUpdateResponseDto>> updateProfile(
		@PathVariable(name = "user_id") Long userId,
		@RequestBody ProfileUpdateRequestDto requestDtd, HttpServletRequest request) {
		Long sessionId = (Long)request.getSession().getAttribute("loginUserId");
		if (!Objects.equals(sessionId, userId)) {
			throw new ValidateException(ResponseCode.ID_MISMATCH);
		}
		return ResponseEntity.ok(
			ApiResponse.success(200, "자기소개 수정 완료", profileService.updateProfile(userId, requestDtd)));
	}


}
