package org.example.newsfeed_project.user.controller;

import org.example.newsfeed_project.common.exception.InvalidUrlException;
import org.example.newsfeed_project.common.exception.ResponseCode;
import org.example.newsfeed_project.common.session.SessionConst;
import org.example.newsfeed_project.dto.*;

import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.user.dto.*;
import org.example.newsfeed_project.user.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<ApiResponse<MessageDto>> signupUser(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {

		userService.signupUser(signUpRequestDto);

		//회원가입 완료 시 CREATED 상태코드와 메세지 반환
		return ResponseEntity.ok(
			ApiResponse.success(201, "회원가입 성공", MessageDto.convertFrom("회원가입 되었습니다.")));
	}

	//잘못 된 경로 예외처리
	@RequestMapping("*")
	public void handleInvalidUrl() {
		//InvalidUrlException를 통해 예외처리
		throw new InvalidUrlException(ResponseCode.URL_NOT_FOUND);
	}

	//회원탈퇴
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse<MessageDto>> CancelUser(@PathVariable Long userId,
		@RequestBody CancelRequestDto cancelRequestDto) {

		userService.CancelUser(userId, cancelRequestDto);

		//탈퇴 성공 시 OK 상태코드와 메세지 반환
		return ResponseEntity.ok(
			ApiResponse.success(200, "회원탈퇴 성공", MessageDto.convertFrom("정상적으로 탈퇴 되었습니다. 감사합니다.")));
	}

	//회원 정보 수정
	@PatchMapping("/{userId}")
	public ResponseEntity<ApiResponse<MessageDto>> updateUserInfo(
		@PathVariable Long userId,
		@Validated @RequestBody UpdateUserInfoRequestDto dto) {

		userService.updateUserInfo(userId, dto);

		return ResponseEntity.ok(
			ApiResponse.success(200, "회원 정보 수정 성공", MessageDto.convertFrom("회원 정보가 수정 되었습니다.")));
	}

	//로그인
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<MessageDto>> login(@Validated @RequestBody LoginRequestDto loginRequestDto,
		HttpServletRequest request) {

		User loginUser = userService.login(loginRequestDto);

		//로그인 성공 처리
		HttpSession session = request.getSession();
		session.setAttribute(SessionConst.LOGIN_USER_NAME, loginUser.getUserName());
		session.setAttribute(SessionConst.LOGIN_USER_ID, loginUser.getUserId());
		session.setAttribute(SessionConst.USER_STATUS, loginUser.getStatus());

		return ResponseEntity.ok(
			ApiResponse.success(200, "로그인 성공", MessageDto.convertFrom("로그인 되었습니다.")));
	}

	//로그아웃
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<MessageDto>> logout(HttpServletRequest request) {

		//세션 조회
		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate(); //남은 세션 모두 삭제
		}

		return ResponseEntity.ok(
			ApiResponse.success(200, "로그아웃 성공", MessageDto.convertFrom("로그아웃 되었습니다.")));
	}
}
