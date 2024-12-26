package org.example.newsfeed_project.user.controller;

import org.example.newsfeed_project.common.exception.InvalidUrlException;
import org.example.newsfeed_project.common.exception.ResponseCode;
import org.example.newsfeed_project.common.session.SessionConst;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.user.dto.CancelRequestDto;
import org.example.newsfeed_project.user.dto.LoginRequestDto;
import org.example.newsfeed_project.user.dto.SignUpRequestDto;
import org.example.newsfeed_project.user.dto.UpdateUserInfoRequestDto;
import org.example.newsfeed_project.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	//회원가입
	@PostMapping("/signup")
	public ResponseEntity<String> signupUser(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {

		userService.signupUser(signUpRequestDto);

		//회원가입 완료 시 CREATED 상태코드와 메세지 반환
		return ResponseEntity.status(ResponseCode.SUCCESS_SIGNUP.getStatus())
			.body(ResponseCode.SUCCESS_SIGNUP.getMessage());
	}

	//잘못 된 경로 예외처리
	@RequestMapping("*")
	public void handleInvalidUrl() {
		//InvalidUrlException를 통해 예외처리
		throw new InvalidUrlException(ResponseCode.URL_NOT_FOUND.getMessage());
	}

	//회원탈퇴
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> CancelUser(@PathVariable Long userId,
		@RequestBody CancelRequestDto cancelRequestDto) {

		userService.CancelUser(userId, cancelRequestDto);

		//탈퇴 성공 시 OK 상태코드와 메세지 반환
		return ResponseEntity.status(ResponseCode.SUCCESS_DELETE_USER.getStatus())
			.body(ResponseCode.SUCCESS_DELETE_USER.getMessage());
	}

	//회원 정보 수정
	@PatchMapping("/{userId}")
	public ResponseEntity<String> updateUserInfo(
		@PathVariable Long userId,
		@Validated @RequestBody UpdateUserInfoRequestDto dto) {

		userService.updateUserInfo(userId, dto);

		return ResponseEntity.status(ResponseCode.SUCCESS_UPDATE.getStatus())
			.body(ResponseCode.SUCCESS_UPDATE.getMessage());
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

		return ResponseEntity.status(ResponseCode.SUCCESS_LOGIN.getStatus())
			.body(ResponseCode.SUCCESS_LOGIN.getMessage());
	}

	//로그아웃
	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {

		//세션 조회
		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate(); //남은 세션 모두 삭제
		}

		return ResponseEntity.status(ResponseCode.SUCCESS_LOGOUT.getStatus())
			.body(ResponseCode.SUCCESS_LOGOUT.getMessage());
	}
}
