package org.example.newsfeed_project.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private final String title;
    private final String contents;
    private final LocalDateTime updatedAt;

    public PostResponseDto(String title, String contents) {


        this.title = title;
        this.contents = contents;
        this.updatedAt = LocalDateTime.now();
    }
}

