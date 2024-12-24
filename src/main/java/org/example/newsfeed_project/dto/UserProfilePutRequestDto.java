package org.example.newsfeed_project.dto;

import lombok.Getter;

@Getter
public class UserProfilePutRequestDto {
    private final String introduction;
    public UserProfilePutRequestDto(String introduction) {
        this.introduction = introduction;
    }


}