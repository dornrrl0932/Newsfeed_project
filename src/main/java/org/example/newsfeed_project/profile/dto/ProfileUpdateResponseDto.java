package org.example.newsfeed_project.profile.dto;

import lombok.Getter;

@Getter
public class ProfileUpdateResponseDto {
   private final String introduction;
    public ProfileUpdateResponseDto(String introduction) {
        this.introduction = introduction;
    }

}
