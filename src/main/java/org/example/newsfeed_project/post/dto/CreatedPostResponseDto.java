package org.example.newsfeed_project.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreatedPostResponseDto {

    private final String title;
    private final String contents;
    private final LocalDateTime updatedAt;
}

