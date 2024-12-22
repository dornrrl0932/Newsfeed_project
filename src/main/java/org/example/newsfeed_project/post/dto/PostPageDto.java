package org.example.newsfeed_project.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostPageDto {
    private String userName;
    private String title;
    private String contents;
    private LocalDateTime updateDate;
}