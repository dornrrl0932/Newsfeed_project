package org.example.newsfeed_project.dto;

import lombok.Getter;

@Getter
public class UserProfilePutResponseDto {
   private final String introduction;
    public UserProfilePutResponseDto(String introduction) {
        this.introduction = introduction;
    }

}
