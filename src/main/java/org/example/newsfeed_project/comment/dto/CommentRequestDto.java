package org.example.newsfeed_project.comment.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentRequestDto {

	@Size(max = 250, message = "댓글은 250자 이내로 작성해야 합니다.")
	private String comment;
}
