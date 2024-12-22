package org.example.newsfeed_project.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.newsfeed_project.entity.Post;
import org.springframework.data.domain.Page;

import java.security.cert.CertPath;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PostPageDto {
    private String userName;
    private String title;
    private String contents;
    private LocalDateTime updateDate;

    // Page<Post> -> List<PostPageDto> 로 변환 (엔티티 -> Dto)
    public static List<PostPageDto> convertFrom(Page<Post> postPage) {
        return postPage.getContent().stream()
                .map(post -> new PostPageDto(
                        post.getUser().getUserName(),
                        post.getTitle(),
                        post.getContents(),
                        post.getUpdatedDate()
                ))
                .collect(Collectors.toList());
    }
}