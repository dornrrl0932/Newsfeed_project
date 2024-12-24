package org.example.newsfeed_project.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {

    private final String email;
    private final String password;
    private final String renterPassword;
    private final String userName;
    private final String introduction;
}
