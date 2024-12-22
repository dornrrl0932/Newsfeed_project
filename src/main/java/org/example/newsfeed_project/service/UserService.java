package org.example.newsfeed_project.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.dto.SignUpRequestDto;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    //회원 가입
    public void signupUser(SignUpRequestDto signUpRequestDto) {

        //회원 생성
        User user = new User(signUpRequestDto.getEmail(), signUpRequestDto.getPassword(), signUpRequestDto.getUserName());

        //DB에 저장
        userRepository.save(user);

    }
}
