package org.example.newsfeed_project.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//RuntimeException을 상속받아 커스텀 예외 생성
@Getter
@RequiredArgsConstructor
public class UserDeletedException extends RuntimeException {

	private final int status;

	public UserDeletedException(ResponseCode responseCode) {
		super(responseCode.getMessage());
		this.status = responseCode.getStatus().value();
	}
}
