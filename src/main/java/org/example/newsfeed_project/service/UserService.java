package org.example.newsfeed_project.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.dto.CancelRequestDto;
import org.example.newsfeed_project.dto.SignUpRequestDto;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    //회원 탈퇴
    public void CancelUser(Long userId, CancelRequestDto cancelRequestDto) {

      User user = userRepository.findById(userId)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"ID가 존재하지 않습니다. = " + userId));

        user.setStatus(false);

        //deleteById는 하드 삭제를 수행 -> save로 현재 상태를 업데이트
        userRepository.save(user);
    }
}
