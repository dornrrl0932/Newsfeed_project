package org.example.newsfeed_project.post.dto;


import lombok.Getter;


import java.time.LocalDateTime;

@Getter
public class PostFindByPageResponseDto {
    private final String title;
    private final String contents;
    private final String userName;
    private final LocalDateTime updateAt;

    public PostFindByPageResponseDto(String title, String contents, String userName, LocalDateTime updateAt) {
        this.userName = userName;
        this.title = title;
        this.contents = contents;
        this.updateAt = updateAt;
    }
}
