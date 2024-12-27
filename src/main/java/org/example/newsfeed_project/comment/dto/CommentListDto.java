package org.example.newsfeed_project.comment.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentListDto {
	private List<CommentDto> commentDtoList;
}
