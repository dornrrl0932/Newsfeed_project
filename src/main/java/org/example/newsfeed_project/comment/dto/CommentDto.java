package org.example.newsfeed_project.comment.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {
	private String comments;
	private Long like;
	private String userName;
	private LocalDateTime updatedAt;
}
