package org.example.newsfeed_project.profile.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.profile.dto.ProfileUpdateRequestDto;
import org.example.newsfeed_project.profile.dto.ProfileUpdateResponseDto;
import org.example.newsfeed_project.profile.dto.ProfileDto;
import org.example.newsfeed_project.profile.service.ProfileService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@RequestMapping("/users/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    @GetMapping("/{user_id}")

    // 프로필 조회
    public ResponseEntity<ProfileDto> getProfile(@PathVariable Long user_id, @RequestParam(defaultValue = "1") int pageNum) {
        // 기본 1페이지(인덱스와 같은 개념이라 요청 들어온 페이지 숫자에 -1 처리), 게시글 10개 씩
        Pageable pageable = PageRequest.of(pageNum-1, 10);
        return ResponseEntity.ok(profileService.getProfile(user_id, pageable));
    }

    @PutMapping("/{id}")
    public ProfileUpdateResponseDto updateProfile(@PathVariable Long id, @RequestBody ProfileUpdateRequestDto requestDtd , HttpServletRequest request) {
        Long sessionId = (Long) request.getSession().getAttribute("loginUserId");
        if(!Objects.equals(sessionId, id)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return profileService.updateProfile(id, requestDtd);
    }

}
