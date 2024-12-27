package org.example.newsfeed_project.post.dto;

import lombok.Getter;

@Getter
public class PostFindByPageRequestDto {

	private final String order;

	public PostFindByPageRequestDto(String order) {
		this.order = order;
	}

}
