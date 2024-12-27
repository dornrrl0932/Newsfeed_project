package org.example.newsfeed_project.post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostFindByDateRangeRequestDto {

	public final LocalDateTime startDate;
	public final LocalDateTime endDate;
	public final String order;

	public PostFindByDateRangeRequestDto(LocalDateTime startDate, LocalDateTime endDate, String order) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.order = order;
	}

}
