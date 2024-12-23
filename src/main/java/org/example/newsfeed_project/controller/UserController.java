package org.example.newsfeed_project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.example.newsfeed_project.dto.LoginRequestDto;
import org.example.newsfeed_project.dto.SignUpRequestDto;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.service.UserService;
import org.example.newsfeed_project.session.SessionConst;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
	public ResponseEntity<String> signupUser(@RequestBody SignUpRequestDto signUpRequestDto) {

		userService.signupUser(signUpRequestDto);

		return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
	}

	//로그인
	@PostMapping("/login")
	public ResponseEntity<String> login(@Validated @RequestBody LoginRequestDto loginRequestDto,
		HttpServletRequest request) {

		User loginUser = userService.login(loginRequestDto);

		//로그인 성공 처리
		HttpSession session = request.getSession();
		session.setAttribute(SessionConst.LOGIN_USER_NAME, loginUser.getUserName());
		session.setAttribute(SessionConst.LOGIN_USER_ID, loginUser.getUserId());
		session.setAttribute(SessionConst.USER_STATUS, loginUser.getStatus());

		return ResponseEntity.status(HttpStatus.OK).body("로그인 되었습니다.");
	}

}
