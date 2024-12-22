package org.example.newsfeed_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CancelRequestDto {

    private final String password;
    private final String renterPassword;
}
