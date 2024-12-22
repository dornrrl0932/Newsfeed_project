package org.example.newsfeed_project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.dto.CancelRequestDto;
import org.example.newsfeed_project.dto.SignUpRequestDto;
import org.example.newsfeed_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("signup")
    public ResponseEntity<String> signupUser (@Valid @RequestBody SignUpRequestDto signUpRequestDto) {

        userService.signupUser(signUpRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

    //회원탈퇴
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> CancelUser (@PathVariable Long userId, @RequestBody CancelRequestDto cancelRequestDto) {

        userService.CancelUser(userId, cancelRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 탈퇴 되었습니다. 감사합니다.");
    }
}
