package org.example.newsfeed_project.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordAuthenticationException extends RuntimeException {

	private int status;

	public PasswordAuthenticationException(ResponseCode responseCode) {
		super(responseCode.getMessage());
		this.status = responseCode.getStatus().value();
	}
}
