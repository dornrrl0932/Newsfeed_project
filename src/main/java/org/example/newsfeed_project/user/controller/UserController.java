package org.example.newsfeed_project.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.user.dto.CancelRequestDto;
import org.example.newsfeed_project.user.dto.SignUpRequestDto;
import org.example.newsfeed_project.exception.InvalidUrlException;
import org.example.newsfeed_project.user.service.UserService;
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

        //회원가입 완료 시 CREATED 상태코드와 메세지 반환
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }


    //잘못 된 경로 예외처리
    @RequestMapping("*")
    public void handleInvalidUrl() {
        //InvalidUrlException를 통해 예외처리
        throw new InvalidUrlException("잘못 된 URL입니다. 다시 입력해주세요.");
    }

    //회원탈퇴
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> CancelUser (@PathVariable Long userId, @RequestBody CancelRequestDto cancelRequestDto) {

        userService.CancelUser(userId, cancelRequestDto);

        //탈퇴 성공 시 OK 상태코드와 메세지 반환
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 탈퇴 되었습니다. 감사합니다.");
    }
}
