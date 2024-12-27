package org.example.newsfeed_project.comment.dto;

import java.time.LocalDateTime;

import org.example.newsfeed_project.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CommentDto {
	private String comments;
	private Long likeCount;
	private String userName;
	private LocalDateTime updatedAt;

	public static CommentDto convertDto(Comment comment) {
		return CommentDto.builder()
			.comments(comment.getComments())
			.likeCount(comment.getLikeCount())
			.userName(comment.getUser().getUserName())
			.updatedAt(comment.getUpdatedAt())
			.build();
	}
}
