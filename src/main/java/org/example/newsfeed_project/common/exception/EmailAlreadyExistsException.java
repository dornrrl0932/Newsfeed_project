package org.example.newsfeed_project.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmailAlreadyExistsException extends RuntimeException {

	private final int status;

	public EmailAlreadyExistsException(ResponseCode responseCode) {
		super(responseCode.getMessage());
		this.status = responseCode.getStatus().value();
	}

}
