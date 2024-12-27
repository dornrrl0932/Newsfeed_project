package org.example.newsfeed_project.post.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class PostListDto {
	private List<PostPageDto> posts;

	private PostListDto(List<PostPageDto> posts) {
		this.posts = posts;
	}

	public static PostListDto convertFrom(List<PostPageDto> posts) {
		return new PostListDto(posts);
	}
}
