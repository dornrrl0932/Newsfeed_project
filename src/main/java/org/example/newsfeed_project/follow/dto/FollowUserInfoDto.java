package org.example.newsfeed_project.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowUserInfoDto {
	private String userName;
	private String introduction;
	private String status;
}
