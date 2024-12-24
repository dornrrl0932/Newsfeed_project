package org.example.newsfeed_project.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.profile.dto.ProfileUpdateRequestDto;
import org.example.newsfeed_project.profile.dto.ProfileUpdateResponseDto;
import org.example.newsfeed_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserService userService;
    @PutMapping("/{id}")
    public ProfileUpdateResponseDto UserProfilePutController(@PathVariable Long id, @RequestBody ProfileUpdateRequestDto requestDtd , HttpServletRequest request) {
        Long sessionId = (Long) request.getSession().getAttribute("loginUserId");
        if(!Objects.equals(sessionId, id)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return userService.userProfilePutService(id, requestDtd);
    }
}
