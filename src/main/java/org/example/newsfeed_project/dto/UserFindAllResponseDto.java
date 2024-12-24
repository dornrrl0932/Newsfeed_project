package org.example.newsfeed_project.dto;

import lombok.Getter;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.entity.User;

@Getter
public class UserFindAllResponseDto {
    private final Long id;
    private final String email;
    private final String password;
    private final String userName;
    private final String introduction;

    public UserFindAllResponseDto(Long id, String email, String password, String userName, String introduction) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.introduction = introduction;
    }

    public static UserFindAllResponseDto toDto(User user){
        return new UserFindAllResponseDto(user.getUserId(), user.getEmail(), user.getPassword(),user.getUserName(), user.getIntroduction());
    }
}
