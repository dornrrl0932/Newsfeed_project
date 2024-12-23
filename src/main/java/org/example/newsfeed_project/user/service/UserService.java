package org.example.newsfeed_project.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.user.config.PasswordEncoder;
import org.example.newsfeed_project.user.dto.CancelRequestDto;
import org.example.newsfeed_project.user.dto.SignUpRequestDto;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.exception.InvalidUrlException;
import org.example.newsfeed_project.exception.UserDeletedException;
import org.example.newsfeed_project.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    //회원 가입
    public void signupUser(SignUpRequestDto signUpRequestDto) {

        InvalidUrlException e = new InvalidUrlException("잘못 된 URL 입니다");

        //비밀번호 확인 : 입력한 비밀번호와 확인 비밀번호가 일치하는지 확인
        if (!signUpRequestDto.getPassword().equals(signUpRequestDto.getRenterPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }

        //이메일 중복 확인
        if (userRepository.findByEmail(signUpRequestDto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다.");
        }

        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

        //회원 생성
        User user = new User(signUpRequestDto.getEmail(), encodedPassword, signUpRequestDto.getUserName());

        //DB에 저장
        userRepository.save(user);
    }


    //회원 탈퇴
    public void CancelUser(Long userId, CancelRequestDto cancelRequestDto) {

        //userId로 회원 검색, 일치하는 회원이 없는 경우 NOT_FOUND 반환
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 존재하지 않습니다. = " + userId));

        //비밀번호 확인 : 입력한 비밀번호와 확인 비밀번호가 일치하는지 확인
        if (!cancelRequestDto.getPassword().equals(cancelRequestDto.getRenterPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }

        //탈퇴처리된 회원 예외처리 -> status가 false인 경우 에러 반환
        if(!user.getStatus()) {
            throw new UserDeletedException("이미 탈퇴 처리 된 회원입니다.");
        }

        //탈퇴처리 -> 회원 상태 false로 변경
        user.setStatus(false);

        //deleteById는 하드 삭제를 수행 -> save로 현재 상태를 업데이트
        userRepository.save(user);
    }
}
