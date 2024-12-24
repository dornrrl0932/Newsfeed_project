package org.example.newsfeed_project.dto;


import lombok.Getter;


import java.time.LocalDateTime;

@Getter
public class NewFeedResponseDto {
    public final Long id;
    private final String title;
    private final String contents;
    private final LocalDateTime updateTime;

    public NewFeedResponseDto(Long id, String title, String contents, LocalDateTime updateTime) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.updateTime = updateTime;
    }
}
