package org.example.newsfeed_project.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreatedPostResponseDto {

    private final String title;
    private final String contents;
    private final LocalDateTime updatedAt;

    public CreatedPostResponseDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.updatedAt = LocalDateTime.now();
    }
}

