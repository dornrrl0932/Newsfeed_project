package org.example.newsfeed_project.profile.dto;

import lombok.Getter;

@Getter
public class ProfileUpdateRequestDto {

	private final String introduction;

	public ProfileUpdateRequestDto(String introduction) {
		this.introduction = introduction;
	}

}