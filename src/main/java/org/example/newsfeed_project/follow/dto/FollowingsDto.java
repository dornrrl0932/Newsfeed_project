package org.example.newsfeed_project.follow.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowingsDto {
	private String userName;
	private List<FollowUserInfoDto> followings;
}
