package org.example.newsfeed_project.dto;

import lombok.Getter;

@Getter
public class NewFeedRequestDto {

    private final String order;

    public NewFeedRequestDto(String order) {
        this.order = order;
    }
}
