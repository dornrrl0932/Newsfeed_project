package org.example.newsfeed_project.profile.controller;

import org.example.newsfeed_project.profile.dto.ProfileDto;
import org.example.newsfeed_project.profile.service.ProfileService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users/profile")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;

	// 프로필 조회
	@GetMapping("/{user_id}")
	public ResponseEntity<ProfileDto> getProfile(@PathVariable Long user_id,
		@RequestParam(defaultValue = "1") int pageNum) {
		// 기본 1페이지(인덱스와 같은 개념이라 요청 들어온 페이지 숫자에 -1 처리), 게시글 10개 씩
		Pageable pageable = PageRequest.of(pageNum - 1, 10);
		return ResponseEntity.ok(profileService.getProfile(user_id, pageable));
	}
}
