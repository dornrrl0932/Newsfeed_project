package org.example.newsfeed_project.post.dto;


import lombok.Getter;


import java.time.LocalDateTime;

@Getter
public class PostFindByPageResponseDto {
    private final String title;
    private final String contents;
    public final String userName;
    private final LocalDateTime updateTime;

    public PostFindByPageResponseDto(String title, String contents, String userName, LocalDateTime updateTime) {
        this.userName = userName;
        this.title = title;
        this.contents = contents;
        this.updateTime = updateTime;
    }
}
