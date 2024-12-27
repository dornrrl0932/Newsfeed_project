package org.example.newsfeed_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageDto {
	private String message;

	public static MessageDto convertFrom(String message) {
		return new MessageDto(message);
	}
}
