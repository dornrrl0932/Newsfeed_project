package org.example.newsfeed_project.common.exception;

import org.example.newsfeed_project.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	//탈퇴 처리 된 회원 예외처리
	@ExceptionHandler(UserDeletedException.class)
	public ResponseEntity<ApiResponse<String>> handleUserDeletedException(UserDeletedException e) {

		//400 BAD_REQUEST와 메세지를 반환
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getStatus(), e.getMessage()));
	}

	//잘못 된 URL에 대한 예외처리
	@ExceptionHandler(InvalidUrlException.class)
	public ResponseEntity<ApiResponse<String>> handleInvalidUrlException(InvalidUrlException e) {

		//404 Not_Found와 메세지를 반환
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getStatus(), e.getMessage()));
	}

	//확인 비밀번호 불일치 예외처리
	@ExceptionHandler(PasswordAuthenticationException.class)
	public ResponseEntity<ApiResponse<String>> HandlePasswordAuthenticationException(
		PasswordAuthenticationException e) {

		// 401 UNAUTHORIZED와 메세지를 반환
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(e.getStatus(), e.getMessage()));
	}

	//이미 존재하는 이메일, 탈퇴한 회원의 이메일 예외처리
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<String>> HandleEmailAlreadyExistsException(EmailAlreadyExistsException e) {

		// 400 BADREQUEST와 메세지 반환
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getStatus(), e.getMessage()));
	}

	// ValidateException 처리
	@ExceptionHandler(ValidateException.class)
	public ResponseEntity<ApiResponse<String>> handleValidateException(ValidateException exception) {
		return ResponseEntity.status(exception.getHttpStatus())
			.body(ApiResponse.error(exception.getStatusValue(), exception.getMessage()));
	}
}

