package org.example.newsfeed_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.dto.SignUpRequestDto;
import org.example.newsfeed_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("signup")
    public ResponseEntity<String> signupUser (@RequestBody SignUpRequestDto signUpRequestDto) {

        userService.signupUser(signUpRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

}
