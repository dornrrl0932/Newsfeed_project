package org.example.newsfeed_project.controller;


import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.dto.MessageResponseDto;
import org.example.newsfeed_project.dto.SignUpRequestDto;
import org.example.newsfeed_project.dto.UserFindAllResponseDto;
import org.example.newsfeed_project.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/signup", produces = "application/json")
    public MessageResponseDto signUp(@RequestBody SignUpRequestDto request) {
        return userService.SignUp(request);
    }

    @GetMapping(value = "/", produces = "application/json")
    public List<UserFindAllResponseDto> findAll() {
        List<UserFindAllResponseDto> userFindAllResponseDtoList = userService.findAll();
        return userFindAllResponseDtoList;
    }
}
