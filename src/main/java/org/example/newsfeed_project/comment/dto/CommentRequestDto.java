package org.example.newsfeed_project.comment.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentRequestDto {
	@Size(max = 250, message = "댓글은 250자 이내로 입력 가능합니다.")
	private String comments;
}
