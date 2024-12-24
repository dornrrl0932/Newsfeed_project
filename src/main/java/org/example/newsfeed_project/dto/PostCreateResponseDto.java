package org.example.newsfeed_project.dto;

import lombok.Getter;

@Getter
public class PostCreateResponseDto {
    private final String title;
    private final String content;


    public PostCreateResponseDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
