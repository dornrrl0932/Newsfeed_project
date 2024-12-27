package org.example.newsfeed_project.profile.dto;

import java.util.List;

import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.post.dto.PostPageDto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProfileDto {
	private String userName;
	private String introduction;
	private Long followingNum;
	private Long followerNum;
	private List<PostPageDto> posts;

	// 정적 팩토리 메서드; ProfileDto로 변환
	public static ProfileDto convertFrom(User user, Long followingNum, Long followerNum, List<PostPageDto> posts) {
		return ProfileDto.builder()
			.userName(user.getUserName())
			.introduction(user.getIntroduction())
			.followingNum(followingNum)
			.followerNum(followerNum)
			.posts(posts)
			.build();
	}
}