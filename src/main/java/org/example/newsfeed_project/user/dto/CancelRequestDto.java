package org.example.newsfeed_project.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CancelRequestDto {

	private final String password;
	private final String renterPassword;

}
