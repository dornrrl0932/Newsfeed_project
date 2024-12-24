package org.example.newsfeed_project.dto;


import lombok.Getter;

@Getter
public class SignUpRequestDto {
    private final String email;
    private final String password;
    private final String userName;
    private final String introduction;


    public SignUpRequestDto(String email, String password, String userName, String introduction) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.introduction = introduction;
    }
}
