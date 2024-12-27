package org.example.newsfeed_project.post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

import org.example.newsfeed_project.entity.Post;

@Getter
public class PostFindDetailByIdResponseDto {

	private final String title;
	private final String contents;
	private final String userName;
	private final LocalDateTime updatedAt;

	private PostFindDetailByIdResponseDto(Post post) {
		this.title = post.getTitle();
		this.userName = post.getUser().getUserName();
		this.contents = post.getContents();
		this.updatedAt = post.getUpdatedAt();
	}

	public static PostFindDetailByIdResponseDto ConvertFromPostFineDetailDto(Post post) {
		return new PostFindDetailByIdResponseDto(post);
	}
}
