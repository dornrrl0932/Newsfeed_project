package org.example.newsfeed_project.post.dto;

import java.time.LocalDateTime;

public class PostFindDetailByIdResponseDto {

    private final String title;
    private final String content;
    private final String userName;
    private final LocalDateTime updatedAt;

    public PostFindDetailByIdResponseDto(String title, String content, String userName, LocalDateTime updatedAt) {
        this.title = title;
        this.userName = userName;
        this.content = content;
        this.updatedAt = updatedAt;
    }
}
