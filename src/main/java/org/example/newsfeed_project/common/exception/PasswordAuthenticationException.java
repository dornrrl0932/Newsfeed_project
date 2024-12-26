package org.example.newsfeed_project.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PasswordAuthenticationException extends RuntimeException {

	private HttpStatus status;

	public PasswordAuthenticationException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}
}
